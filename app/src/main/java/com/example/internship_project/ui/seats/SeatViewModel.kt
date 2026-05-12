package com.example.internship_project.ui.seats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.internship_project.data.NammaMelaRepository
import com.example.internship_project.data.SeatEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SeatViewModel(private val repository: NammaMelaRepository) : ViewModel() {
    val seats: StateFlow<List<SeatEntity>> = repository.seats.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    init {
        viewModelScope.launch {
            repository.ensureSeeded()
        }
    }

    fun reserveSeat(seat: SeatEntity) {
        if (seat.isReserved) return
        viewModelScope.launch {
            repository.reserveSeat(seat.id)
        }
    }

    companion object {
        fun factory(repository: NammaMelaRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SeatViewModel(repository) as T
                }
            }
        }
    }
}
