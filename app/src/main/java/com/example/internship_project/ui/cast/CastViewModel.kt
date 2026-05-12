package com.example.internship_project.ui.cast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.internship_project.data.CastEntity
import com.example.internship_project.data.NammaMelaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CastViewModel(private val repository: NammaMelaRepository) : ViewModel() {
    val cast: StateFlow<List<CastEntity>> = repository.cast.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = NammaMelaRepository.DefaultCast
    )

    init {
        viewModelScope.launch {
            repository.ensureSeeded()
        }
    }

    companion object {
        fun factory(repository: NammaMelaRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CastViewModel(repository) as T
                }
            }
        }
    }
}
