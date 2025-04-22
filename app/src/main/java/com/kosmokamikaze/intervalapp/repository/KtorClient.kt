package com.kosmokamikaze.intervalapp.repository

import android.content.Context
import android.util.Log
import com.kosmokamikaze.intervalapp.model.auth.LoginRequest
import com.kosmokamikaze.intervalapp.model.auth.LoginResponse
import com.kosmokamikaze.intervalapp.model.auth.SignupRequest
import com.kosmokamikaze.intervalapp.model.auth.SignupResponse
import com.kosmokamikaze.intervalapp.model.quiz.HighScoreRequest
import com.kosmokamikaze.intervalapp.model.quiz.QuizData
import com.kosmokamikaze.intervalapp.model.quiz.QuizDataRequest
import com.kosmokamikaze.intervalapp.model.quiz.QuizDataResponse
import com.kosmokamikaze.intervalapp.model.quiz.QuizTypes
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class KtorClient(private val baseUrl: String = "http://10.0.2.2:8080/") {
    private val client = HttpClient(OkHttp) {
        defaultRequest { url(baseUrl) }

        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun login(login: String, password: String): LoginResponse {
        try {
            return client.post("login") {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                setBody(LoginRequest(login, password))
            }.body()
        } catch (e: Exception) {
            Log.e("KtorClient", "Error during login", e)
            throw e
        }
    }

    suspend fun signup(login: String, email: String, password: String): SignupResponse {
        try {
            val response = client.post("signup") {
                url {
                    parameters.append("login", login)
                    parameters.append("email", email)
                    parameters.append("password", password)
                }
            }

            return if (response.status.isSuccess()) {
                response.body()
            } else {
                val errorMessage = response.body<String>()
                SignupResponse(
                    success = false,
                    message = errorMessage,
                    token = null
                )
            }
        } catch (e: Exception) {
            Log.e("KtorClient", "Error during signup: ${e.message}", e)
            return SignupResponse(
                success = false,
                message = "Network error occurred: ${e.message}",
                token = null
            )
        }
    }

    suspend fun getQuizData(id: Int): QuizData {
        try {
            val response = client.get("quizdata?id=$id") {
                accept(ContentType.Application.Json)
            }
            val responseBody = response.body<QuizDataResponse>()
            return QuizData(
                id = responseBody.id,
                highScore = responseBody.highScore,
                type = QuizTypes.valueOf(responseBody.type),
                option = responseBody.option,
                range = responseBody.range,
                amountOfButtons = responseBody.amountOfButtons
            )
        } catch (e: Exception) {
            Log.e("KtorClient", "Error getting quiz data for id=$id", e)
            throw e
        }
    }

    suspend fun getIntervalQuizData(): List<QuizData> {
        val list = mutableListOf<QuizData>()
        for (i in 1..15) {
            list.add(getQuizData(i))
        }
        return list
    }

    suspend fun getChordQuizData(): List<QuizData> {
        val list = mutableListOf<QuizData>()
        for (i in 16..21) {
            list.add(getQuizData(i))
        }
        return list
    }

    suspend fun getScaleQuizData(): List<QuizData> {
        val list = mutableListOf<QuizData>()
        for (i in 22..28) {
            list.add(getQuizData(i))
        }
        return list
    }

    suspend fun updateHighScore(id: Int, highScore: Int) {
        try {
            Log.d("KtorClient", "Updating high score: id=$id, score=$highScore")
            val response = client.post("quizdata") {
                contentType(ContentType.Application.Json)
                setBody(HighScoreRequest(id = id, highScore = highScore))
            }
            Log.d("KtorClient", "Update response status: ${response.status}")
        } catch (e: Exception) {
            Log.e("KtorClient", "Error updating high score", e)
            throw e
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: KtorClient? = null

        fun getKtorClient(context: Context): KtorClient {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val instance = KtorClient()
                INSTANCE = instance
                return instance
            }
        }
    }
}

