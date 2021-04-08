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

    // private static ArrayList<Integer> commands = new ArrayList<>();

    /**
     * Тег логов сервиса
     */
    private static final String LOG_TAG = "myLogs";

    /**
     * Флаг использования сервиса
     */
    private static boolean isNeeded = true;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand with id: " + startId);
        // commands.add(startId);
        someTask(startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isNeeded = !isNeeded;
        Log.d(LOG_TAG, "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind");
        return null;
    }

    /**
     * Функция с кодом нашего сервиса.
     * <p>
     * Выполнение потока завязано на существование родительского сервиса:
     * как только произойдёт событие onDestroy, булевая переменная
     * бысконечного цикла инвертирует значение и завершить все дочерние
     * потоки
     * </p>
     *
     * @param id номер, под которым была запущена команда
     */
    private void someTask(int id) {
        new Thread(() -> {
            // запуск бесконечного цикла выводов в лог похожих строк с задержкой в 1 секунду
            int i = 0;
            while (isNeeded) {
                i++;
                Log.d(LOG_TAG, "Log [" + id + "] - " + i);
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
            stopSelf();
        }).start();
    }
}
