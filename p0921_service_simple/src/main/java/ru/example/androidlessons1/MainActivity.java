package ru.example.androidlessons1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Основная активность приложения
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    /**
     * Обработчик нажатия на кнопку запуска сервиса. Создаёт, если необходимо, и запускает {@link MyService Сервис}
     * @param view нажатый виджет (кнопка "Start Service")
     */
    public void onClickStart(View view) {
        startService(new Intent(this, MyService.class));
    }

    /**
     * Обработчик нажатия на кнопку остановки сервиса. Останавливает и уничтожает {@link MyService Сервис}
     * @param view нажатый виджет (кнопка "Stop Service")
     */
    public void onClickStop(View view) {
        stopService(new Intent(this, MyService.class));
    }
}