package ru.example.p0931_service_stop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Класс, реализующий основную активность приложения
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    /**
     * Обработчик нажатия на кнопку "Start".
     * <p>
     * Трижды вызывает {@link MyService сервис} с разным временем
     * задержки
     * </p>
     *
     * @param view виджет кнопки
     */
    public void onClickStart(View view) {
        startService(new Intent(this, MyService.class).putExtra("time", 7));
        startService(new Intent(this, MyService.class).putExtra("time", 2));
        startService(new Intent(this, MyService.class).putExtra("time", 4));
    }
}