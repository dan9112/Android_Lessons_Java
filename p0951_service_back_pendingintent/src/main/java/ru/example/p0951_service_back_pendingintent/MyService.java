package ru.example.p0951_service_back_pendingintent;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Пример сервиса c обменом данных.
 * <p>
 * Обмен через PendingIntent
 * </p>
 */
public class MyService extends Service implements Codes {

    /**
     * Экземпляр интерфейса для управления исполнением и контроля потоков
     */
    private ExecutorService es;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(LOG_TAG, "MyService onCreate");
        es = Executors.newFixedThreadPool(2);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "MyService onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "MyService onStartCommand");

        int time = intent.getIntExtra(PARAM_TIME, 1);
        PendingIntent pi = intent.getParcelableExtra(PARAM_PINTENT);

        MyRun mr = new MyRun(time, startId, pi);
        es.execute(mr);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Свой класс с реализацией интерфейса
     */
    private class MyRun implements Runnable {

        /**
         * Время задержки
         */
        private final int time,
        /**
         * Порядковый номер запроса сервиса
         */
        startId;

        /**
         * Объект для связи с активностью, сделавшей запрос
         */
        PendingIntent pi;

        /**
         * Конструктор получения необходимых параметров. Выводит в лог строку с сообщением о
         * создании объекта
         *
         * @param time    время задержки
         * @param startId порядковый номер запроса сервиса
         * @param pi      объект для связи с активностью, сделавшей запрос
         */
        public MyRun(int time, int startId, PendingIntent pi) {
            this.time = time;
            this.startId = startId;
            this.pi = pi;
            Log.d(LOG_TAG, "MyRun#" + startId + " create");
        }

        @Override
        public void run() {
            Log.d(LOG_TAG, "MyRun#" + startId + " start, time = " + time);
            try {
                // Сообщаем о запуске задачи
                pi.send(STATUS_START);

                // Начинаем выполнение задачи
                TimeUnit.SECONDS.sleep(time);

                // Сообщаем об окончании задачи
                Intent intent = new Intent().putExtra(PARAM_RESULT, time * 100);
                pi.send(MyService.this, STATUS_FINISH, intent);
            } catch (InterruptedException | PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
            stop();
        }

        /**
         * Функция, информирующая сервис о завершении выполнения команды по её {@link #startId}.
         * Также выводит информацию о сыбытии в лог
         */
        void stop() {
            Log.d(LOG_TAG, "MyRun#" + startId + " end, stopSelfResult(" + startId + ") = "
                    + stopSelfResult(startId));
        }
    }
}