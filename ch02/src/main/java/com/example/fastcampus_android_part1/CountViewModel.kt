package com.example.fastcampus_android_part1

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class CountViewModel(application: Application) : AndroidViewModel(application) {
    var currentNumber=0

    fun initNumber() {
        currentNumber=0
    }

    fun increaseNumber() {
        currentNumber++
    }
}