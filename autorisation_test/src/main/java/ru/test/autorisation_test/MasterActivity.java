package ru.test.autorisation_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MasterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText login, password;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master);

        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        ok = findViewById(R.id.ok_1);

        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, SlaveActivity.class);
        intent.putExtra("log", login.getText().toString());
        intent.putExtra("pas", password.getText().toString());
        startActivity(intent);
    }
}
