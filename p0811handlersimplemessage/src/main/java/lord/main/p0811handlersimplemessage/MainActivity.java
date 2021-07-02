package lord.main.p0811handlersimplemessage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.util.concurrent.TimeUnit;

import lord.main.p0811handlersimplemessage.databinding.MainBinding;

public class MainActivity extends AppCompatActivity {

    final int STATUS_NONE = 0, // нет подключения
            STATUS_CONNECTING = 1, // подключаемся
            STATUS_CONNECTED = 2; // поключено

    Handler h;

    TextView tvStatus;
    ProgressBar pbConnect;
    Button btnConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainBinding binding = DataBindingUtil.setContentView(this, R.layout.main);

        tvStatus = binding.tvStatus;
        pbConnect = binding.pbConnect;
        btnConnect = binding.btnConnect;

        h = new Handler(Looper.getMainLooper()) {
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case STATUS_NONE:
                        btnConnect.setEnabled(true);
                        tvStatus.setText(R.string.not_connected);
                        break;
                    case STATUS_CONNECTING:
                        btnConnect.setEnabled(false);
                        pbConnect.setVisibility(View.VISIBLE);
                        tvStatus.setText(R.string.connecting);
                        break;
                    case STATUS_CONNECTED:
                        pbConnect.setVisibility(View.GONE);
                        tvStatus.setText(R.string.connected);
                        break;
                }
            }
        };
        h.sendEmptyMessage(STATUS_NONE);
    }

    public void onclick(View view) {
        Thread t = new Thread(() -> {
            try {
                // устанавливаем подключение
                h.sendEmptyMessage(STATUS_CONNECTING);
                TimeUnit.SECONDS.sleep(2);

                // установлено
                h.sendEmptyMessage(STATUS_CONNECTED);

                // выполняется какая-то работа
                TimeUnit.SECONDS.sleep(3);

                // разрываем подключение
                h.sendEmptyMessage(STATUS_NONE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
    }
}