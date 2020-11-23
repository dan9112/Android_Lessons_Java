package ru.startandroid.p0211two_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    Button btnActTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnActTwo = (Button) findViewById(R.id.btnActTwo);
        btnActTwo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnActTwo:
                Intent intent = new Intent(this, ActivityTwo.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
