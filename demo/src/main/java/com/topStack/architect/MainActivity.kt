package com.topStack.architect

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.topStack.architect.banner.HiBannerDemoActivity
import com.topStack.architect.databinding.ActivityMainBinding
import com.topStack.architect.executor.HiExecutorDemoActivity
import com.topStack.architect.item.HiItemDemoActivity
import com.topStack.architect.log.HiLogDemoActivity
import com.topStack.architect.navigation.HiNavigationDemoActivity
import com.topStack.architect.refresh.HiRefreshDemoActivity
import com.topStack.architect.tab.HiTabBottomDemoActivity
import com.topStack.architect.tab.HiTabTopDemoActivity

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
        binding.btnBanner.setOnClickListener {
            startActivity(Intent(this, HiBannerDemoActivity::class.java))
        }
        binding.btnItem.setOnClickListener {
            startActivity(Intent(this, HiItemDemoActivity::class.java))
        }
        binding.btnNavigation.setOnClickListener {
            startActivity(Intent(this, HiNavigationDemoActivity::class.java))
        }
        binding.btnExecutor.setOnClickListener {
            startActivity(Intent(this, HiExecutorDemoActivity::class.java))
        }
    }
}