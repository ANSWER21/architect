package com.topstack.aspro.route

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor

@Interceptor(name = "biz_interceptor", priority = 9)
class BizInterceptor : IInterceptor {
    private var context: Context? = null

    override fun init(context: Context?) {
        this.context = context
    }

    /**
     * 运行在子线程
     */
    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        val flag = postcard!!.extra
        //按位与操作
        if (flag.and(RouteFlag.FLAG_LOGIN) != 0) {
            //login
            callback!!.onInterrupt(RuntimeException("need login"))
            showToast("请先登录")
        } else if (flag.and(RouteFlag.FLAG_AUTHENTICATION) != 0) {
            callback!!.onInterrupt(RuntimeException("need authentication"))
            showToast("请先实名认证")
        } else if (flag and (RouteFlag.FLAG_VIP) != 0) {
            callback!!.onInterrupt(RuntimeException("need become vip"))
            showToast("请先加入会员")
        } else {
            callback!!.onContinue(postcard)
        }
    }

    private fun showToast(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

}