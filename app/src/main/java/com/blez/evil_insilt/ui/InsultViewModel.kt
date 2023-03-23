package com.blez.evil_insilt.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blez.evil_insilt.data.model.InsultResponse
import com.blez.evil_insilt.repository.InsultRepository
import com.blez.evil_insilt.util.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsultViewModel @Inject constructor(val repo : InsultRepository) : ViewModel() {

    sealed class SetupEvent{
        object Loading : SetupEvent()
        data class InsultData(val data : InsultResponse) : SetupEvent()
        data class Failure(val message : String) : SetupEvent()

    }
    init {
        generateInsult()
    }
    private val _LoadState = MutableStateFlow<SetupEvent>(SetupEvent.Loading)
    val loadState :StateFlow<SetupEvent>
    get () = _LoadState

    fun generateInsult(){
        viewModelScope.launch {
            val result = repo.generateAPI()
            when (result){
                is ResultState.Success->{
                    _LoadState.value = SetupEvent.InsultData(result.data!!)
                }
                is ResultState.Failure->{
                    _LoadState.value = SetupEvent.Failure(result.message.toString())
                }
            }
        }
    }

}