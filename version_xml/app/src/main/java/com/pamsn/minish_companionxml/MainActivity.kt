package com.pamsn.minish_companionxml

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pamsn.minish_companionxml.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navToDash = {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener { navToDash() }
        binding.btnRegister.setOnClickListener { navToDash() }
        binding.btnGuest.setOnClickListener { navToDash() }
    }
}
