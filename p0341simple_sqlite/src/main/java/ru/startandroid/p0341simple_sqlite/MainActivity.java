package ru.startandroid.p0341simple_sqlite;

import android.app.Activity;
        import android.content.ContentValues;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {

    final String LOG_TAG = "myLogs";

    Button btnAdd, btnRead, btnClear, btnDelete, btnUpdate, btnTest;
    EditText etName, etEmail, etId;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnDelete = findViewById(R.id.btnDel);
        btnDelete.setOnClickListener(this);

        btnUpdate = findViewById(R.id.btnUpd);
        btnUpdate.setOnClickListener(this);

        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        btnTest = findViewById(R.id.btnTest);
        btnTest.setOnClickListener(this);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etId = findViewById(R.id.etId);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {

        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String id = etId.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        switch (v.getId()) {

            case R.id.btnAdd:
                Log.d(LOG_TAG, "--- Insert in mytable: ---");

                contentValues.put(DBHelper.KEY_NAME, name);
                contentValues.put(DBHelper.KEY_MAIL, email);
                long rowID = database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);

                Log.d(LOG_TAG, "row inserted, ID = " + rowID);

                break;

            case R.id.btnRead:
                Log.d(LOG_TAG, "--- Rows in mytable: ---");

                Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int emailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);
                    do {
                        Log.d(LOG_TAG, "ID = " + cursor.getInt(idIndex) +
                                ", name = " + cursor.getString(nameIndex) +
                                ", email = " + cursor.getString(emailIndex));
                    } while (cursor.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");

                cursor.close();
                break;

            case R.id.btnClear:

                Log.d(LOG_TAG, "--- Clear mytable: ---");

                int clearCount = database.delete(DBHelper.TABLE_CONTACTS, null, null);

                Log.d(LOG_TAG, "deleted rows count = " + clearCount);

                break;

            case R.id.btnUpd:
                if (id.equalsIgnoreCase("")) {
                    break;
                }
                contentValues.put(DBHelper.KEY_MAIL, email);
                contentValues.put(DBHelper.KEY_NAME, name);
                //int updCount = database.update(DBHelper.TABLE_CONTACTS, contentValues, DBHelper.KEY_ID + "= ?", new String[] {id});//по id
                int updCount = database.update(DBHelper.TABLE_CONTACTS, contentValues, DBHelper.KEY_NAME + "= ?", new String[]{name});//по имени

                Log.d(LOG_TAG, "updates rows count = " + updCount);

                break;
            case R.id.btnDel:
                if (id.equalsIgnoreCase("")) {
                    break;
                }
                //int delCount = database.delete(DBHelper.TABLE_CONTACTS, DBHelper.KEY_ID + "=" + id, null);//по id
                int delCount = database.delete(DBHelper.TABLE_CONTACTS, DBHelper.KEY_NAME + "= ?", new String[]{name});//по имени

                Log.d(LOG_TAG, "deleted rows count = " + delCount);

                break;
            case R.id.btnTest:
                if (id.equalsIgnoreCase("") || name.equalsIgnoreCase("") || email.equalsIgnoreCase("")) {
                    Log.d(LOG_TAG, "empty input fields!");
                    //Log.d(LOG_TAG, "unable to create an entry! this ID is already in use!");
                    break;
                }
                contentValues.put(DBHelper.KEY_ID, id);
                contentValues.put(DBHelper.KEY_NAME, name);
                contentValues.put(DBHelper.KEY_MAIL, email);
                database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);

                Log.d(LOG_TAG, "row inserted successfully");

                break;
        }
        dbHelper.close();
    }
}