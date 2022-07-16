package com.topStack.architect.navigation

import androidx.fragment.app.Fragment
import com.topstack.nav_annotation.Destination

/**
 * 添加Destination将由NavProcessor处理器获取注解信息后存放到assets文件下的destination.json文件中
 */
@Destination(pageUrl = "notification")
class NotificationFragment : Fragment() {
}