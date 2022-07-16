package com.topStack.architect.item;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.topStack.architect.R;
import com.topstack.hiui.item.HiDataItem;

/**
 * @author Administrator
 */
public class TopTabDataItem extends HiDataItem<ItemData, RecyclerView.ViewHolder> {
    public TopTabDataItem(ItemData itemData) {
        super(itemData);
    }

    @Override
    public void onBindData(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageView imageView = holder.itemView.findViewById(R.id.item_image);
        imageView.setImageResource(R.drawable.item_top_tab);
    }

    @Override
    public int getItemLayoutRes() {
        return R.layout.layout_list_item_top_tab;
    }
}
