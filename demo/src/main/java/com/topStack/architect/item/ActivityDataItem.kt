package com.topStack.architect.item

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.topStack.architect.R
import com.topstack.hiui.item.HiDataItem

class ActivityDataItem(data: ItemData?) :
    HiDataItem<ItemData, ActivityDataItem.ActivityHolder>(data!!) {
    override fun onBindData(holder: ActivityHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.item_image)
        imageView.setImageResource(R.drawable.item_activity)
    }


    override fun getItemLayoutRes(): Int {
        return R.layout.layout_list_item_activity
    }


    class ActivityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}