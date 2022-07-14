package com.topStack.architect.banner

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.topStack.architect.R
import com.topstack.hiui.banner.HiBanner
import com.topstack.hiui.banner.core.HiBannerMo
import com.topstack.hiui.banner.indicator.HiCircleIndicator
import com.topstack.hiui.banner.indicator.HiIndicator
import com.topstack.hiui.banner.indicator.HiNumIndicator

class HiBannerDemoActivity : AppCompatActivity() {
    private var urls = arrayOf(
        R.drawable.banner0,
        R.drawable.banner1,
        R.drawable.banner2,
        R.drawable.banner3,
        R.drawable.banner4,
        R.drawable.banner5,
        R.drawable.banner0,
        R.drawable.banner1
    )
    private var hiIndicator: HiIndicator<*>? = null
    private var autoPlay: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_banner_demo)
        initView(HiCircleIndicator(this), false)
        findViewById<Switch>(R.id.auto_play).setOnCheckedChangeListener { _, isChecked ->
            autoPlay = isChecked
            initView(hiIndicator, autoPlay)
        }
        findViewById<View>(R.id.tv_switch).setOnClickListener {
            //            mHiBanner.setAutoPlay(false)
            if (hiIndicator is HiCircleIndicator) {
                initView(HiNumIndicator(this), autoPlay)
            } else {
                initView(HiCircleIndicator(this), autoPlay)
            }

        }
    }

    private fun initView(hiIndicator: HiIndicator<*>?, autoPlay: Boolean) {
        this.hiIndicator = hiIndicator
        val mHiBanner = findViewById<HiBanner>(R.id.banner)
        val moList: MutableList<HiBannerMo> = ArrayList()
        for (i in 0..5) {
            val mo = BannerMo()
            mo.url = urls[i % urls.size]
            moList.add(mo)
        }
        mHiBanner!!.setHiIndicator(hiIndicator)
        mHiBanner.setAutoPlay(autoPlay)
        mHiBanner.setIntervalTime(2000)
        //自定义布局
        mHiBanner.setBannerData(R.layout.banner_item_layout, moList)
        mHiBanner.setBindAdapter { viewHolder, mo, position ->
            val imageView: ImageView = viewHolder.findViewById(R.id.iv_image)
            imageView.setBackgroundColor(Color.WHITE)
            Glide.with(this@HiBannerDemoActivity).load(mo.url).into(imageView)
            val titleView: TextView = viewHolder.findViewById(R.id.tv_title)
            titleView.text = mo.url.toString()
            Log.d("----position:", position.toString() + "url:" + mo.url)
        }

    }
}