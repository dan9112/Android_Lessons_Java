package ru.example.p0971_service_bind_client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    /**
     * Тег логов
     */
    final String LOG_TAG = "myLogs";

    /**
     * Флаг подключения к сервису
     */
    boolean bound = false;
    /**
     * Объект для связи с сервисом
     */
    ServiceConnection sConn;
    /**
     * Экземпляр класса {@link Intent}.
     * <p>
     * Взаимодействует с другими активностями/сервисами в соответствии с настройками
     * </p>
     */
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        intent = new Intent("ru.startandroid.develop.p0972servicebindserver.MyService");
        intent.setPackage("ru.example.p0972_service_bind_server");

        sConn = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.d(LOG_TAG, "MainActivity onServiceConnected");
                bound = true;
            }

            public void onServiceDisconnected(ComponentName name) {
                Log.d(LOG_TAG, "MainActivity onServiceDisconnected");
                bound = false;
            }
        };
    }

    /**
     * Метод обработки нажатия кнопки "Start".
     * <p>
     * Запускает сервис
     * </p>
     *
     * @param view виджет кнопки
     */
    public void onClickStart(View view) {
        startService(intent);
    }

    /**
     * Метод обработки нажатия кнопки "Stop".
     * <p>
     * Останавливает сервис
     * </p>
     *
     * @param view виджет кнопки
     */
    public void onClickStop(View view) {
        stopService(intent);
    }

    /**
     * Метод обработки нажатия кнопки "Bind".
     * <p>
     * Соединяет с сервисом
     * </p>
     *
     * @param view виджет кнопки
     */
    public void onClickBind(View view) {
        bindService(intent, sConn, BIND_AUTO_CREATE);
    }

    /**
     * Метод обработки нажатия кнопки "Unbind".
     * <p>
     * Отсоединяет от сервиса, если соединение установлено
     * </p>
     *
     * @param view виджет кнопки
     */
    public void onClickUnBind(View view) {
        if (!bound) return;
        unbindService(sConn);
        bound = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onClickUnBind(null);
    }
}