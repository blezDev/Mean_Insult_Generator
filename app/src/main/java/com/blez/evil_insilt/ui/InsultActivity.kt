package com.blez.evil_insilt.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope

import com.blez.evil_insilt.R
import com.blez.evil_insilt.databinding.ActivityInsultBinding
import com.blez.evil_insilt.util.snakeBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InsultActivity : AppCompatActivity() {
    private lateinit var binding : ActivityInsultBinding
    private lateinit var viewModel: InsultViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[InsultViewModel::class.java]
        viewVisibility(false)
        subscribeToUI()
        binding.generate.setOnClickListener {
            viewModel.generateInsult()
        }



    }

    private fun viewVisibility(value : Boolean){
        binding.insultText.isVisible = value
        binding.generate.isVisible = value
        binding.insultByText.isVisible = value
        binding.progressBar.isVisible = !value
    }


    private fun subscribeToUI(){
        lifecycleScope.launch {
            viewModel.loadState.collect{
                when(it){
                    is InsultViewModel.SetupEvent.Loading->{
                        viewVisibility(false)
                    }

                    is InsultViewModel.SetupEvent.InsultData->{
                        viewVisibility(true)
                        Log.e("TAG",it.data.toString())
                        binding.insultText.text = it.data.insult
                        if (it.data.createdby.isEmpty()){
                            binding.insultByText.text = "-Someone probably"
                        }else
                        binding.insultByText.text = "-${it.data.createdby}"
                    }
                    is InsultViewModel.SetupEvent.Failure->{
                        binding.progressBar.isVisible = false
                        snakeBar(it.message,binding.root)
                    }
                    else-> Unit
                }
            }
        }
    }
}