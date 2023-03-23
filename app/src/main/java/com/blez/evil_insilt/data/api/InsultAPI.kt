package com.blez.evil_insilt.data.api

import com.blez.evil_insilt.data.model.InsultResponse
import retrofit2.Response
import retrofit2.http.GET

interface InsultAPI {
    @GET("/generate_insult.php?lang=en&type=json")
    suspend fun generateInsult() : Response<InsultResponse>
}