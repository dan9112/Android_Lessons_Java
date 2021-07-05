package lord.main.p0831handlermessagemanage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class MainActivity extends AppCompatActivity {

    final static String LOG_TAG = "myLogs";

    Handler h;

    Handler.Callback hc = msg -> {
        Log.d(LOG_TAG, "what = " + msg.what);
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.main);

        h = new Handler(Looper.getMainLooper(), hc);
        sendMessages();
    }

    void sendMessages() {
        Log.d(LOG_TAG, "send messages");
        h.sendEmptyMessageDelayed(1, 1000);
        h.sendEmptyMessageDelayed(2, 2000);
        h.sendEmptyMessageDelayed(3, 3000);
        h.removeMessages(2);
    }
}