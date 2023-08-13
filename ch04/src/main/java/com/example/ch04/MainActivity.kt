package com.example.ch04

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.ch04.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

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
                Uri.parse("tel:${binding.telTextView.toString()}"))
            startActivity(intent)
        }
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

    private fun initContent() {
        binding.nameTextView.text=""
        binding.birthTextView.text=""
        binding.bloodTextView.text=""
        binding.telTextView.text=""
        binding.detailTextView.text=""
    }
}