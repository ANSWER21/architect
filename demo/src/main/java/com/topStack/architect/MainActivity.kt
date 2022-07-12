package com.topStack.architect

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.topStack.architect.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLog.setOnClickListener {
            startActivity(Intent(this, HiLogDemoActivity::class.java))
        }
        binding.btnTabBottom.setOnClickListener {
            startActivity(Intent(this, HiTabBottomDemoActivity::class.java))
        }
        binding.btnTabTop.setOnClickListener {
            startActivity(Intent(this, HiTabTopDemoActivity::class.java))
        }
        binding.btnRefresh.setOnClickListener {
            startActivity(Intent(this, HiRefreshDemoActivity::class.java))
        }
    }
}