package com.topstack.aspro.route

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.topstack.aspro.R
import com.topstack.common.ui.view.EmptyView

/**
 * 全局的统一错误页
 */
@Route(path = "/degrade/global/activity")
class DegradeGlobalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.layout_global_degrade)
        val emptyView = findViewById<EmptyView>(R.id.empty_view)

        emptyView.setIcon(R.string.if_unexpected1)
        emptyView.setTitle(getString(R.string.degrade_tips))
    }
}