package ru.startandroid.p0421simple_list;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String[] names = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
            "Костя", "Игорь", "Анна", "Денис", "Андрей" };

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // находим список
        ListView lvMain = findViewById(R.id.lvMain);

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.my_list_item, names);

        // присваиваем адаптер списку
        lvMain.setAdapter(adapter);
    }
}