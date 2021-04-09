package ru.example.p0951_service_back_pendingintent;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Основная активность приложения.
 * <p>
 * Запускает сервис {@link MyService}, взаимодействует с ним, отправляя и получая данные
 * </p>
 */
public class MainActivity extends AppCompatActivity implements Codes{

    /**
     * Код запроса для Task1
     */
    final int TASK1_CODE = 1,
    /**
     * Код запроса для Task2
     */
    TASK2_CODE = 2,
    /**
     * Код запроса для Task3
     */
    TASK3_CODE = 3;

    /**
     * Текстовое окно вывода для Task1
     */
    TextView tvTask1,
    /**
     * Текстовое окно вывода для Task2
     */
    tvTask2,
    /**
     * Текстовое окно вывода для Task3
     */
    tvTask3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tvTask1 = findViewById(R.id.tvTask1);
        tvTask1.setText("Task1");

        tvTask2 = findViewById(R.id.tvTask2);
        tvTask2.setText("Task2");

        tvTask3 = findViewById(R.id.tvTask3);
        tvTask3.setText("Task3");
    }

    /**
     * Функция обработки нажатия на кнопку запуска.
     * <p>
     * Запускает серию вызовов сервиса {@link MyService} с различными входными данными
     * </p>
     *
     * @param view виджет кнопки
     */
    public void onClickStart(View view) {
        PendingIntent pi;
        Intent intent;

        // Создаём PendingIntent для Task1
        pi = createPendingResult(TASK1_CODE, new Intent(), 0);
        // Создаём Intent для вызова сервиса, кладём туда параметр времени и созданный PendingIntent
        intent = new Intent(this, MyService.class).putExtra(PARAM_TIME, 7)
                .putExtra(PARAM_PINTENT, pi);
        // Запускаем сервис
        startService(intent);

        // Создаём PendingIntent для Task2
        pi = createPendingResult(TASK2_CODE, new Intent(), 0);
        // Создаём Intent для вызова сервиса, кладём туда параметр времени и созданный PendingIntent
        intent = new Intent(this, MyService.class).putExtra(PARAM_TIME, 4)
                .putExtra(PARAM_PINTENT, pi);
        // Запускаем сервис
        startService(intent);

        // Создаём PendingIntent для Task3
        pi = createPendingResult(TASK3_CODE, new Intent(), 0);
        // Создаём Intent для вызова сервиса, кладём туда параметр времени и созданный PendingIntent
        intent = new Intent(this, MyService.class).putExtra(PARAM_TIME, 6)
                .putExtra(PARAM_PINTENT, pi);
        // Запускаем сервис
        startService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(LOG_TAG, "requestCode = " + requestCode + ", resultCode = "
                + resultCode);

        // Ловим сообщения о запуске задач
        if (resultCode == STATUS_START) {
            switch (requestCode) {
                case TASK1_CODE:
                    tvTask1.setText("Task1 start");
                    break;
                case TASK2_CODE:
                    tvTask2.setText("Task2 start");
                    break;
                case TASK3_CODE:
                    tvTask3.setText("Task3 start");
                    break;
            }
        }

        // Ловим сообщения об окончании задач
        if (resultCode == STATUS_FINISH) {
            int result = data.getIntExtra(PARAM_RESULT, 0);
            switch (requestCode) {
                case TASK1_CODE:
                    tvTask1.setText("Task1 finish, result = " + result);
                    break;
                case TASK2_CODE:
                    tvTask2.setText("Task2 finish, result = " + result);
                    break;
                case TASK3_CODE:
                    tvTask3.setText("Task3 finish, result = " + result);
                    break;
            }
        }
    }
}