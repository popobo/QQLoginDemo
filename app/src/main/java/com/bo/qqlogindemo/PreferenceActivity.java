package com.bo.qqlogindemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.Nullable;

public class PreferenceActivity extends Activity implements CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "Preference Activity";
    private Switch mIsAllowUnkownSource;
    private SharedPreferences mSharedPrefrences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_layout);
        // find view

        mIsAllowUnkownSource = this.findViewById(R.id.isAllowUnknownAppsSources);
        mIsAllowUnkownSource.setOnCheckedChangeListener(this);
        mSharedPrefrences = this.getSharedPreferences("settings_info", MODE_PRIVATE);

        mIsAllowUnkownSource.setChecked(mSharedPrefrences.getBoolean("settings_info", false));
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        // save preference data
        Log.d(TAG, "current stae == " + b);
        SharedPreferences.Editor editor = mSharedPrefrences.edit();
        editor.putBoolean("state", b);
        editor.commit();
    }
}
