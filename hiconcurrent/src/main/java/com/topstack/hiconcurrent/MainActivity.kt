package com.topstack.hiconcurrent

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.topstack.hiconcurrent.coroutine.CoroutineScene3
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * 线程
         */
        //ConcurrentTest.concurrentTest()
        //AtomicDemo.atomicTest()
        //SynchronizedDemo.synchronizedTest()
        //ReentrantLockDemo.reentrantLockTest()
        //ReentrantReadWriteLockDemo.reentrantReadWriteLockTest()

        /**
         * 协程
         */
        //CoroutineScene.startScene1()
        //CoroutineScene.startScene2()

        lifecycleScope.launch {
            val content = CoroutineScene3.parseAssetsFile(assets, "config.json")
            Log.i("coroutine", content)
        }
        Log.i("coroutine", "parseAssetsFile lifecycleScope end")
    }
}