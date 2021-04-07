package ru.example.androidlessons1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.TimeUnit;

/**
 * Простой пример сервиса
 */
public class MyService extends Service {

    /**
     * Тег логов сервиса
     */
    private static final String LOG_TAG = "myLogs";

    /**
     * Флаг остановки потока задачи сервиса
     */
    private static boolean stop = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");
        someTask();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop = !stop;
        Log.d(LOG_TAG, "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind");
        return null;
    }

    /**
     * Функция с кодом нашего сервиса
     */
    private void someTask() {
        new Thread(() -> {
            // запуск бесконечного цикла выводов в лог похожих строк с задержкой в 1 секунду
            int i = 0;
            while (!stop) {
                i++;
                Log.d(LOG_TAG, "Log [" + i + "]");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Вывод чисел от одного до 5 с задержкой в 1 секунду
            // for (int i = 1; i <= 5; i++) {
            //     Log.d(LOG_TAG, "i = " + i);
            //     try {
            //         TimeUnit.SECONDS.sleep(1);
            //     } catch (InterruptedException e) {
            //         e.printStackTrace();
            //     }
            // }
            // stopSelf();
        }).start();
    }
}
