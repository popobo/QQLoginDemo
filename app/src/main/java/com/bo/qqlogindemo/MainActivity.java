package com.bo.qqlogindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private EditText mAccount;
    private EditText mPassword;
    private Button mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qq_login_layout);
        // init view
        initView();
        // init click listener
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        File fileDir = this.getFilesDir();
        File file = new File(fileDir, "userinfo.txt");
        Log.d(TAG, "");
        try {
//            FileInputStream fileInputStream = this.openFileInput("userinfo.text");
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String userinfo = bufferedReader.readLine();
            String[] userinfoSplits = userinfo.split(":");
            String account = userinfoSplits[0];
            String password = userinfoSplits[1];
            // show data in view
            mAccount.setText(account);
            mPassword.setText(password);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initListener() {
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLoginEvent();
            }
        });
    }

    private void handleLoginEvent() {
        // get content from views
        String account = mAccount.getText().toString();
        String password = mPassword.getText().toString();

//        if (account.length() == 0) {
//            Toast.makeText(this, "account can not be empty", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (password.length() == 0) {
//            Toast.makeText(this, "password can not be empty", Toast.LENGTH_SHORT).show();
//            return;
//        }

        if (TextUtils.isEmpty(account)) {
            Toast.makeText(this, "account can not be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "password can not be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // store account and password
        saveUserInfo(account, password);
    }

    private void saveUserInfo(String account, String password) {
        Log.d(TAG, new Exception().getStackTrace()[0].getMethodName());

        // get cache file storage dir
        // cache file dir while be cleaned by the system according to situation
        File cacheDir = this.getCacheDir();
        Log.d(TAG, cacheDir.toString());

        // how to get file store path
        // fileDir is /data/data/com.bo.qqlogindemo

        try {
            File fileDir = this.getFilesDir();
            File file = new File(fileDir, "userinfo.txt");
            if (file.exists()){
                file.createNewFile();
            }
            OutputStream fos = new FileOutputStream(file);
            fos.write((account + ":" + password).getBytes());
            fos.flush();
            fos.close();
            Toast.makeText(this, "save data successfully", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        mAccount = this.findViewById(R.id.account);
        mPassword = this.findViewById(R.id.password);
        mLogin = this.findViewById(R.id.login);
    }
}