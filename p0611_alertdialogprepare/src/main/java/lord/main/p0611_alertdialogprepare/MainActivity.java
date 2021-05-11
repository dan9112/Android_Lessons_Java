package lord.main.p0611_alertdialogprepare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.sql.Date;

public class MainActivity extends AppCompatActivity {

    final static String LOG_TAG = "myLogs";
    final int DIALOG = 1;
    SimpleDateFormat sdf =  new SimpleDateFormat("HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onclick(View view) {
        showDialog(DIALOG);
    }

    protected Dialog onCreateDialog(int id) {
        Log.d(LOG_TAG, "onCreateDialog");
        if (id == DIALOG) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Текущее время");
            adb.setMessage(sdf.format(new Date(System.currentTimeMillis())));
            return adb.create();
        }
        return super.onCreateDialog(id);
    }

    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        Log.d(LOG_TAG,"onPrepareDialog");
        if (id == DIALOG) {
            ((AlertDialog) dialog).setMessage(sdf.format(new Date(System.currentTimeMillis())));
        }
    }
}