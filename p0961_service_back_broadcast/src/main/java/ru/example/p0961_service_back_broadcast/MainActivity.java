package ru.example.p0961_service_back_broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Основная активность приложения.
 * <p>
 * Запускает сервис {@link MyService}, взаимодействует с ним, отправляя и получая данные
 * </p>
 */
public class MainActivity extends AppCompatActivity implements Codes {

    /**
     * Код запроса для Task1
     */
    private final int TASK1_CODE = 1,
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
    private TextView tvTask1,
    /**
     * Текстовое окно вывода для Task2
     */
    tvTask2,
    /**
     * Текстовое окно вывода для Task3
     */
    tvTask3;

    /**
     * Слушатель широковещательных сообщений
     */
    private BroadcastReceiver br;

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

        // создаем BroadcastReceiver
        br = new BroadcastReceiver() {
            // действия при получении сообщений
            @Override
            public void onReceive(Context context, Intent intent) {
                int task = intent.getIntExtra(PARAM_TASK, 0);
                int status = intent.getIntExtra(PARAM_STATUS, 0);
                int hero = intent.getIntExtra(CODE_OUT, 0);
                Log.d(LOG_TAG, "onReceive: task = " + task + ", status = " + status);

                // Ловим сообщения о старте задач
                if (status == STATUS_START) {
                    switch (task) {
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
                if (status == STATUS_FINISH) {
                    int result = intent.getIntExtra(PARAM_RESULT, 0);
                    switch (task) {
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

                // Ловим моё сообщение
                if (hero == 13) {
                    int sI = intent.getIntExtra("sI", -1);
                    int i = 0;
                    switch (sI) {
                        case 1:
                            i = 111;
                            break;
                        case 2:
                            i = 222;
                            break;
                        case 3:
                            i = 333;
                            break;
                    }
                    sendBroadcast(new Intent(BROADCAST_ACTION1 + sI)
                            .putExtra(CODE_IN, i)
                            .putExtra("sI", sI));
                }
            }
        };
        // создаем фильтр для BroadcastReceiver
        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);
        // регистрируем (включаем) BroadcastReceiver
        registerReceiver(br, intFilt);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // дерегистрируем (выключаем) BroadcastReceiver
        unregisterReceiver(br);
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
        Intent intent;

        // Создаем Intent для вызова сервиса,
        // кладем туда параметр времени и код задачи
        intent = new Intent(this, MyService.class).putExtra(PARAM_TIME, 7)
                .putExtra(PARAM_TASK, TASK1_CODE);
        // стартуем сервис
        startService(intent);

        intent = new Intent(this, MyService.class).putExtra(PARAM_TIME, 4)
                .putExtra(PARAM_TASK, TASK2_CODE);
        startService(intent);

        intent = new Intent(this, MyService.class).putExtra(PARAM_TIME, 6)
                .putExtra(PARAM_TASK, TASK3_CODE);
        startService(intent);
    }
}