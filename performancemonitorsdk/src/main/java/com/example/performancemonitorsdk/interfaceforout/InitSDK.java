package com.example.performancemonitorsdk.interfaceforout;

import android.app.Application;
import com.example.performancemonitorsdk.function.Logger;
import com.squareup.leakcanary.LeakCanary;

public class InitSDK {

    public static synchronized void init(Application appCtx, boolean isWriter, Logger.Level level) {
        Logger.initialize(appCtx,isWriter, level);
        LeakCanary.install(appCtx);
    }
}
