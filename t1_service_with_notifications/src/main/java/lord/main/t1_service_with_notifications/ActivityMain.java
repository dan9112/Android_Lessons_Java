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

    /**
     * Функция обработки нажатия на кнопку создания нового уведомления.
     * <p>
     * Запускает {@link ActivityCreateNotification активность создания нового уведомления}
     * </p>
     *
     * @param view виджет кнопки
     */
    public void onCreateClick(View view) {
        startActivity(new Intent(this, ActivityCreateNotification.class));
    }

    /**
     * Функция обработки нажатия на кнопку просмотра истории уведомлений.
     * <p>
     * Запускает {@link ActivityShowNotifyHistory активность демонстрации истории уведомлений}
     * </p>
     *
     * @param view виджет кнопки
     */
    public void onShowClick(View view) {
        startActivity(new Intent(this, ActivityShowNotifyHistory.class));
    }

    /**
     * Функция обработки нажатия на кнопку просмотра справочной информации о приложении.
     * <p>
     * Запускает активность со справочной информацией?
     * </p>
     *
     * @param view виджет кнопки
     */
    public void help(View view) {
        //Справочная активность
    }
}