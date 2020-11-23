package ru.startandroid.p0121log_and_mess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvOut;
    Button btnOk;
    Button btnCancel;

    private static final String TAG = "myLogs";

    /* Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // найдем View-элементы
        Log.d(TAG, "Найдем View-элементы");
        tvOut = findViewById(R.id.tvOut);
        btnOk = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);

        // присваиваем обработчик кнопкам
        Log.d(TAG, "Присваиваем обработчик кнопкам");
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // по id определяем кнопку, вызвавшую этот обработчик
        Log.d(TAG, "По id определяем кнопку, вызвавшую этот обработчик");
        switch (v.getId()) {
            case R.id.btnOk:
                // кнопка ОК
                Log.d(TAG, "Кнопка ОК");
                tvOut.setText(R.string.bOK);
                Toast.makeText(this, "Нажата кнопка OK", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnCancel:
                // кнопка Cancel
                Log.d(TAG, "Кнопка Cancel");
                tvOut.setText(R.string.bCancel);
                Toast.makeText(this, "Нажата кнопка Cancel", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
