package ru.example.p0942_service_kill_server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class MyService extends Service {

    /**
     * Тег логов
     */
    private static final String LOG_TAG = "myLogs";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "MyService onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "MyService onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId) {
        Log.d(LOG_TAG, "MyService onStartCommand, name = " +
                intent.getStringExtra("name"));
        readFlag(flag);
        MyRun mr = new MyRun(startId);
        new Thread(mr).start();
        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    /**
     * Вывод в лог полученного сервисом флага.
     * <p>
     * Расшифровывает из кода
     * </p>
     *
     * @param flag код флага
     */
    void readFlag(int flag) {
        if ((flag & START_FLAG_REDELIVERY) == START_FLAG_REDELIVERY)
            Log.d(LOG_TAG, "START_FLAG_REDELIVERY");
        if ((flag & START_FLAG_RETRY) == START_FLAG_RETRY)
            Log.d(LOG_TAG, "START_FLAG_RETRY");
    }

    /**
     * Класс, который эмулирует долгую работу.
     * <p>
     * Ожидает 15 секунд и вызывает {@link #stopSelf(int)}
     * </p>
     */
    class MyRun implements Runnable {

        /**
         * Порядковый номер запроса сервиса
         */
        int startId;

        /**
         * Конструктор получения необходимых параметров. Выводит в
         * лог строку с сообщением о создании объекта
         *
         * @param startId порядковый номер запроса сервиса
         */
        public MyRun(int startId) {
            this.startId = startId;
            Log.d(LOG_TAG, "MyRun#" + startId + " create");
        }

        @Override
        public void run() {
            Log.d(LOG_TAG, "MyRun#" + startId + " start");
            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stop();
        }

        /**
         * Функция, информирующая сервис о завершении выполнения
         * команды по её {@link #startId}. Также выводит информацию
         * о сыбытии в лог
         */
        void stop() {
            Log.d(LOG_TAG, "MyRun#" + startId + " end, stopSelfResult("
                    + startId + ") = " + stopSelfResult(startId));
        }
    }
}
