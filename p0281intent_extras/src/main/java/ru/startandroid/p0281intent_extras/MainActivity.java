package ru.startandroid.p0281intent_extras;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {

    EditText etFName, etLName;
    Button btnSubmit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFName = findViewById(R.id.etFName);
        etLName = findViewById(R.id.etLName);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ViewActivity.class);
        intent.putExtra("fname", etFName.getText().toString());
        intent.putExtra("lname", etLName.getText().toString());
        startActivity(intent);
    }
}
