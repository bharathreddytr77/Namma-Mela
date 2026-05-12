package com.example.internship_project.ui.fanwall

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.internship_project.data.CommentEntity
import com.example.internship_project.data.NammaMelaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FanWallViewModel(private val repository: NammaMelaRepository) : ViewModel() {
    val comments: StateFlow<List<CommentEntity>> = repository.comments.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    private val _commentText = MutableStateFlow("")
    val commentText: StateFlow<String> = _commentText

    fun updateComment(value: String) {
        _commentText.value = value
    }

    fun addComment() {
        val text = _commentText.value.trim()
        if (text.isBlank()) return
        viewModelScope.launch {
            repository.addComment(text)
            _commentText.update { "" }
        }
    }

    companion object {
        fun factory(repository: NammaMelaRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return FanWallViewModel(repository) as T
                }
            }
        }
    }
}
