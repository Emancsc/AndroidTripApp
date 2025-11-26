package com.example.assignment1;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.d(TAG,"On OnCreate() State");
    }


    public void onStart(){
        super.onStart();
        Log.d(TAG,"On OnStart() State");

    }

    public void onResume(){
        super.onResume();
        Log.d(TAG,"On onResume() State");

    }


    public void onPause(){
        super.onPause();
        Log.d(TAG,"On onPause() State");

    }

    public void onStop(){
        super.onStop();
        Log.d(TAG,"On onStop() State");

    }

    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"On onDestroy() State");

    }








}