package com.kosmokamikaze.intervalapp.viewmodel.auth

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kosmokamikaze.intervalapp.repository.KtorClient
import kotlinx.coroutines.launch

class SignupViewModel(
    private val context: Context,
    private val extras: Bundle
) : ViewModel() {

    private val _signupResult = MutableLiveData<Boolean>()
    val signupResult: LiveData<Boolean> = _signupResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val ktorClient = KtorClient()

    fun signup(login: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = ktorClient.signup(login, email, password)
                if (response.success && response.token != null) {
                    // Store the token for future use
                    val sharedPrefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
                    sharedPrefs.edit().putString("token", response.token).apply()
                    _signupResult.value = true
                } else {
                    _error.value = response.message ?: "Signup failed"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "An error occurred during signup"
            }
        }
    }
} 