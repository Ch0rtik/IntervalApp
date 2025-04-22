package com.kosmokamikaze.intervalapp.viewmodel.auth

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kosmokamikaze.intervalapp.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(context: Context) : ViewModel() {
    private val repository = AuthRepository(context)

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    fun login(login: String, password: String) {
        viewModelScope.launch {
            try {
                _loginState.value = LoginState.Loading
                val token = repository.login(login, password)
                _loginState.value = LoginState.Success(token)
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    sealed class LoginState {
        object Loading : LoginState()
        data class Success(val token: String) : LoginState()
        data class Error(val message: String) : LoginState()
    }
} 