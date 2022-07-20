package com.topstack.hiconcurrent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ConcurrentTest.concurrentTest()
        //AtomicDemo.atomicTest()
        //SynchronizedDemo.synchronizedTest()
        //ReentrantLockDemo.reentrantLockTest()
        ReentrantReadWriteLockDemo.reentrantReadWriteLockTest()
    }
}