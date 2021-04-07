package ru.example.androidlessons1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onClickStart(View view) {
        startService(new Intent(this, MyService.class));
    }

    public void onClickStop(View view) {
        stopService(new Intent(this, MyService.class));
    }
}