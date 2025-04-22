package com.kosmokamikaze.intervalapp.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    val login: String,
    val email: String,
    val password: String
)

@Serializable
data class SignupResponse(
    val success: Boolean,
    val message: String? = null,
    val token: String? = null
) 