package ru.test.library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Color;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.content.ContentValues;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    DBHelper dbHelper;
    SQLiteDatabase database;
    final String LOG_TAG = "myLogs";
    Switch hiding0, hiding1, autor, seria, name;
    TableLayout tabOut;
    final int c0 = 13, c1 = 14, c2 = 15, c3 = 16;
    int[] colors = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        colors[0] = Color.parseColor("#559966CC");
        colors[1] = Color.parseColor("#55336699");

        dbHelper = new DBHelper(this);

        autor = findViewById(R.id.Autor);
        seria = findViewById(R.id.Seria);
        name = findViewById(R.id.Name);
        hiding0 = findViewById(R.id.Hiding0);
        hiding1 = findViewById(R.id.Hiding1);
        tabOut = findViewById(R.id.tabOut);

        hiding0.setOnCheckedChangeListener(this);
        hiding1.setOnCheckedChangeListener(this);
        autor.setOnCheckedChangeListener(this);
        seria.setOnCheckedChangeListener(this);
        name.setOnCheckedChangeListener(this);

        database = dbHelper.getWritableDatabase();

        // проверка существования записей
        Cursor c = database.query(dbHelper.TABLE_CONTACTS, null, null, null, null, null, null);
        c.close();
        dbHelper.close();
    }

    // создание меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lib_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // обновление меню!!!!!!!
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    // обработка нажатий
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId())
        {
            case R.id.Del:
                intent = new Intent(this, DeleteActivity.class);
                startActivity(intent);
                break;
            case R.id.Add:
                intent = new Intent(this, AddActivity.class);
                startActivity(intent);
                break;
            case R.id.Exit:
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.Ok:
                SQLiteDatabase database = dbHelper.getWritableDatabase();

                // переменные для query
                String selection;
                String orderBy;
                String[] columns = new String[]{dbHelper.KEY_NAME, dbHelper.KEY_AUTHOR, dbHelper.KEY_SERIA, dbHelper.KEY_NUM, dbHelper.KEY_ID};

                // курсор
                Cursor c = null;

                if (name.isChecked() == true && autor.isChecked() == false && seria.isChecked() == false)
                    orderBy = dbHelper.KEY_NAME;
                else if (name.isChecked() == false && autor.isChecked() == true && seria.isChecked() == false)
                    orderBy = dbHelper.KEY_AUTHOR;
                else if (name.isChecked() == false && autor.isChecked() == false && seria.isChecked() == true)
                    orderBy = dbHelper.KEY_SERIA + ", " + dbHelper.KEY_NUM;
                else if (name.isChecked() == true && autor.isChecked() == true && seria.isChecked() == false)
                    orderBy = dbHelper.KEY_AUTHOR + ", " + dbHelper.KEY_NAME;
                else if (name.isChecked() == true && autor.isChecked() == false && seria.isChecked() == true)
                    orderBy = dbHelper.KEY_SERIA + ", " + dbHelper.KEY_NUM + ", " + dbHelper.KEY_NAME;
                else if (name.isChecked() == false && autor.isChecked() == true && seria.isChecked() == true)
                    orderBy = dbHelper.KEY_AUTHOR + ", " + dbHelper.KEY_SERIA + ", " + dbHelper.KEY_NUM;
                else if (name.isChecked() == true && autor.isChecked() == true && seria.isChecked() == true)
                    orderBy = dbHelper.KEY_AUTHOR + ", " + dbHelper.KEY_SERIA + ", " + dbHelper.KEY_NUM + ", " + dbHelper.KEY_NAME;
                else orderBy = null;

                if (hiding0.isChecked()) selection = dbHelper.KEY_READ + " = 0";
                else if (hiding1.isChecked()) selection = dbHelper.KEY_READ + " = 1";
                else selection = null;
                tabOut.removeAllViews();

                //вывод после применения всех фильтров выше
                c = database.query(dbHelper.TABLE_CONTACTS, columns, selection, null, null, null, orderBy);

                if (c != null) {
                    TableRow s0 = new TableRow(MainActivity.this);
                    s0.setBackgroundColor(colors[1]);

                    TextView m0 = new TextView(MainActivity.this);
                    m0.setText("Название");
                    m0.setId(c0);
                    m0.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            if (v.getId() == c0){
                                if (tabOut.getChildCount() > 2) {
                                    for (int i = tabOut.getChildCount() - 1; i > 1; i--) {
                                        for (int j = 1; j < i; j++) {
                                            TableRow t1 = (TableRow) tabOut.getChildAt(i);
                                            String s1 = ((TextView) t1.getChildAt(0)).getText().toString();
                                            TableRow t2 = (TableRow) tabOut.getChildAt(j);
                                            String s2 = ((TextView) t2.getChildAt(0)).getText().toString();
                                            if (s1.compareTo(s2) < 0) {
                                                for (int k = 0; k < 5; k++) {
                                                    String z = ((TextView) t2.getChildAt(k)).getText().toString();//временная переменная
                                                    ((TextView) t2.getChildAt(k)).setText(((TextView) t1.getChildAt(k)).getText());
                                                    ((TextView) t1.getChildAt(k)).setText(z);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
                    m0.setTextColor(Color.BLACK);

                    TextView m1 = new TextView(MainActivity.this);
                    m1.setId(c1);
                    m1.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            if (v.getId() == c1){
                                if (tabOut.getChildCount() > 2) {
                                    for (int i = tabOut.getChildCount() - 1; i > 1; i--) {
                                        for (int j = 1; j < i; j++) {
                                            TableRow t1 = (TableRow) tabOut.getChildAt(i);
                                            String s1 = ((TextView) t1.getChildAt(1)).getText().toString();
                                            TableRow t2 = (TableRow) tabOut.getChildAt(j);
                                            String s2 = ((TextView) t2.getChildAt(1)).getText().toString();
                                            if (s1.compareTo(s2) < 0) {
                                                for (int k = 0; k < 5; k++) {
                                                    String z = ((TextView) t2.getChildAt(k)).getText().toString();//временная переменная
                                                    ((TextView) t2.getChildAt(k)).setText(((TextView) t1.getChildAt(k)).getText());
                                                    ((TextView) t1.getChildAt(k)).setText(z);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
                    m1.setText("Автор");
                    m1.setTextColor(Color.BLACK);

                    TextView m2 = new TextView(MainActivity.this);
                    m2.setId(c2);
                    m2.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            if (v.getId() == c2){
                                if (tabOut.getChildCount() > 2) {
                                    for (int i = tabOut.getChildCount() - 1; i > 1; i--) {
                                        for (int j = 1; j < i; j++) {
                                            TableRow t1 = (TableRow) tabOut.getChildAt(i);
                                            String s1 = ((TextView) t1.getChildAt(2)).getText().toString();
                                            TableRow t2 = (TableRow) tabOut.getChildAt(j);
                                            String s2 = ((TextView) t2.getChildAt(2)).getText().toString();
                                            if (s1.compareTo(s2) < 0) {
                                                for (int k = 0; k < 5; k++) {
                                                    String z = ((TextView) t2.getChildAt(k)).getText().toString();//временная переменная
                                                    ((TextView) t2.getChildAt(k)).setText(((TextView) t1.getChildAt(k)).getText());
                                                    ((TextView) t1.getChildAt(k)).setText(z);
                                                }
                                            }
                                        }
                                    }
                                    for (int i = tabOut.getChildCount() - 1; i > 1; i--) {
                                        for (int j = 1; j < i; j++) {
                                            TableRow t1 = (TableRow) tabOut.getChildAt(i);
                                            String s1 = ((TextView) t1.getChildAt(3)).getText().toString();
                                            TableRow t2 = (TableRow) tabOut.getChildAt(j);
                                            String s2 = ((TextView) t2.getChildAt(3)).getText().toString();
                                            if (s1.compareTo(s2) < 0) {
                                                for (int k = 0; k < 5; k++) {
                                                    String z = ((TextView) t2.getChildAt(k)).getText().toString();//временная переменная
                                                    ((TextView) t2.getChildAt(k)).setText(((TextView) t1.getChildAt(k)).getText());
                                                    ((TextView) t1.getChildAt(k)).setText(z);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
                    m2.setText("Серия");
                    m2.setTextColor(Color.BLACK);

                    TextView m3 = new TextView(MainActivity.this);
                    m3.setText("Номер в серии");
                    m3.setTextColor(Color.BLACK);

                    //TextView m4 = new TextView(MainActivity.this);
                    //m4.setWidth(0);

                    s0.addView(m0);
                    s0.addView(m1);
                    s0.addView(m2);
                    s0.addView(m3);
                    ///s0.addView(m4);

                    tabOut.addView(s0);

                    boolean cc = false;
                    //int i = 0;
                    if (c.moveToFirst()) {
                        do {

                            TableRow s = new TableRow(MainActivity.this);
                            for (String cn : c.getColumnNames()) {
                                TextView r = new TextView(MainActivity.this);
                                String str = c.getString(c.getColumnIndex(cn));
                                if (str.length() == 0) str = " -- ";
                                r.setText(str);
                                Log.i("Text",str);
                                r.setTextColor(Color.BLACK);
                                r.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                if (cn.equals(dbHelper.KEY_ID)) r.setVisibility(View.INVISIBLE);
                                s.addView(r);
                            }
                            if (cc){
                                s.setBackgroundColor(colors[0]);
                                cc = false;
                            }
                            else if (!cc) cc = true;

                            tabOut.addView(s);
                        } while (c.moveToNext());
                    }
                    c.close();
                } else {
                    Log.d(LOG_TAG, "Cursor is null");//вывод в логи
                }
                dbHelper.close();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.Hiding0:
                if (isChecked) hiding1.setChecked(false);
                break;
            case R.id.Hiding1:
                if (isChecked) hiding0.setChecked(false);
                break;
        }
    }
}