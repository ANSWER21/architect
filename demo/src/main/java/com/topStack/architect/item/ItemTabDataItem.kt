package com.topStack.architect.item

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.topStack.architect.R
import com.topstack.hiui.item.HiDataItem


class ItemTabDataItem(data: ItemData?) :
    HiDataItem<ItemData, ItemTabDataItem.MyViewHolder>(data!!) {
    override fun onBindData(holder: MyViewHolder, position: Int) {
        holder.imageView.setImageResource(R.drawable.item_tab)
    }

    override fun getItemLayoutRes(): Int {
        return R.layout.layout_list_item_tab;
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView

        init {
            imageView = itemView.findViewById(R.id.item_image)
        }
    }
}
