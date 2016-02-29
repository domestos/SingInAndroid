package com.example.vpelenskyi.androidsingin;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.example.vpelenskyi.androidsingin.R;

/**
 * Created by v.pelenskyi on 29.02.2016.
 */
public class SuccessActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.succeeded);
    }
}
