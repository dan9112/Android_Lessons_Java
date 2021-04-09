package ru.example.p0961_service_back_broadcast;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Пример сервиса c обменом данных.
 * <p>
 * Обмен через BroadcastReceiver в {@link MainActivity}
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
        int task = intent.getIntExtra(PARAM_TASK, 0);

        MyRun mr = new MyRun(startId, time, task);
        es.execute(mr);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    /**
     * Свой класс с реализацией интерфейса
     */
    class MyRun implements Runnable {

        /**
         * Время задержки
         */
        int time,
        /**
         * Порядковый номер запроса сервиса
         */
        startId,
        /**
         * Код задачи
         */
        task;

        /**
         * Слушатель широковещательных сообщений
         * <p>
         * !Тест возможности получения сообщений по широковещательному каналу!
         * </p>
         */
        BroadcastReceiver br;


        /**
         * Конструктор получения необходимых параметров. Выводит в лог строку с сообщением о
         * создании объекта
         *
         * @param startId порядковый номер запроса сервиса
         * @param time    время задержки
         * @param task    код задачи
         */
        public MyRun(int startId, int time, int task) {
            this.time = time;
            this.startId = startId;
            this.task = task;
            Log.d(LOG_TAG, "MyRun#" + startId + " create");



            br = new BroadcastReceiver() {
                // действия при получении сообщений
                @Override
                public void onReceive(Context context, Intent intent) {
                    int hero = intent.getIntExtra(CODE_IN, 0);
                    int startId = intent.getIntExtra("sI", -1);
                    // Вывод полученных данных и задачи, вызвавшей их в лог
                    Log.d(LOG_TAG, "onReceive: hero = " + hero + ", startId = " + startId);

                    stop(startId);
                }
            };
            // создаем фильтр для BroadcastReceiver
            IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION1 + startId);
            // регистрируем (включаем) BroadcastReceiver
            registerReceiver(br, intFilt);
        }

        @Override
        public void run() {
            Intent intent = new Intent(BROADCAST_ACTION);
            Log.d(LOG_TAG, "MyRun#" + startId + " start, time = " + time);
            try {
                // сообщаем о старте задачи
                intent.putExtra(PARAM_TASK, task);
                intent.putExtra(PARAM_STATUS, STATUS_START);
                sendBroadcast(intent);

                // начинаем выполнение задачи
                TimeUnit.SECONDS.sleep(time);

                // сообщаем об окончании задачи
                intent.putExtra(PARAM_STATUS, STATUS_FINISH);
                intent.putExtra(PARAM_RESULT, time * 100);

                intent.putExtra(CODE_OUT, 13);
                intent.putExtra("sI", startId);

                sendBroadcast(intent);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * Функция, информирующая сервис о завершении выполнения команды по её {@link #startId}.
         * Также выводит информацию о сыбытии в лог
         */
        void stop(int startId) {
            // дерегистрируем (выключаем) BroadcastReceiver
            unregisterReceiver(br);

            Log.d(LOG_TAG, "MyRun#" + startId + " end, stopSelfResult("
                    + startId + ") = " + stopSelfResult(startId));
        }
    }
}