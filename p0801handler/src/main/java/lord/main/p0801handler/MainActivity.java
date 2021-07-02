package lord.main.p0801handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import lord.main.p0801handler.databinding.MainBinding;

public class MainActivity extends AppCompatActivity {

    final static String LOG_TAG = "myLogs";

    Handler h;
    TextView tvInfo;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainBinding binding = DataBindingUtil.setContentView(this, R.layout.main);

        tvInfo = binding.tvInfo;
        btnStart = binding.btnStart;

        h = new MyHandler(Looper.getMainLooper(), this);

        btnStart.setOnClickListener(view -> {
            view.setEnabled(false);
            Thread t = new Thread(() -> {
                for (int i = 1; i <= 10; i++) {
                    // долгий процесс
                    downloadFile();
                    h.sendEmptyMessage(i);
                    // пишем лог
                    Log.d(LOG_TAG, "i = " + i);
                }
            });
            t.start();
        });
        binding.btnTest.setOnClickListener(view -> Log.d(LOG_TAG, "test"));
    }

    void downloadFile() {
        // пауза - 1 секунда
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if (h != null) h.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    static class MyHandler extends Handler {
        WeakReference<MainActivity> wrActivity;

        public MyHandler(Looper looper, MainActivity activity) {
            super(looper);
            wrActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            MainActivity activity = wrActivity.get();
            if (activity != null) {
                // обновляем TextView
                activity.tvInfo.setText(activity.getString(R.string.downloaded, msg.what));
                if (msg.what == 10) activity.btnStart.setEnabled(true);
            }
        }
    }
}