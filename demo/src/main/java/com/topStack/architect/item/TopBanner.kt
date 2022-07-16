package com.topStack.architect.item

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.topStack.architect.R
import com.topstack.hiui.item.HiDataItem

class TopBanner(data: ItemData) : HiDataItem<ItemData, RecyclerView.ViewHolder>(data) {


    override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.item_image)
        imageView.setImageResource(R.drawable.item_banner)
    }

    override fun getItemLayoutRes(): Int {
        return R.layout.layout_list_item_banner
    }
}