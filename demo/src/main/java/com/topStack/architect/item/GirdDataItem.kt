package com.topStack.architect.item

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.topStack.architect.R
import com.topstack.hiui.item.HiDataItem

class GirdDataItem(data: ItemData) : HiDataItem<ItemData, GirdDataItem.MyHolder>(data) {


    override fun onBindData(holder: MyHolder, position: Int) {
        holder.imageView!!.setImageResource(R.drawable.item_grid)
    }

    override fun getItemLayoutRes(): Int {
        return R.layout.layout_list_item_grid;
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView? = null

        init {
            imageView = itemView.findViewById<ImageView>(R.id.item_image)
        }
    }
}
