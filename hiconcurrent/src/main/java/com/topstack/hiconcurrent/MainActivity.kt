package com.topstack.hiconcurrent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.topstack.hiconcurrent.thread.SemaphoreDemo

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * 线程
         */
//        ConcurrentTest.concurrentTest()
//        AtomicDemo.atomicTest()
//        SynchronizedDemo.synchronizedTest()
//        ReentrantLockDemo.reentrantLockTest()
//        ReentrantReadWriteLockDemo.reentrantReadWriteLockTest()

        /**
         * 协程
         */
//        CoroutineScene.startScene1()
//        CoroutineScene.startScene2()

//        lifecycleScope.launch {
//            val content = CoroutineScene3.parseAssetsFile(assets, "config.json")
//            Log.i("coroutine", content)
//        }
//        Log.i("coroutine", "parseAssetsFile lifecycleScope end")


//        CountdownLatchDemo.countdownLatchTest()
        SemaphoreDemo.semaphoreTest()
    }
}