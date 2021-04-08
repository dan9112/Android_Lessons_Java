package ru.example.p0931_service_stop;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Реализация своего сервиса
 */
public class MyService extends Service {
    /**
     * Тег логов
     */
    private static final String LOG_TAG = "myLogs";
    /**
     * Экземпляр интерфейса для управления исполнением и контроля
     * потоков
     */
    ExecutorService es;
    /**
     * Некоторый абстрактный объект ресурсов
     */
    Object someRes;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "MyService onCreate");
        es = Executors.newFixedThreadPool(1);
        someRes = new Object();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "MyService onDestroy");
        someRes = null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "MyService onStartCommand");
        int time = intent.getIntExtra("time", 1);
        MyRun mr = new MyRun(time, startId);
        es.execute(mr);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Свой класс с реализации интерфейса для создания потоков
     */
    private class MyRun implements Runnable {

        /**
         * Время задержки
         */
        private final int time;
        /**
         * Порядковый номер запроса сервиса
         */
        private final int startId;

        /**
         * Конструктор получения необходимых параметров. Выводит в
         * лог строку с сообщением о создании объекта
         *
         * @param time    время задержки
         * @param startId порядковый номер запроса сервиса
         */
        public MyRun(int time, int startId) {
            this.time = time;
            this.startId = startId;
            Log.d(LOG_TAG, "MyRun#" + startId + " create");
        }

        @Override
        public void run() {
            Log.d(LOG_TAG, "MyRun#" + startId + " start, time = " + time);
            try {
                TimeUnit.SECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Log.d(LOG_TAG, "MyRun#" + startId + " someRes = " + someRes.getClass());
            } catch (NullPointerException e) {
                Log.d(LOG_TAG, "MyRun#" + startId + " error, null pointer");
            }
            stop();
        }

        /**
         * Функция, информирующая сервис о завершении выполнения
         * команды по её {@link #startId}. Также выводит информацию
         * о сыбытии в лог
         */
        private void stop() {
            Log.d(LOG_TAG, "MyRun#" + startId + " end, stopSelf(" + startId + ")");
            stopSelf(startId);
        }
    }
}
