package com.topstack.aspro.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.topstack.aspro.R;
import com.topstack.common.ui.component.HiBaseFragment;

/**
 * @author Administrator
 */
public class HomePageFragment extends HiBaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutView.findViewById(R.id.profile).setOnClickListener(v -> navigation("/profile/detail"));
        layoutView.findViewById(R.id.vip).setOnClickListener(v -> navigation("/profile/vip"));
        layoutView.findViewById(R.id.authentication).setOnClickListener(v -> navigation("/profile/authentication"));
        layoutView.findViewById(R.id.unKnow).setOnClickListener(v -> navigation("/profile/unKnow"));
    }

    void navigation(String path) {
        ARouter.getInstance().build(path).navigation(getContext());
    }
}
