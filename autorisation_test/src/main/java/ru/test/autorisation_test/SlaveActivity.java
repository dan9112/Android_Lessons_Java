package ru.test.autorisation_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SlaveActivity extends AppCompatActivity implements View.OnClickListener {

    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slave);

        TextView login = findViewById(R.id.login_0),
                password = findViewById(R.id.password_0);
        ok = findViewById(R.id.ok_2);

        Intent intent = getIntent();

        login.setText(intent.getStringExtra("log"));
        password.setText(intent.getStringExtra("pas"));

        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.finish();
    }
}