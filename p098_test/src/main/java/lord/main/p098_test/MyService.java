package lord.main.p098_test;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    MyBinder binder = new MyBinder();
    Thread thread;

    protected void start(int start) {
        thread = new MyThread(start);
        thread.start();
    }

    protected void stop() {
        thread.interrupt();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private class MyThread extends Thread {

        private int counter;

        MyThread(int start) {
            counter = start;
        }

        @Override
        public void run() {
            while (!isInterrupted()) {

                Intent intent = new Intent();
                intent.putExtra("message", counter);
                intent.setAction("com.com.com.lord");
                sendBroadcast(intent);

                counter++;
            }
        }
    }

    class MyBinder extends Binder {

        MyService getService() {
            return MyService.this;
        }
    }
}