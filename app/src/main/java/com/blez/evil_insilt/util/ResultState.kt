package com.blez.evil_insilt.util

sealed class ResultState<T>(val data : T? =null,val message : String?= null) {
    class Success<T>(data: T?) : ResultState<T>(data)
    class Failure<T>(message: String?=null) : ResultState<T>(message = message)

}