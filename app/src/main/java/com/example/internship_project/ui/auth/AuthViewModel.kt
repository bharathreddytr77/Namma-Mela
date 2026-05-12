package com.example.internship_project.ui.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

enum class UserRole {
    USER,
    ADMIN
}

data class AuthUiState(
    val role: UserRole? = null,
    val adminPin: String = "",
    val errorMessage: String = ""
)

class AuthViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    fun continueAsUser() {
        _uiState.update {
            it.copy(role = UserRole.USER, adminPin = "", errorMessage = "")
        }
    }

    fun updateAdminPin(value: String) {
        _uiState.update {
            it.copy(adminPin = value, errorMessage = "")
        }
    }

    fun loginAsAdmin() {
        val pin = _uiState.value.adminPin.trim()
        if (pin == ADMIN_PIN) {
            _uiState.update {
                it.copy(role = UserRole.ADMIN, adminPin = "", errorMessage = "")
            }
        } else {
            _uiState.update {
                it.copy(errorMessage = "Invalid admin PIN")
            }
        }
    }

    fun logout() {
        _uiState.update { AuthUiState() }
    }

    companion object {
        const val ADMIN_PIN = "2468"
    }
}
