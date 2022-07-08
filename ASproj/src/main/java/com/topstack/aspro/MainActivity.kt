package com.topstack.aspro

import android.os.Bundle
import com.topstack.common.ui.component.HiBaseActivity

class MainActivity : HiBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}