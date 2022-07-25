package com.topStack.architect.executor

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.topStack.architect.R
import com.topstack.hilibrary.executor.HiExecutor

class HiExecutorDemoActivity : AppCompatActivity() {

    private var pause: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_executor_demo)

        //依次按优先级完成线程
        findViewById<View>(R.id.btn_1).setOnClickListener {
            for (priority in 0 until 10) {
                val finalPriority = priority
                HiExecutor.execute(priority) {
                    try {
                        Thread.sleep((1000 - finalPriority * 100).toLong())
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        //暂停及恢复
        findViewById<View>(R.id.btn_2).setOnClickListener {
            if (pause) {
                HiExecutor.resume()
            } else {
                HiExecutor.pause()
            }
            pause = !pause
        }

        findViewById<View>(R.id.btn_3).setOnClickListener {
            HiExecutor.execute(0, object : HiExecutor.Callable<String>() {
                override fun onBackground(): String {
                    Log.e(
                        "HiExecutorDemoActivity",
                        "onBackGround-当前线程是:${Thread.currentThread().name}"
                    )
                    return "我是异步任务的结果";
                }

                override fun onCompleted(t: String?) {
                    Log.e(
                        "HiExecutorDemoActivity",
                        "onCompleted-当前线程是:${Thread.currentThread().name}"
                    )
                    Log.e("HiExecutorDemoActivity", "onCompleted 任务结果是:${t}")
                }

            })
        }


    }

}