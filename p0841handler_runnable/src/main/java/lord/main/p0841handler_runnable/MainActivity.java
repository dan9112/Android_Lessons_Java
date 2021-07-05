package lord.main.p0841handler_runnable;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.util.concurrent.TimeUnit;

import lord.main.p0841handler_runnable.databinding.MainBinding;

public class MainActivity extends AppCompatActivity {
    final static String LOG_TAG = "myLogs";
    final int max = 100;
    ProgressBar pbCount;
    TextView tvInfo;
    CheckBox chbInfo;
    int cnt;
    Handler h;
    // обновление ProgressBar
    Runnable updateProgress = new Runnable() {
        @Override
        public void run() {
            pbCount.setProgress(cnt);
        }
    };
    // показ информации
    Runnable showInfo = new Runnable() {
        @Override
        public void run() {
            Log.d(LOG_TAG, "showInfo");
            tvInfo.setText("Count = " + cnt);
            // планирует сам себя через 1 сек
            h.postDelayed(showInfo, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainBinding binding = DataBindingUtil.setContentView(this, R.layout.main);

        h = new Handler(Looper.getMainLooper());

        pbCount = binding.pbCount;
        pbCount.setMax(max);
        pbCount.setProgress(0);

        tvInfo = binding.tvInfo;

        chbInfo = binding.chbInfo;
        chbInfo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tvInfo.setVisibility(View.VISIBLE);
                // показываем информацию
                h.post(showInfo);
            } else {
                tvInfo.setVisibility(View.GONE);
                // отменяем показ информации
                h.removeCallbacks(showInfo);
            }
        });

        Thread t = new Thread(() -> {
            try {
                for (cnt = 1; cnt < max; cnt++) {
                    TimeUnit.MILLISECONDS.sleep(100);
                    // обновляем ProgressBar
                    h.post(updateProgress);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
    }
}