package com.example.ch04

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.isVisible
import com.example.ch04.databinding.ActivityEditBinding


class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bloodTypeSpinner.adapter = ArrayAdapter.createFromResource(
            this, R.array.blood_type,
            android.R.layout.simple_list_item_1
        )

        binding.datePickLayer.setOnClickListener {
            val listener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                binding.birthPickTextView.text = "$year-${month.inc()}-$dayOfMonth"
            }
            DatePickerDialog(
                this,
                listener,
                1970,
                0,
                1
            ).show()
        }

        binding.detailCheckBox.setOnCheckedChangeListener { _, isChecked ->
            binding.detailInputEditText.isVisible = isChecked
        }

        binding.saveFloatingActionButton.setOnClickListener {
            saveData()
            finish()
        }
    }

    private fun saveData() {
        with(getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit()) {
            putString(PERSON_NAME, binding.nameInputEditText.text.toString())
            putString(PERSON_BIRTH_DATE, binding.birthPickTextView.text.toString())
            putString(PERSON_BLOOD_TYPE, getBloodType())
            putString(PERSON_PHONE_NUMBER, binding.telInputEditText.text.toString())
            putString(PERSON_DETAIL, getWarningDetails())
            apply()
        }
    }

    private fun getBloodType(): String {
        val bloodType = binding.bloodTypeSpinner.selectedItem.toString()
        val rhType = when (binding.rhBloodTypeRadioGroup.checkedRadioButtonId) {
            R.id.rhPlusBloodType -> binding.rhPlusBloodType.text.toString()
            else -> binding.rhMinusBloodType.text.toString()
        }
        return rhType + bloodType
    }

    private fun getWarningDetails(): String {
        return if (binding.detailCheckBox.isChecked) {
            binding.detailInputEditText.text.toString()
        } else {
            ""
        }
    }
}