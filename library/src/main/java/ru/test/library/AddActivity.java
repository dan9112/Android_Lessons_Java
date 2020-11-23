package ru.test.library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    final String LOG_TAG = "myLogs";
    Button add;
    EditText name, autor, seria, num;
    CheckBox read;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        add = findViewById(R.id.nAdd);
        add.setOnClickListener(this);

        name = findViewById(R.id.nName);
        autor = findViewById(R.id.nAutor);
        seria = findViewById(R.id.nSeria);
        num = findViewById(R.id.nNum);
        read = findViewById(R.id.nRead);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.nAdd) {
            //создаём объект для данных
            ContentValues cv = new ContentValues();
            //считываем введённые поля
            String _name = name.getText().toString();
            //проверка заполненности обязательного поля
            if (!_name.equals("")) {
                String _author = autor.getText().toString();
                String _seria = seria.getText().toString();
                String _num = num.getText().toString();
                //подключаемся к БД
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                cv.put("Name", _name);
                cv.put("Author", _author);
                cv.put("Seria", _seria);
                cv.put("Num", _num);
                if (read.isChecked()) cv.put("Read", 1);
                else cv.put("Read", 0);
                //вставляем запись
                long rowID = database.insert(dbHelper.TABLE_CONTACTS, null, cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
            }
            else{
                Toast.makeText(this, "Заполните обязательное поле 'Названия книги'!", Toast.LENGTH_LONG).show();
            }
            dbHelper.close();
        }
    }
}