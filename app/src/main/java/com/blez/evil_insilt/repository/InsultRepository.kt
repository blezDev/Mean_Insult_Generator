package com.blez.evil_insilt.repository

import android.content.Context
import com.blez.evil_insilt.data.api.InsultAPI
import com.blez.evil_insilt.data.model.InsultResponse
import com.blez.evil_insilt.util.ResultState
import com.blez.evil_insilt.util.checkForInternetConnection
import dagger.hilt.android.qualifiers.ApplicationContext

class InsultRepository(val api : InsultAPI,val  context : Context) {

    suspend fun generateAPI() : ResultState<InsultResponse>{
        return try {
            val result = api.generateInsult()
            if (result.isSuccessful){
                ResultState.Success(result.body())
            }
            else if (context.checkForInternetConnection()){
                ResultState.Failure("Please Connect to Internet!!")
            }
            else
                ResultState.Failure("Server Issue. Please Try Later!!")
        }catch (e:Exception){
            ResultState.Failure("Something went wrong!! Please Try Again Later. 😖")
        }


    }
}