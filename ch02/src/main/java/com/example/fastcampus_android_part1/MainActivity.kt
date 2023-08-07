package com.example.fastcampus_android_part1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.fastcampus_android_part1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val countViewModel: CountViewModel by lazy {
        ViewModelProvider(this)[CountViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentCount=savedInstanceState?.getInt(COUNT_KEY) ?: 0

        countViewModel.currentNumber=currentCount

        setCountNumber()

        binding.initButton.setOnClickListener {
            countViewModel.initNumber()
            setCountNumber()
        }

        binding.plusButton.setOnClickListener {
            countViewModel.increaseNumber()
            setCountNumber()
        }
    }
    private fun setCountNumber() {
        binding.countTextView.text=countViewModel.currentNumber.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(COUNT_KEY,countViewModel.currentNumber)
    }
}