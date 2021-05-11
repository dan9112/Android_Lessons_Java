package lord.main.p0621_alertdialogitems;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    final int DIALOG_ITEMS = 1,
            DIALOG_ADAPTER = 2,
            DIALOG_CURSOR = 3;
    int cnt = 0;
    DB db;
    Cursor cursor;

    String[] data = {"one", "two", "three", "four"};

    /**
     * Обработчик нажатия на пункт списка диалогового окна
     */
    DialogInterface.OnClickListener myClickListener = (dialog, which) -> {
        // вывод в лог позиции нажатого элемента
        Log.d(LOG_TAG, "which = " + which);

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // открываем подключение к БД
        db = new DB(this);
        db.open();
        cursor = db.getAllData();
        startManagingCursor(cursor);
    }

    public void onclick(View view) {
        changeCount();
        switch (view.getId()) {
            case R.id.btnItems:
                showDialog(DIALOG_ITEMS);
                break;
            case R.id.btnAdapter:
                showDialog(DIALOG_ADAPTER);
                break;
            case R.id.btnCursor:
                showDialog(DIALOG_CURSOR);
                break;
            default:
                break;
        }
    }

    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        switch (id) {
            // массив
            case DIALOG_ITEMS:
                adb.setTitle(R.string.items);
                adb.setItems(data, myClickListener);
                break;
            // адаптер
            case DIALOG_ADAPTER:
                adb.setTitle(R.string.adapter);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, data);
                adb.setAdapter(adapter, myClickListener);
                break;
            // курсор
            case DIALOG_CURSOR:
                adb.setTitle(R.string.cursor);
                adb.setCursor(cursor, myClickListener, DB.COLUMN_TXT);
                break;
        }
        return adb.create();
    }

    protected void onPrepareDialog(int id, Dialog dialog) {
        // получаем доступ к адаптеру списка диалога
        AlertDialog aDialog = (AlertDialog) dialog;
        ListAdapter lAdapter = aDialog.getListView().getAdapter();

        switch (id) {
            case DIALOG_ITEMS:
            case DIALOG_ADAPTER:
                // проверка возможности преобразования
                if (lAdapter instanceof BaseAdapter) {
                    // преобразование и вызов метода уведомления о новых данных
                    BaseAdapter bAdapter = (BaseAdapter) lAdapter;
                    bAdapter.notifyDataSetChanged();
                }
                break;
            case DIALOG_CURSOR:
                break;
            default:
                break;
        }
    }

    /**
     * Замена значения счётчика
     */
    void changeCount() {
        cnt++;
        // обновление массива
        data[3] = String.valueOf(cnt);
        // обновление БД
        db.changeRec(4, String.valueOf(cnt));
        cursor.requery();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}