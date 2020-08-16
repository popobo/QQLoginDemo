package com.bo.qqlogindemo;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SDCardDemoActivity extends Activity implements View.OnClickListener {

    private final String TAG = "MainActivity";

    private Button mWriteSdcardButton;
    private Button mCheckSdcardButton;
    private Button mGetScardInfoButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set layout
        setContentView(R.layout.activity_sdcard_layout);

        verifyStoragePermissions(this);

        initView();
        initListener();
    }

    private void initListener() {
        mWriteSdcardButton.setOnClickListener(this);
        mCheckSdcardButton.setOnClickListener(this);
        mGetScardInfoButton.setOnClickListener(this);
    }

    private void initView() {
        mWriteSdcardButton = this.findViewById(R.id.writeSdcard);
        mCheckSdcardButton = this.findViewById(R.id.checkSdcard);
        mGetScardInfoButton = this.findViewById(R.id.getSdcardInfo);
    }

    @Override
    public void onClick(View view) {
        if (view == mWriteSdcardButton) {
            // write data to sdcard
//            File sdcardPath = new File("/sdcard");
//            File file = new File(sdcardPath, "userinfo.txt");
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            Log.d(TAG, "ext-filepath:" + externalStorageDirectory);
            File file = new File(externalStorageDirectory, "userinfo.txt");
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write("test".getBytes());
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (view == mCheckSdcardButton) {
            String exState = Environment.getExternalStorageState();
            Log.d(TAG, exState);
            if (exState.equals(Environment.MEDIA_MOUNTED)) {
                Log.d(TAG, "sdcard is mounted");
            } else if (exState.equals(Environment.MEDIA_REMOVED)) {
                Log.d(TAG, "sdcard is removed");
            }
        } else if (view == mGetScardInfoButton) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            Log.d(TAG, "ext-filepath:" + externalStorageDirectory);
            long freeSpace = externalStorageDirectory.getFreeSpace();
            String formatFileSize = Formatter.formatFileSize(this, freeSpace);
            Log.d(TAG, "freespace:" + formatFileSize);
        }
    }

    private static final int REQUEST_ALL = 1;

    private static String[] PERMISSIONS_ALL = {
//            "android.permission.CAMERA",
//            "android.permission.RECORD_AUDIO",
//            "android.permission.INTERNET",
//            "android.permission.ACCESS_NETWORK_STATE"
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"
    };

    public static void verifyStoragePermissions(Activity activity) {
        try {
            int permission = 0;
            //检测所有需要的权限
            for(String temp : PERMISSIONS_ALL){
                permission = ActivityCompat.checkSelfPermission(activity, temp);
                if (permission != PackageManager.PERMISSION_GRANTED){
                    break;
                }
            }

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_ALL,REQUEST_ALL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
