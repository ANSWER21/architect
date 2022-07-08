package com.topStack.architect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.topStack.architect.databinding.ActivityHiLogDemoBinding
import com.topstack.hilog.log.*
import com.topstack.hilog.log.HiLogConfig.JsonParser

class HiLogDemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHiLogDemoBinding

    var viewPrinter: HiViewPrinter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHiLogDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewPrinter = HiViewPrinter(this)
        HiLogManager.init(object : HiLogConfig() {
            override fun injectJsonParser(): JsonParser {
                return JsonParser { src -> Gson().toJson(src) }
            }

            override fun getGlobalTag(): String {
                return "MApplication"
            }

            override fun enable(): Boolean {
                return true
            }
        }, HiConsolePrinter())
        binding.btnLog.setOnClickListener {
            printLog()
        }
        viewPrinter!!.viewProvider.showFloatingView()
    }

    private fun printLog() {
        HiLogManager.getInstance().addPrinter(viewPrinter)
        //自定义Log配置
        HiLog.log(object : HiLogConfig() {
            override fun includeThread(): Boolean {
                return true
            }
        }, HiLogType.E, "---------", "5566")
        HiLog.a("9900")
    }

}