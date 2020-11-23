package ru.test.casino_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.ContextMenu;

public class MainActivity extends AppCompatActivity {

    TextView out;
    EditText min, max;
    Button start;
    int a, b, c;
    boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //найдём элементы
        out = findViewById(R.id.out);
        min = findViewById(R.id.min);
        max = findViewById(R.id.max);
        start = findViewById(R.id.start);

        //для min и max необходимо создавать контекстное меню
        registerForContextMenu(min);
        registerForContextMenu(max);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() ==  MotionEvent.ACTION_DOWN) hideKeyboard();
        return super.dispatchTouchEvent(ev);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        int d;
        switch (v.getId()){
            case R.id.min:
                getMenuInflater().inflate(R.menu.contextmenu1, menu);
                if (max.getText().toString().length()>0) {
                    d = Integer.parseInt(max.getText().toString());
                    if (d == 0) menu.removeItem(R.id.fix_0);//отключить контекстное меню
                    if (d < 10) menu.removeItem(R.id.fix_10);
                    if (d < 25) menu.removeItem(R.id.fix_25);
                    if (d < 45) menu.removeItem(R.id.fix_45);
                }
                menu.setGroupVisible(R.id.m2, false);
                break;
            case R.id.max:
                getMenuInflater().inflate(R.menu.contextmenu1, menu);
                if (min.getText().toString().length()>0) {
                    d = Integer.parseInt(min.getText().toString());
                    if (d == 100) menu.removeItem(R.id.fix_100);//отключить контекстное меню
                    if (d > 90) menu.removeItem(R.id.fix_90);
                    if (d > 75) menu.removeItem(R.id.fix_75);
                    if (d > 55) menu.removeItem(R.id.fix_55);
                }
                menu.setGroupVisible(R.id.m1, false);
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.fix_0:
                min.setText("0");
                break;
            case R.id.fix_10:
                min.setText("10");
                break;
            case R.id.fix_25:
                min.setText("25");
                break;
            case R.id.fix_45:
                min.setText("45");
                break;
            case R.id.fix_55:
                max.setText("55");
                break;
            case R.id.fix_75:
                max.setText("75");
                break;
            case R.id.fix_90:
                max.setText("90");
                break;
            case R.id.fix_100:
                max.setText("100");
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void onClickStart(View v){
        if (v.getId() == R.id.start) {
            if (min.getText().toString().length()>0){
                a = Integer.parseInt(min.getText().toString());
                min.setText(Integer.toString(a));
            }
            else{
                a = 0;
                min.setText("0");
            }
            if (max.getText().toString().length()>0){
                b = Integer.parseInt(max.getText().toString());
                max.setText(Integer.toString(b));
            }
            else{
                b = 0;
                max.setText("0");
            }
            if (a > b) {
                min.setTextColor(Color.RED);
                max.setTextColor(Color.RED);
                out.setTextColor(Color.RED);
                out.setText("Диапазон задан с ошибкой: минимальное значение превышает максимальное!");
            }
            else {
                min.setTextColor(Color.BLACK);
                max.setTextColor(Color.BLACK);
                out.setTextColor(Color.BLACK);
                long t = System.currentTimeMillis();
                final long end = t + 5000;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (System.currentTimeMillis() < end) {
                            c = a + (int) (Math.random() * (b - a + 1));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    out.setText(Integer.toString(c));
                                }
                            });
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        }
    }
}
