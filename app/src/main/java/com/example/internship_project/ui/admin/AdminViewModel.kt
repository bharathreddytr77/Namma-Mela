package com.example.internship_project.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.internship_project.data.CastEntity
import com.example.internship_project.data.NammaMelaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CastEditState(
    val id: Int,
    val name: String,
    val role: String,
    val imageUrl: String
)

data class AdminUiState(
    val playName: String = "",
    val duration: String = "",
    val posterUrl: String = "",
    val cast: List<CastEditState> = emptyList(),
    val isLoaded: Boolean = false,
    val isDirty: Boolean = false,
    val savedMessage: String = ""
)

class AdminViewModel(private val repository: NammaMelaRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(AdminUiState())
    val uiState: StateFlow<AdminUiState> = _uiState

    init {
        viewModelScope.launch {
            repository.ensureSeeded()
        }
        viewModelScope.launch {
            combine(repository.play, repository.cast) { play, cast ->
                AdminUiState(
                    playName = play.name,
                    duration = play.duration,
                    posterUrl = play.posterUrl,
                    cast = cast.map {
                        CastEditState(
                            id = it.id,
                            name = it.name,
                            role = it.role,
                            imageUrl = it.imageUrl
                        )
                    },
                    isLoaded = true
                )
            }.collect { freshState ->
                _uiState.update { current ->
                    if (current.isDirty) current else freshState
                }
            }
        }
    }

    fun updatePlayName(value: String) {
        _uiState.update { it.copy(playName = value, isDirty = true, savedMessage = "") }
    }

    fun updateDuration(value: String) {
        _uiState.update { it.copy(duration = value, isDirty = true, savedMessage = "") }
    }

    fun updatePosterUrl(value: String) {
        _uiState.update { it.copy(posterUrl = value, isDirty = true, savedMessage = "") }
    }

    fun updateCastName(id: Int, value: String) {
        updateCast(id) { it.copy(name = value) }
    }

    fun updateCastRole(id: Int, value: String) {
        updateCast(id) { it.copy(role = value) }
    }

    fun updateCastImageUrl(id: Int, value: String) {
        updateCast(id) { it.copy(imageUrl = value) }
    }

    fun saveChanges() {
        val state = _uiState.value
        viewModelScope.launch {
            saveShow(state, resetSeats = false)
            _uiState.update {
                it.copy(isDirty = false, savedMessage = "Saved")
            }
        }
    }

    fun saveAsNewShow() {
        val state = _uiState.value
        viewModelScope.launch {
            saveShow(state, resetSeats = true)
            _uiState.update {
                it.copy(
                    isDirty = false,
                    savedMessage = "New show saved. Seats and fan comments are reset."
                )
            }
        }
    }

    fun clearFanComments() {
        viewModelScope.launch {
            repository.resetFanComments()
            _uiState.update {
                it.copy(savedMessage = "Fan wall comments cleared.")
            }
        }
    }

    private fun updateCast(
        id: Int,
        transform: (CastEditState) -> CastEditState
    ) {
        _uiState.update { state ->
            state.copy(
                cast = state.cast.map { if (it.id == id) transform(it) else it },
                isDirty = true,
                savedMessage = ""
            )
        }
    }

    private suspend fun saveShow(state: AdminUiState, resetSeats: Boolean) {
        val cast = state.cast.map {
            CastEntity(
                id = it.id,
                name = it.name,
                role = it.role,
                imageUrl = it.imageUrl
            )
        }
        if (resetSeats) {
            repository.saveShowAndResetSeats(
                name = state.playName,
                duration = state.duration,
                posterUrl = state.posterUrl,
                cast = cast
            )
        } else {
            repository.savePlay(
                name = state.playName,
                duration = state.duration,
                posterUrl = state.posterUrl
            )
            repository.saveCast(cast)
        }
    }

    companion object {
        fun factory(repository: NammaMelaRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AdminViewModel(repository) as T
                }
            }
        }
    }
}
