package com.example.sdkdemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.performancemonitorsdk.function.Logger;

public class MainActivity extends AppCompatActivity {

    private static String TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TAG="TEST-Logger";
        TextView textView=(TextView)findViewById(R.id.text1);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });

        Logger.d(TAG,"debug");
        Logger.i(TAG,"info");
        Logger.d(TAG,"debug",new Exception());
        Logger.i(TAG,"info",new Exception());

        PermissionUtil.checkStoragePermission(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 100:
                if(grantResults[0]!= PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this,"请开启权限使用App",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }
}
