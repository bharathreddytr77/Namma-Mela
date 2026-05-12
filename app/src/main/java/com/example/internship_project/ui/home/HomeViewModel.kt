package com.example.internship_project.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.internship_project.data.NammaMelaRepository
import com.example.internship_project.data.PlayEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: NammaMelaRepository) : ViewModel() {
    val play: StateFlow<PlayEntity> = repository.play.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = NammaMelaRepository.DefaultPlay
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
                    return HomeViewModel(repository) as T
                }
            }
        }
    }
}
