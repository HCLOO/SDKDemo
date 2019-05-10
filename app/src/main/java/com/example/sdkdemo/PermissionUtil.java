package com.example.sdkdemo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionUtil {

    public static boolean checkStoragePermission(Activity activity) {
        boolean flag=false;
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
        } else {
            flag=true;
        }
        return flag;
    }
}
