package ru.test.library;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class DeleteActivity extends AppCompatActivity implements OnClickListener {

    final String LOG_TAG = "myLogs";
    Button dDel;
    TableLayout bIn;

    DBHelper dbHelper;

    String o;
    final int REQUEST_CODE_AUTHOR = 1,
            REQUEST_CODE_SERIA = 2;

    int[] colors = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete);

        colors[0] = Color.parseColor("#559966CC");
        colors[1] = Color.parseColor("#55336699");

        dDel = findViewById(R.id.dDel);
        dDel.setOnClickListener(this);

        bIn = findViewById(R.id.bIn);

        dbHelper = new DBHelper(this);

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String[] columns = new String[]{dbHelper.KEY_ID, dbHelper.KEY_NAME, dbHelper.KEY_AUTHOR, dbHelper.KEY_SERIA, dbHelper.KEY_NUM, dbHelper.KEY_READ};
        Cursor c = database.query(dbHelper.TABLE_CONTACTS, columns, null, null, null, null, null);

        if (c != null) {
            int i = 0;
            boolean cc = false;
            if (c.moveToFirst()) {
                do {
                    TableRow s = new TableRow(DeleteActivity.this);

                    CheckBox cb = new CheckBox(DeleteActivity.this);
                    s.addView(cb);
                    for (String cn : c.getColumnNames()) {
                        TextView r = new TextView(DeleteActivity.this);
                        String str = c.getString(c.getColumnIndex(cn));
                        if (str.length() == 0) str = " -- ";
                        if (cn.equals(dbHelper.KEY_READ)) {
                            if (str.equals("1")) str = "Прочитано";
                            else if (str.equals("0")) str = "Не прочитано";
                        }
                        if (cn.equals(dbHelper.KEY_ID)) r.setWidth(0);
                        r.setText(str);
                        r.setTextColor(Color.BLACK);
                        r.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        s.addView(r);
                    }
                    if (cc){
                        s.setBackgroundColor(colors[0]);
                        cc = false;
                    }
                    else if (!cc) cc = true;

                    bIn.addView(s, MATCH_PARENT, WRAP_CONTENT);
                } while (c.moveToNext());
            }
            c.close();
        } else Toast.makeText(this, "Таблица пусита", Toast.LENGTH_LONG).show();
        dbHelper.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.del_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    // обработка нажатий закончить!!!
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.oAutor:
                intent = new Intent(this, SamplingActivity.class);
                intent.putExtra(o, "author");
                startActivityForResult(intent, REQUEST_CODE_AUTHOR);
                break;
            case R.id.oSeria:
                intent = new Intent(this, SamplingActivity.class);
                intent.putExtra(o, "seria");
                startActivityForResult(intent, REQUEST_CODE_SERIA);
                break;
            case R.id.oRead:
                for (int i = 1; i < bIn.getChildCount(); i++) {
                    TableRow t1 = (TableRow) bIn.getChildAt(i);
                    String s1 = ((TextView) t1.getChildAt(6)).getText().toString();
                    if (s1.equals("Прочитано")) ((CheckBox) t1.getChildAt(0)).setChecked(true);
                }
                break;
            case R.id.oNRead:
                for (int i = 1; i < bIn.getChildCount(); i++) {
                    TableRow t1 = (TableRow) bIn.getChildAt(i);
                    String s1 = ((TextView) t1.getChildAt(6)).getText().toString();
                    if (!s1.equals("Прочитано")) ((CheckBox) t1.getChildAt(0)).setChecked(true);
                }
                break;
            case R.id.oNChecked:
                for (int i = 1; i < bIn.getChildCount(); i++) {
                    TableRow t1 = (TableRow) bIn.getChildAt(i);
                    ((CheckBox) t1.getChildAt(0)).setChecked(false);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // если пришло ОК
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_AUTHOR:
                    o = data.getStringExtra("author");

                    break;
                case REQUEST_CODE_SERIA:
                    o = data.getStringExtra("seria");

                    break;
            }
            // если вернулось не ОК
        } else {
            Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View v) {
        //Change!!!
        switch (v.getId()) {
            case R.id.dDel:
                ArrayList<String> did = new ArrayList<String>();
                for (int i = 1; i < bIn.getChildCount(); i++) {
                    TableRow tr = (TableRow) bIn.getChildAt(i);
                    CheckBox cb = (CheckBox) tr.getChildAt(0);
                    if (cb.isChecked()) {
                        did.add(((TextView) tr.getChildAt(1)).getText().toString());
                    }
                }
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                int DelCount = 0;
                for (String s : did)
                    DelCount += database.delete(dbHelper.TABLE_CONTACTS, DBHelper.KEY_ID + "= ?", new String[]{s});
                Toast.makeText(this, "Удалено: " + DelCount, Toast.LENGTH_SHORT).show();
                dbHelper.close();
                break;
            case R.id.sortName:
                if (bIn.getChildCount() > 2) {
                    for (int i = bIn.getChildCount() - 1; i > 1; i--) {
                        for (int j = 1; j < i; j++) {
                            TableRow t1 = (TableRow) bIn.getChildAt(i);
                            String s1 = ((TextView) t1.getChildAt(2)).getText().toString();
                            TableRow t2 = (TableRow) bIn.getChildAt(j);
                            String s2 = ((TextView) t2.getChildAt(2)).getText().toString();
                            if (s1.compareTo(s2) < 0) {
                                if (((CheckBox) t1.getChildAt(0)).isChecked() != ((CheckBox) t2.getChildAt(0)).isChecked()) {
                                    boolean z = ((CheckBox) t1.getChildAt(0)).isChecked();//временная переменная
                                    ((CheckBox) t1.getChildAt(0)).setChecked(((CheckBox) t2.getChildAt(0)).isChecked());
                                    ((CheckBox) t2.getChildAt(0)).setChecked(z);
                                }
                                for (int k = 1; k < 7; k++) {
                                    String z = ((TextView) t2.getChildAt(k)).getText().toString();//временная переменная
                                    ((TextView) t2.getChildAt(k)).setText(((TextView) t1.getChildAt(k)).getText());
                                    ((TextView) t1.getChildAt(k)).setText(z);
                                }
                            }
                        }
                    }
                }
                break;
            case R.id.sortAutor:
                if (bIn.getChildCount() > 2) {
                    for (int i = bIn.getChildCount() - 1; i > 1; i--) {
                        for (int j = 1; j < i; j++) {
                            TableRow t1 = (TableRow) bIn.getChildAt(i);
                            String s1 = ((TextView) t1.getChildAt(3)).getText().toString();
                            TableRow t2 = (TableRow) bIn.getChildAt(j);
                            String s2 = ((TextView) t2.getChildAt(3)).getText().toString();
                            if (s1.compareTo(s2) < 0) {
                                if (((CheckBox) t1.getChildAt(0)).isChecked() != ((CheckBox) t2.getChildAt(0)).isChecked()) {
                                    boolean z = ((CheckBox) t1.getChildAt(0)).isChecked();//временная переменная
                                    ((CheckBox) t1.getChildAt(0)).setChecked(((CheckBox) t2.getChildAt(0)).isChecked());
                                    ((CheckBox) t2.getChildAt(0)).setChecked(z);
                                }
                                for (int k = 1; k < 7; k++) {
                                    String z = ((TextView) t1.getChildAt(k)).getText().toString();//ременная переменная
                                    ((TextView) t1.getChildAt(k)).setText(((TextView) t2.getChildAt(k)).getText());
                                    ((TextView) t2.getChildAt(k)).setText(z);
                                }
                            }
                        }
                    }
                }
                break;
            case R.id.sortSeria:
                if (bIn.getChildCount() > 2) {
                    for (int i = bIn.getChildCount() - 1; i > 1; i--) {
                        for (int j = 1; j < i; j++) {
                            TableRow t1 = (TableRow) bIn.getChildAt(i);
                            String s1 = ((TextView) t1.getChildAt(4)).getText().toString();
                            TableRow t2 = (TableRow) bIn.getChildAt(j);
                            String s2 = ((TextView) t2.getChildAt(4)).getText().toString();
                            if (s1.compareTo(s2) < 0) {
                                if (((CheckBox) t1.getChildAt(0)).isChecked() != ((CheckBox) t2.getChildAt(0)).isChecked()) {
                                    boolean z = ((CheckBox) t1.getChildAt(0)).isChecked();//временная переменная
                                    ((CheckBox) t1.getChildAt(0)).setChecked(((CheckBox) t2.getChildAt(0)).isChecked());
                                    ((CheckBox) t2.getChildAt(0)).setChecked(z);
                                }
                                for (int k = 1; k < 7; k++) {
                                    String z = ((TextView) t1.getChildAt(k)).getText().toString();//ременная переменная
                                    ((TextView) t1.getChildAt(k)).setText(((TextView) t2.getChildAt(k)).getText());
                                    ((TextView) t2.getChildAt(k)).setText(z);
                                }
                            }
                        }
                    }
                    for (int i = bIn.getChildCount() - 1; i > 1; i--) {
                        for (int j = 1; j < i; j++) {
                            TableRow t1 = (TableRow) bIn.getChildAt(i);
                            String s1 = ((TextView) t1.getChildAt(5)).getText().toString();
                            TableRow t2 = (TableRow) bIn.getChildAt(j);
                            String s2 = ((TextView) t2.getChildAt(5)).getText().toString();
                            if (s1.compareTo(s2) < 0) {
                                if (((CheckBox) t1.getChildAt(0)).isChecked() != ((CheckBox) t2.getChildAt(0)).isChecked()) {
                                    boolean z = ((CheckBox) t1.getChildAt(0)).isChecked();//временная переменная
                                    ((CheckBox) t1.getChildAt(0)).setChecked(((CheckBox) t2.getChildAt(0)).isChecked());
                                    ((CheckBox) t2.getChildAt(0)).setChecked(z);
                                }
                                for (int k = 1; k < 7; k++) {
                                    String z = ((TextView) t1.getChildAt(k)).getText().toString();//ременная переменная
                                    ((TextView) t1.getChildAt(k)).setText(((TextView) t2.getChildAt(k)).getText());
                                    ((TextView) t2.getChildAt(k)).setText(z);
                                }
                            }
                        }
                    }
                }
                break;
            case R.id.sortRead:
                if (bIn.getChildCount() > 2) {
                    for (int i = bIn.getChildCount() - 1; i > 1; i--) {
                        for (int j = 1; j < i; j++) {
                            TableRow t1 = (TableRow) bIn.getChildAt(i);
                            String s1 = ((TextView) t1.getChildAt(6)).getText().toString();
                            TableRow t2 = (TableRow) bIn.getChildAt(j);
                            String s2 = ((TextView) t2.getChildAt(6)).getText().toString();
                            if (s1.compareTo(s2) < 0) {
                                if (((CheckBox) t1.getChildAt(0)).isChecked() != ((CheckBox) t2.getChildAt(0)).isChecked()) {
                                    boolean z = ((CheckBox) t1.getChildAt(0)).isChecked();//временная переменная
                                    ((CheckBox) t1.getChildAt(0)).setChecked(((CheckBox) t2.getChildAt(0)).isChecked());
                                    ((CheckBox) t2.getChildAt(0)).setChecked(z);
                                }
                                for (int k = 1; k < 7; k++) {
                                    String z = ((TextView) t1.getChildAt(k)).getText().toString();//временная переменная
                                    ((TextView) t1.getChildAt(k)).setText(((TextView) t2.getChildAt(k)).getText());
                                    ((TextView) t2.getChildAt(k)).setText(z);
                                }
                            }
                        }
                    }
                }
                break;
            case R.id.sortChecked:
                if (bIn.getChildCount() > 2) {
                    for (int i = bIn.getChildCount() - 1; i > 1; i--) {
                        for (int j = 1; j < i; j++) {
                            TableRow t1 = (TableRow) bIn.getChildAt(i);
                            boolean s1 = ((CheckBox) t1.getChildAt(0)).isChecked();
                            TableRow t2 = (TableRow) bIn.getChildAt(j);
                            boolean s2 = ((CheckBox) t2.getChildAt(0)).isChecked();
                            if (s1 == true && s2 == false) {
                                if (((CheckBox) t1.getChildAt(0)).isChecked() != ((CheckBox) t2.getChildAt(0)).isChecked()) {
                                    boolean z = ((CheckBox) t1.getChildAt(0)).isChecked();//временная переменная
                                    ((CheckBox) t1.getChildAt(0)).setChecked(((CheckBox) t2.getChildAt(0)).isChecked());
                                    ((CheckBox) t2.getChildAt(0)).setChecked(z);
                                }
                                for (int k = 1; k < 7; k++) {
                                    String z = ((TextView) t1.getChildAt(k)).getText().toString();//временная переменная
                                    ((TextView) t1.getChildAt(k)).setText(((TextView) t2.getChildAt(k)).getText());
                                    ((TextView) t2.getChildAt(k)).setText(z);
                                }
                            }
                        }
                    }
                }
                break;
        }
    }
}