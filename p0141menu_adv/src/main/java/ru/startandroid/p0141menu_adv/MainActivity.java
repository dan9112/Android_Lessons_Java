package ru.startandroid.p0141menu_adv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Элементы экрана
    TextView tv;
    CheckBox chb;
    CheckBox chb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // находим элементы
        tv = findViewById(R.id.textView);
        chb = findViewById(R.id.chbExtMenu);
        chb1 = findViewById(R.id.chbExt1Menu);
    }

    // создание меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        /*
        // TODO Auto-generated method stub
        // добавляем пункты меню
        menu.add(0, 1, 0, "add");
        menu.add(0, 2, 0, "edit");
        menu.add(0, 3, 3, "delete");
        menu.add(1, 4, 1, "copy");
        menu.add(1, 5, 2, "paste");
        menu.add(1, 6, 4, "exit");
        menu.add(2, 7, 5, "f");
        menu.add(2, 8, 5, "u");
        menu.add(2, 9, 7, "c");
        menu.add(2, 10, 2, "k");*/

        return super.onCreateOptionsMenu(menu);
    }

    // обновление меню
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        // пункты меню с ID группы = 1 видны, если в CheckBox стоит галка
        menu.setGroupVisible(R.id.group1, chb.isChecked());

        // пункты меню с ID группы = 2 видны, если в CheckBox1 стоит галка
        //menu.setGroupVisible(2, chb1.isChecked());

        return super.onPrepareOptionsMenu(menu);
    }

    // обработка нажатий
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Выведем в TextView информацию о нажатом пункте меню
        String sb = "Item Menu" +
                "\r\n groupId: " + item.getGroupId() +
                "\r\n itemId: " + item.getItemId() +
                "\r\n order: " + item.getOrder() +
                "\r\n title: " + item.getTitle();
        tv.setText(sb);

        return super.onOptionsItemSelected(item);
    }
}
