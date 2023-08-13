package com.example.ch04

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ch04.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}