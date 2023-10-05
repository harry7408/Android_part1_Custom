package com.example.ch05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.text.isDigitsOnly
import com.example.ch05.databinding.ActivityMainBinding
import java.lang.StringBuilder
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val userInput = arrayListOf<StringBuilder>()
    private var calculate: ((firstOperand : String, secondOperand: String)->String)?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
    }


    fun clickButton(view: View) {
        val input = when(view as? Button) {
            binding.btn0->binding.btn0.text
            binding.btn1->binding.btn1.text
            binding.btn2->binding.btn2.text
            binding.btn3->binding.btn3.text
            binding.btn4->binding.btn4.text
            binding.btn5->binding.btn5.text
            binding.btn6->binding.btn6.text
            binding.btn7->binding.btn7.text
            binding.btn8->binding.btn8.text
            binding.btn9->binding.btn9.text
            binding.btnMinus->binding.btnMinus.text
            binding.btnPlus->binding.btnPlus.text
            binding.btnDot->binding.btnDot.text
            else ->""
        }.toString()

        if (input.isDigitOrDot() && userInput.isEmpty()) {
            userInput.add(java.lang.StringBuilder(""))
        } else if (!input.isDigitOrDot() && getBufferListSize() % 2 != 0) {
            userInput.add(java.lang.StringBuilder(""))
        } else if (input.isDigitOrDot() && getBufferListSize() % 2 == 0) {
            userInput.add(java.lang.StringBuilder(""))
        }

        val buffer = userInput[getBufferListSize() - 1]

        if (getBufferListSize() % 2 == 1 && buffer.isEmpty() && input == "0") return
        if ("." in buffer && input == ".") return
        if (getBufferListSize() % 2 == 0 && buffer.isNotEmpty()) return
        if (getBufferListSize() % 2 != 0 && input == ".") {
            buffer.append("0")
        }
        buffer.append(input)
        updateEquation()
    }

    private fun getBufferListSize(): Int {
        return userInput.size
    }

    private fun updateEquation() {
        val equation: java.lang.StringBuilder = java.lang.StringBuilder("")
        userInput.forEach {
            equation.append(it)
            binding.expressionTextView.text = equation
        }
    }

    fun clearClicked(view: View) {
        when (view as? Button) {
            binding.btnClear -> {
                binding.resultTextView.text = ""
                binding.expressionTextView.text = getString(R.string.text_zero)
                userInput.clear()
            }
        }
    }

    fun equalClicked(view: View) {
        if (userInput.size % 2 == 0 && getBufferListSize() < 3) return
        calculate = when (userInput[getBufferListSize() / 2].toString()) {
            "+" -> { operand1: String, operand2: String ->
                (formatStringToDouble(operand1) + formatStringToDouble(operand2)).toString()
            }

            else -> { operand1: String, operand2: String ->
                (formatStringToDouble(operand1) - formatStringToDouble(operand2)).toString()
            }
        }
        binding.resultTextView.text =
            calculate?.let { it(userInput[0].toString(), userInput[2].toString()) }
    }


    private fun numberFormat(): DecimalFormat {
        val symbols = DecimalFormatSymbols()
        symbols.decimalSeparator = '.'

        val format = DecimalFormat("#,###")
        format.decimalFormatSymbols = symbols
        return format
    }

    private fun formatStringToDouble(number: String): Double {
        return numberFormat().parse(number)?.toDouble() ?: 0.0
    }

    private fun String.isDigitOrDot(): Boolean {
        return (this.isDigitsOnly() || this.contains("."))
    }
}