package lord.main.p0601_alertdialogsimple;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    final int DIALOG_EXIT = 1;

    OnClickListener myClickListener = (dialog, which) -> {
        switch (which) {
            // положительная кнопка
            case Dialog.BUTTON_POSITIVE:
                saveData();
                finish();
                break;
            // негативная кнопка
            case Dialog.BUTTON_NEGATIVE:
                finish();
                break;
            // нейтральная кнопка
            case Dialog.BUTTON_NEUTRAL:
                break;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onclick(View view) {
        // вызываем диалог
        showDialog(DIALOG_EXIT);
    }

    /**
     * Функция обработки нажатия кнопки "Назад"
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // вызываем диалоговое окно
        showDialog(DIALOG_EXIT);
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_EXIT) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            // заголовок
            adb.setTitle(R.string.exit);
            // сообщение
            adb.setMessage(R.string.save_data);
            // иконка
            adb.setIcon(android.R.drawable.ic_dialog_info);
            // кнопка положительного ответа
            adb.setPositiveButton(R.string.yes, myClickListener);
            // кнопка отрицательного ответа
            adb.setNegativeButton(R.string.no, myClickListener);
            // кнопка нейтрального ответа
            adb.setNeutralButton(R.string.cancel, myClickListener);
            // запрещаем "отменять" диалоговое окно
            adb.setCancelable(false);
            // создаём диалог
            return adb.create();
        }
        return super.onCreateDialog(id);
    }

    private void saveData() {
        Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
    }
}