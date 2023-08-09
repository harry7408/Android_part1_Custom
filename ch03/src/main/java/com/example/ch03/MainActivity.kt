package com.example.ch03

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.ch03.databinding.ActivityMainBinding
import java.math.BigDecimal

private const val UNIT_SETTINGS = "Unit Settings"
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var cmTom = true

    /**
     * BigDecimal 자료형에 BigDecimal(숫자)를 넣으면 소숫점 오류가 그대로 나타난다
     * -> 숫자를 넣을땐 .valueOf를 활용 문자를 넣을땐 그냥 괄호 안에 넣기
     */
    private var inputNumber: BigDecimal = BigDecimal.valueOf(0.0)


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.inputEditText.addTextChangedListener { text ->
            inputNumber = if (text.isNullOrEmpty()) {
                BigDecimal.valueOf(0.0)
            } else {
                text.toString().toBigDecimal()
            }

            if (cmTom) {
                binding.changedTextView.text = inputNumber
                    .multiply(BigDecimal.valueOf(0.01)).toString()
            } else {
                binding.changedTextView.text =
                    inputNumber.multiply(BigDecimal.valueOf(100)).toString()
            }
        }


        binding.swapButton.setOnClickListener {
            cmTom = cmTom.not()

            if (cmTom) {
                binding.originalUnitTextView.text = getString(R.string.cm_unit)
                binding.wantedUnitTextView.text = getString(R.string.meter_unit)
                binding.changedTextView.text = inputNumber
                    .multiply(BigDecimal.valueOf(0.01)).toString()
            } else {
                binding.originalUnitTextView.text = getString(R.string.meter_unit)
                binding.wantedUnitTextView.text = getString(R.string.cm_unit)
                binding.changedTextView.text =
                    inputNumber.multiply(BigDecimal.valueOf(100)).toString()
            }
        }
    }

    /**
     * onPause()와 onStop()이 호출 되더라도 이 함수가 호출 안되는 경우도 있다
     * */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(UNIT_SETTINGS, cmTom)
        Log.e(TAG, getString(R.string.savedInstanceState))
    }

    /**
     * onStart( ) 메서드 다음에 호출된다.
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        cmTom = savedInstanceState.getBoolean(UNIT_SETTINGS)
        binding.originalUnitTextView.text =
            if (cmTom) getString(R.string.cm_unit) else getString(R.string.meter_unit)
        binding.wantedUnitTextView.text =
            if (cmTom) getString(R.string.meter_unit) else getString(R.string.cm_unit)
        Log.e(TAG, getString(R.string.restoreInstanceState))
        super.onRestoreInstanceState(savedInstanceState)
    }
}