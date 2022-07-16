package com.topstack.aspro;

import com.alibaba.android.arouter.launcher.ARouter;
import com.topstack.common.ui.component.HiBaseApplication;

/**
 * @author Administrator
 */
public class HiApplication extends HiBaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }

        ARouter.init(this);
    }
}
