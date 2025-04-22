package com.kosmokamikaze.intervalapp.repository

import android.content.Context
import android.content.SharedPreferences
import com.kosmokamikaze.intervalapp.model.auth.LoginRequest
import com.kosmokamikaze.intervalapp.model.auth.LoginResponse

class AuthRepository(private val context: Context) {
    private val ktorClient = KtorClient.getKtorClient(context)
    private val prefs: SharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    suspend fun login(login: String, password: String): String {
        val response = ktorClient.login(login, password)
        saveToken(response.token)
        return response.token
    }

    private fun saveToken(token: String) {
        prefs.edit().putString("auth_token", token).apply()
    }

    fun getToken(): String? {
        return prefs.getString("auth_token", null)
    }

    fun clearToken() {
        prefs.edit().remove("auth_token").apply()
    }
} 