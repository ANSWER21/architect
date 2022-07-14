package com.topstack.hiui.banner.core;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.topstack.hiui.banner.indicator.HiIndicator;

import java.util.List;

/**
 * @author Administrator
 */
public interface IHiBanner {
    void setBannerData(@LayoutRes int layoutResId, @NonNull List<? extends HiBannerMo> models);

    void setBannerData(@NonNull List<? extends HiBannerMo> models);

    void setHiIndicator(HiIndicator hiIndicator);

    /**
     * 是否自动播放
     *
     * @param autoPlay
     */
    void setAutoPlay(boolean autoPlay);

    void setLoop(boolean loop);

    void setIntervalTime(int intervalTime);

    void setBindAdapter(IBindAdapter bindAdapter);

    void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener);

    void setScrollDuration(int duration);

    void setOnBannerClickListener(OnBannerClickListener onBannerClickListener);

    interface OnBannerClickListener {
        void onBannerClick(@NonNull HiBannerAdapter.HiBannerViewHolder viewHolder, @NonNull HiBannerMo bannerMo, int position);
    }
}
