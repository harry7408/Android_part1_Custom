package com.sesac.ch06

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.sesac.ch06.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mainTimer: Timer? = null
    private var lapTimer: Timer? = null
    private var recordCount = 0

    private var mainTimerMinute: Int = 0
    private var mainTimerSecond: Int = 0
    private var mainTimerDeciSecond: Int = 0

    private var lapTimerMinute: Int = 0
    private var lapTimerSecond: Int = 0
    private var lapTimerDeciSecond: Int = 0

    private var mainDeciSecond: Int = 0
    private var lapDeciSecond: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        initViewSettings()

        with(binding) {
            startButton.setOnClickListener {
                ongoingViewSettings()
                start()
            }

            stopButton.setOnClickListener {
                stopViewSettings()
                stop()
            }

            resetButton.setOnClickListener {
                resetViewSettings()
                reset()
            }

            recordButton.setOnClickListener {
                record()
                recordViewSettings()
                recordCount++
            }

            resumeButton.setOnClickListener {
                resumeViewSettings()
                resume()
            }
        }
    }

    private fun start() {
        mainTimerWithCoroutine()
    }

    private fun stop() {
        mainTimer?.cancel()
        mainTimer = null
        resetLapTimer()
    }

    private fun resume() {
        mainTimerWithCoroutine()
        resetLapTimer()
        lapTimerWithCoroutine()
    }

    private fun reset() {
        mainDeciSecond = 0
        mainTimer?.cancel()
        mainTimer = null

        lapDeciSecond = 0
        resetLapTimer()

        recordCount = 0
    }

    private fun record() {
        if (recordCount > 0) {
            lapDeciSecond = 0
            resetLapTimer()
            lapTimerWithCoroutine()
            recordText()
        } else {
            lapTimerWithCoroutine()
        }
    }

    private fun mainTimerWithCoroutine() {
        with(CoroutineScope(Dispatchers.Default)) {
            mainTimer = timer(period = 10, initialDelay = 0) {
                mainDeciSecond += 1

                mainTimerMinute = (mainDeciSecond / 100) / 60
                mainTimerSecond = (mainDeciSecond / 100) % 60
                mainTimerDeciSecond = mainDeciSecond % 100

                CoroutineScope(Dispatchers.Main).launch {
                    binding.mainTimerTextView.text =
                        String.format(
                            "%02d:%02d:%02d",
                            mainTimerMinute, mainTimerSecond, mainTimerDeciSecond
                        )
                }
            }
        }
    }

    private fun lapTimerWithCoroutine() {
        with(CoroutineScope(Dispatchers.Default)) {
            lapTimer = timer(period = 10, initialDelay = 0) {
                lapDeciSecond += 1

                lapTimerMinute = (lapDeciSecond / 100) / 60
                lapTimerSecond = (lapDeciSecond / 100) % 60
                lapTimerDeciSecond = lapDeciSecond % 100

                CoroutineScope(Dispatchers.Main).launch {
                    binding.lapTimerTextView.text =
                        String.format(
                            "%02d:%02d:%02d",
                            lapTimerMinute, lapTimerSecond, lapTimerDeciSecond
                        )
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun recordText() {
        val container = binding.resultLinearLayout
        TextView(applicationContext).apply {
            textSize = 24f
            gravity = Gravity.CENTER
            text = "\t\t\t\t ${recordCount - 1} \t\t\t ${binding.lapTimerTextView.text}" +
                    String.format(
                        "\t\t\t %02d:%02d:%02d",
                        mainTimerMinute,
                        mainTimerSecond,
                        mainTimerDeciSecond
                    )
            setPadding(24, 24, 0, 0)
        }.let {
            container.addView(it, 0)
        }
    }

    private fun initViewSettings() {
        with(binding) {
            stopButton.visibility = View.GONE
            resetButton.visibility = View.GONE
            resumeButton.visibility = View.GONE
            lapTitleTextView.visibility = View.GONE
            recordButton.run {
                alpha = 0.5f
                isClickable = false
            }
        }
    }

    private fun ongoingViewSettings() {
        with(binding) {
            recordButton.alpha = 1.0f
            stopButton.visibility = View.VISIBLE
            startButton.visibility = View.GONE
            recordButton.isClickable = true
        }
    }

    private fun stopViewSettings() {
        with(binding) {
            resumeButton.visibility = View.VISIBLE
            stopButton.visibility = View.GONE
            recordButton.visibility = View.GONE
            resetButton.visibility = View.VISIBLE
        }
    }

    private fun resumeViewSettings() {
        with(binding) {
            resumeButton.visibility = View.GONE
            stopButton.visibility = View.VISIBLE
            binding.resetButton.visibility = View.INVISIBLE
            binding.recordButton.visibility = View.VISIBLE
        }
    }

    private fun resetViewSettings() {
        with(binding) {
            mainTimerTextView.text = getString(R.string.init_time)
            binding.resetButton.visibility = View.GONE
            resumeButton.visibility = View.GONE
            startButton.visibility = View.VISIBLE
            lapTitleTextView.visibility = View.GONE
            lapTimeScrollView.visibility = View.INVISIBLE
            lapTimerTextView.visibility = View.GONE
            recordButton.run {
                visibility = View.VISIBLE
                alpha = 0.5f
                isClickable = false
            }
            resultLinearLayout.removeAllViews()
        }
    }

    private fun recordViewSettings() {
        with(binding) {
            lapTitleTextView.visibility = View.VISIBLE
            lapTimerTextView.visibility = View.VISIBLE
            lapTimeScrollView.visibility = View.VISIBLE
        }
    }

    private fun resetLapTimer() {
        lapTimer?.cancel()
        lapTimer = null
    }
}