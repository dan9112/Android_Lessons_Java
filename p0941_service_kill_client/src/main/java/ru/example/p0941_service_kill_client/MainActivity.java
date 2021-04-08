package ru.example.p0941_service_kill_client;

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
        Intent intent = new Intent("lord.dan.service");
        intent.putExtra("name", "value");
        intent.setPackage("ru.example.p0942_service_kill_server");
        startService(intent);
    }
}
