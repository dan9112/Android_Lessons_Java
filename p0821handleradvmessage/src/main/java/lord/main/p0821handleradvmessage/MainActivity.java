package lord.main.p0821handleradvmessage;

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

import java.util.Random;
import java.util.concurrent.TimeUnit;

import lord.main.p0821handleradvmessage.databinding.MainBinding;

public class MainActivity extends AppCompatActivity {

    final static int STATUS_NONE = 0,
            STATUS_CONNECTING = 1,
            STATUS_CONNECTED = 2,
            STATUS_DOWNLOAD_START = 3,
            STATUS_DOWNLOAD_FILE = 4,
            STATUS_DOWNLOAD_END = 5,
            STATUS_DOWNLOAD_NONE = 6;

    Handler h;

    TextView tvStatus;
    ProgressBar pbDownload;
    Button btnConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainBinding binding = DataBindingUtil.setContentView(this, R.layout.main);

        tvStatus = binding.tvStatus;
        pbDownload = binding.pbDownload;
        btnConnect = binding.btnConnect;

        h = new Handler(Looper.getMainLooper()) {
          public void handleMessage(@NonNull Message msg) {
              switch (msg.what) {
                  case STATUS_NONE:
                      btnConnect.setEnabled(true);
                      tvStatus.setText("Not connected");
                      pbDownload.setVisibility(View.GONE);
                      break;
                  case STATUS_CONNECTING:
                      btnConnect.setEnabled(false);
                      tvStatus.setText("Connecting");
                      break;
                  case STATUS_CONNECTED:
                      tvStatus.setText("Connected");
                      break;
                  case STATUS_DOWNLOAD_START:
                      tvStatus.setText("Start download " + msg.arg1 + " files");
                      pbDownload.setMax(msg.arg1);
                      pbDownload.setProgress(0);
                      pbDownload.setVisibility(View.VISIBLE);
                      break;
                  case STATUS_DOWNLOAD_FILE:
                      tvStatus.setText("Downloading. Left " + msg.arg2 + " files");
                      pbDownload.setProgress(msg.arg1);
                      saveFile((byte[]) msg.obj);
                      break;
                  case STATUS_DOWNLOAD_END:
                      tvStatus.setText("Download complete!");
                      break;
                  case STATUS_DOWNLOAD_NONE:
                      tvStatus.setText("No files for download");
                      break;
              }
          }
        };
        h.sendEmptyMessage(STATUS_NONE);
    }

    public void onclick(View view) {
        Thread t = new Thread(new Runnable() {
            Message msg;
            byte[] file;
            final Random rand = new Random();

            @Override
            public void run() {
                try {
                    // устанавливаем подключение
                    h.sendEmptyMessage(STATUS_CONNECTING);
                    TimeUnit.SECONDS.sleep(1);

                    // подключение установлено
                    h.sendEmptyMessage(STATUS_CONNECTED);

                    // определяем кол-во файлов
                    TimeUnit.SECONDS.sleep(1);
                    int filesCount = rand.nextInt(5);

                    if (filesCount == 0) {
                        // сообщаем, что файлов для загрузки нет
                        h.sendEmptyMessage(STATUS_DOWNLOAD_NONE);
                        // и отключаемся
                        TimeUnit.MILLISECONDS.sleep(1500);
                        h.sendEmptyMessage(STATUS_NONE);
                        return;
                    }

                    // загрузка начинается
                    // создаём сообщение с информацией о количестве файлов
                    msg = h.obtainMessage(STATUS_DOWNLOAD_START, filesCount, 0);
                    // отправляем
                    h.sendMessage(msg);

                    for (int i = 1; i <= filesCount; i++) {
                        // загружается файл
                        file = downloadFile();
                        // создаём сообщение с информацией о порядковом номере
                        // файла, кол-вом оставшихся и самим файлом
                        msg = h.obtainMessage(STATUS_DOWNLOAD_FILE, i, filesCount - i, file);
                        // отправляем
                        h.sendMessage(msg);
                    }

                    // загрузка завершена
                    h.sendEmptyMessage(STATUS_DOWNLOAD_END);

                    // отключаемся
                    TimeUnit.MILLISECONDS.sleep(1500);
                    h.sendEmptyMessage(STATUS_NONE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    byte[] downloadFile() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        return new byte[1024];
    }

    void saveFile(byte[] file) {

    }
}