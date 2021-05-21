package lord.main.t1_service_with_notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Главная активность приложения
 */
public class ActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Функция обработки нажатия на кнопку создания нового канала уведомлений.
     * <p>
     * Запускает {@link ActivityAddChannel активность создания канала уведомлений}
     * </p>
     *
     * @param view виджет кнопки
     */
    public void onAddClick(View view) {
        startActivity(new Intent(this, ActivityAddChannel.class));
    }

    public void onCreateClick(View v) {
        startActivity(new Intent(this, ActivityCreateNotification.class));
    }

    public void onShowClick(View v) {

    }
}