package lord.main.p098_test;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener {

    boolean bound = false;
    Intent intent;
    EditText display;
    ServiceConnection sConn;
    MyService myService;
    private Button start, stop;
    private int lastCounterValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        display = findViewById(R.id.display);
        start = findViewById(R.id.button2);
        stop = findViewById(R.id.button3);

        start.setOnLongClickListener(this);

        intent = new Intent(this, MyService.class);
        sConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myService = ((MyService.MyBinder) service).getService();
                bound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                bound = false;
            }
        };

        BroadcastReceiver messageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                lastCounterValue = intent.getIntExtra("message", 0);
                Log.d("myLogs", String.valueOf(lastCounterValue));

                // ненадолго блокирует интерфейс при каждом входящем сообщении
                //
                // в данном примере интерфейс блокируется полностью, так как поток входящих
                // сообщений черезмерно интенсивен: у пользователя нет возможности остановить
                // процесс после запуска
                // display.setText(display.getText() + "  " + lastCounterValue);
            }
        };

        registerReceiver(messageReceiver, new IntentFilter("com.com.com.lord"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(intent, sConn, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!bound) return;
        unbindService(sConn);
        bound = false;
    }

    public void onClickStart(View view) {
        startService(intent);
        Toast.makeText(this, "Соединились", Toast.LENGTH_SHORT).show();
        start.setEnabled(true);
        view.setEnabled(false);
    }

    public void onClickDo(View view) {
        myService.start(0);
        Toast.makeText(this, "Запустили", Toast.LENGTH_SHORT).show();
        stop.setEnabled(true);
        start.setEnabled(false);
    }

    public void onClickStopDo(View view) {
        myService.stop();
        Toast.makeText(this, "Остановили", Toast.LENGTH_SHORT).show();
        stop.setEnabled(false);
        start.setEnabled(true);
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.button2) {
            myService.start(lastCounterValue);
            Toast.makeText(this, "Продолжили", Toast.LENGTH_SHORT).show();
            stop.setEnabled(true);
            start.setEnabled(false);

            return true;
        } else return false;
    }
}