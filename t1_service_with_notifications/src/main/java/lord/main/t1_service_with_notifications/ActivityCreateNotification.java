package lord.main.t1_service_with_notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Arrays;

import lord.main.t1_service_with_notifications.supporting_classes.SpinnerAdapter;

/**
 * Активность создания уведомлений
 */
public class ActivityCreateNotification extends AppCompatActivity {

    /**
     * Экземпляр {@link ServiceBdConnection сервиса для взаимодействия с БД} приложения
     */
    private ServiceBdConnection bdService;
    /**
     * Выпадающее меню выбора иконки для уведомления
     */
    private Spinner icon,
    /**
     * Выпадающее меню выбора цвета иконки уведомления
     */
    iconColor,
    /**
     * Выпадающее меню выбора канала, в котором будет отправлено уведомление
     */
    channelId;
    /**
     * Чекбокс, являющийся также флагом автоматического сокрытия уведомления
     */
    private CheckBox autoCancel,
    /**
     * Чекбокс, являющийся также флагом отображения времени создания уведомления
     */
    showWhen;
    /**
     * Текстовое окно для ввода заголовка уведомления
     */
    private EditText title,
    /**
     * Текстовое окно для ввода текста уведомления
     */
    text;
    /**
     * Список уникальных идентификаторов всех каналов уведомлений из БД
     */
    private ArrayList<String> channels = new ArrayList<>();
    /**
     * Флаг подключения к {@link ServiceBdConnection сервису взаимодействия с БД}
     */
    private boolean bdBound = false;
    /**
     * Объект для подключения к {@link ServiceBdConnection сервису взаимодействия с БД}
     */
    private ServiceConnection bdServiceConnection;

    /**
     * Функция заполнения таблицы
     */
    private void fillTable() {
        ArrayList<ArrayList<String>> list = bdService.getAllChannels();
        if (list == null) {
            Toast.makeText(this, "Ошибка чтения каналов уведомлений из БД!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            channels = list.get(1);
            channelId.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list.get(0)));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notification);

        channelId = findViewById(R.id.notify_channel_id);

        bdServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                bdService = ((ServiceBdConnection.Binder) service).getService();
                bdBound = true;
                fillTable();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                bdBound = false;
            }
        };

        bindService(new Intent(this, ServiceBdConnection.class), bdServiceConnection, BIND_AUTO_CREATE);

        icon = findViewById(R.id.notify_icon);
        icon.setAdapter((new SpinnerAdapter(
                this,
                R.layout.dropdown_item_text,
                Arrays.asList(
                        R.drawable.ic_baseball,
                        R.drawable.ic_star,
                        R.drawable.ic_umbrella,
                        R.drawable.ic_info),
                1
        )));
        iconColor = findViewById(R.id.notify_icon_color);
        iconColor.setAdapter(new SpinnerAdapter(
                this,
                R.layout.dropdown_item_text,
                Arrays.asList(
                        R.drawable.color_example_with_stroke_white,
                        R.drawable.color_example_with_stroke_blue,
                        R.drawable.color_example_with_stroke_red,
                        R.drawable.color_example_with_stroke_green),
                2));

        autoCancel = findViewById(R.id.notify_auto_cancel);
        showWhen = findViewById(R.id.notify_show_when);
        title = findViewById(R.id.notify_title);
        text = findViewById(R.id.notify_text);
    }

    /**
     * Функция обработки нажатия на кнопку создания уведомления.
     * <p>
     * Создаёт уведомление с заданными параметрами и сохраняет их в БД
     * </p>
     *
     * @param view виджет кнопки
     */
    public void onClickCreate(View view) {
        int iconId, color;
        switch (icon.getSelectedItemPosition()) {
            case 0:
                iconId = R.drawable.ic_baseball;
                break;
            case 1:
                iconId = R.drawable.ic_star;
                break;
            case 2:
                iconId = R.drawable.ic_umbrella;
                break;
            default:
                iconId = R.drawable.ic_info;
                break;
        }
        switch (iconColor.getSelectedItemPosition()) {
            case 1:
                color = Color.BLUE;
                break;
            case 2:
                color = Color.RED;
                break;
            case 3:
                color = Color.GREEN;
                break;
            default:
                color = Color.WHITE;
                break;
        }

        int id = bdService.saveNotification(channels.get(channelId.getSelectedItemPosition()),
                title.getText().toString(),
                text.getText().toString(),
                iconId,
                color);

        Notification builder = new NotificationCompat.Builder(this, channels.get(channelId.getSelectedItemPosition()))
                .setContentTitle(title.getText())
                .setContentText(text.getText())
                .setSmallIcon(iconId)
                .setColor(color)
                .setAutoCancel(autoCancel.isChecked())
                .setShowWhen(showWhen.isChecked())
                .build();
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(id, builder);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!bdBound) return;
        unbindService(bdServiceConnection);
        bdBound = false;
    }

    /**
     * Функция обработки нажатия на layout со {@link #icon спиннером выбора иконки уведомления} и подсказкой.
     * <p>
     * Открывает {@link #icon спиннер выбора иконки уведомления}
     * </p>
     *
     * @param view виджет layout
     */
    public void click00(View view) {
        icon.performClick();
    }

    /**
     * Функция обработки нажатия на layout со {@link #iconColor спиннером выбора цвета иконки уведомления} и подсказкой.
     * <p>
     * Открывает {@link #iconColor спиннер выбора цвета иконки уведомления}
     * </p>
     *
     * @param view виджет layout
     */
    public void click01(View view) {
        iconColor.performClick();
    }

    /**
     * Функция обработки нажатия на layout со {@link #channelId спиннером выбора канала уведомлений, по которому оно будет отправлено}, и подсказкой.
     * <p>
     * Открывает {@link #channelId спиннер выбора цвета канала уведомлений, по которому оно будет отправлено}
     * </p>
     *
     * @param view виджет layout
     */
    public void click02(View view) {
        channelId.performClick();
    }
}