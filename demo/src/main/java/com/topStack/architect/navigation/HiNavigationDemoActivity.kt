package com.topStack.architect.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.topStack.architect.R
import com.topStack.architect.databinding.ActivityNavigationDemoBinding

class HiNavigationDemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        //寻找出路由控制器对象,它是我们路由跳转的唯一入口
        val navController: NavController = findNavController(R.id.nav_host_fragment)
        val hostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
        //需从nav_host_fragment中找到childFragmentManager，否则将无内容显示
        NavUtil.builderNavGraph(
            this,
            hostFragment!!.childFragmentManager,
            navController,
            R.id.nav_host_fragment
        )

        NavUtil.builderBottomBar(navView)

        navView.setOnNavigationItemSelectedListener { item ->
            navController.navigate(item.itemId)
            true
        }
        //将navController和BottomNavigationView绑定，形成联动效果
//        navView.setupWithNavController(navController)
//
//
//        navController!!.navigate(R.id.navigation_notifications)
//        navController!!.navigate(R.id.navigation_notifications, Bundle.EMPTY)
//        navController!!.navigate(Uri.parse("www.imooc.com"))
//
//        navController!!.navigateUp()
//        navController!!.popBackStack(R.id.navigation_dashboard,false)
    }
}