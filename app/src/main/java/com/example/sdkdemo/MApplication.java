package com.example.sdkdemo;

import android.app.Application;
import com.example.performancemonitorsdk.function.Logger;
import com.example.performancemonitorsdk.interfaceforout.InitSDK;

public class MApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        InitSDK.init(this,false, Logger.Level.VERBOSE);
    }
}
