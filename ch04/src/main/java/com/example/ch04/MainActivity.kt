package com.example.ch04

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.ch04.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.editButton.setOnClickListener {
            val intent= Intent(this,EditActivity::class.java)
            startActivity(intent)
        }

        binding.telButton.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW,
                Uri.parse("tel:${binding.telTextView.text.toString().
                replace("-","")}"))
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        filLContent()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater=menuInflater
        inflater.inflate(R.menu.item_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.initText -> {
            initContent()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun filLContent() {
        with(getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)) {
            binding.nameTextView.text=getString(PERSON_NAME,
                getString(R.string.default_name))
            binding.birthTextView.text=getString(PERSON_BIRTH_DATE,
                getString(R.string.default_birth))
            binding.bloodTextView.text=getString(PERSON_BLOOD_TYPE,
            getString(R.string.default_bloodType))
            binding.telTextView.text=getString(PERSON_PHONE_NUMBER,
            getString(R.string.default_phone))

            val detailText=getString(PERSON_DETAIL,"")

            binding.detailGuideTextView.isVisible=detailText.isNullOrEmpty().not()
            binding.detailTextView.isVisible=detailText.isNullOrEmpty().not()

            if (!detailText.isNullOrEmpty()) {
                binding.detailTextView.text=detailText
            }
        }
    }

    private fun initContent() {
        binding.nameTextView.text=""
        binding.birthTextView.text=""
        binding.bloodTextView.text=""
        binding.telTextView.text=""
        binding.detailTextView.text=""
        with(getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE).edit()) {
            clear()
            apply()
        }
    }
}