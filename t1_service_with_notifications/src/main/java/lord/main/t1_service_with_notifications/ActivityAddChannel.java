package lord.main.t1_service_with_notifications;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

import lord.main.t1_service_with_notifications.supporting_classes.SpinnerAdapter;

/**
 * Активность добавления нового канала уведомлений
 */
public class ActivityAddChannel extends AppCompatActivity {
    /**
     * Экземпляр {@link ServiceBdConnection сервиса для взаимодействия с БД} приложения
     */
    private ServiceBdConnection bdService;
    /**
     * Кнопка создания и регистрации нового канала уведомлений
     */
    private Button ok;
    /**
     * Выпадающее меню выбора уровня важности для уведомлений создаваемого канала уведомлений
     */
    private Spinner importanceLevel,
    /**
     * Выпадающее меню выбора цвета индикаторов уведомлений для устройств, поддерживающих эту функцию
     */
    lightColor,
    /**
     * Выпадающее меню выбора группы, к которой будет принадлежать создаваемый канал уведомлений
     */
    groupId,
    /**
     * Выпадающее меню выбора звука канала
     */
    soundId;
    /**
     * Наименование создаваемого канала уведомлений
     */
    private EditText name,
    /**
     * Описание создаваемого канала уведомлений
     */
    description;
    /**
     * Чекбокс, являющийся также флагом подключения вибрации к каналу уведомлений
     */
    private CheckBox vibration;
    /**
     * Менеджер уведомлений для управления ими
     */
    private NotificationManager notificationManager;
    /**
     * Список всех групп каналов уведомлений
     */
    private ArrayList<String> notificationGroups;
    /**
     * Флаг подключения к {@link ServiceBdConnection сервису взаимодействия с БД}
     */
    private boolean bdBound = false;
    /**
     * Объект для подключения к {@link ServiceBdConnection сервису взаимодействия с БД}
     */
    private ServiceConnection bdServiceConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_channel);

        ok = findViewById(R.id.btnCreateChannel);

        bdServiceConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                bdService = ((ServiceBdConnection.Binder) service).getService();
                bdBound = true;
                ok.setEnabled(true);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                bdBound = false;
            }
        };

        bindService(new Intent(this, ServiceBdConnection.class), bdServiceConn, BIND_AUTO_CREATE);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        importanceLevel = findViewById(R.id.importance_level);
        importanceLevel.setSelection(1);// Установить выбранный по умолчанию на "По умолчанию"

        lightColor = findViewById(R.id.light_color);
        lightColor.setAdapter(new SpinnerAdapter(
                this,
                R.layout.dropdown_item_text,
                Arrays.asList(
                        R.drawable.color_example_with_stroke_white,
                        R.drawable.color_example_with_stroke_blue,
                        R.drawable.color_example_with_stroke_red,
                        R.drawable.color_example_with_stroke_green),
                0));

        groupId = findViewById(R.id.group_id);
        soundId = findViewById(R.id.sound_id);
        soundId.setSelection(1);// Установить выбранный по умолчанию на "По умолчанию"
        name = findViewById(R.id.channel_name);
        description = findViewById(R.id.channel_description);
        vibration = findViewById(R.id.vibration);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createGroups();
        } else
            Toast.makeText(this, "У вас устаревшая версия ОС для корректной работы функционала каналов уведомлений! Необходима не ниже Oreo(26)!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Функция создания групп каналов уведомлений. В данном примере их фиксированное количество - 4
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createGroups() {
        notificationGroups = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.group_ids)));
        notificationGroups.remove(0);
        notificationManager.createNotificationChannelGroup(new NotificationChannelGroup(notificationGroups.get(0), notificationGroups.get(0)));
        notificationManager.createNotificationChannelGroup(new NotificationChannelGroup(notificationGroups.get(1), notificationGroups.get(1)));
        notificationManager.createNotificationChannelGroup(new NotificationChannelGroup(notificationGroups.get(2), notificationGroups.get(2)));
        notificationManager.createNotificationChannelGroup(new NotificationChannelGroup(notificationGroups.get(3), notificationGroups.get(3)));
    }

    /**
     * Функция обработки нажатия на кнопку создания канала
     *
     * @param view виджет кнопки
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickCreate(View view) {
        if (name.getText().length() > 0) {
            int iL, // уровень важности создаваемого канала
                    l, // цвет светового сигнала создаваемого канала
                    sI; // код мелодии
            String gI; // уникальный идентификатор группы каналов

            NotificationChannel channel;
            // Создание канала
            switch (importanceLevel.getSelectedItemPosition()) {
                // Выбран высокий уровень важности канала
                case 0:
                    iL = NotificationManager.IMPORTANCE_HIGH;
                    break;

                // Выбран уровень важности канала по умолчанию
                default:
                    iL = NotificationManager.IMPORTANCE_DEFAULT;
                    break;

                // Выбран средний уровень важности канала
                case 2:
                    iL = NotificationManager.IMPORTANCE_LOW;
                    break;

                // Выбран низкий уровень важности канала
                case 3:
                    iL = NotificationManager.IMPORTANCE_MIN;
                    break;

                // Выбран уровень важности канала "неважные уведомления"
                case 4:
                    iL = NotificationManager.IMPORTANCE_NONE;
                    break;
            }
            channel = new NotificationChannel("My app notification channel " + notificationManager.getNotificationChannels().size(),
                    name.getText(), iL);

            // Добавление описания каналу
            channel.setDescription(description.getText().toString());
            // Добавление световой индикации
            switch (lightColor.getSelectedItemPosition()) {
                // Не включать
                default:
                    l = 13;
                    channel.enableLights(false);
                    break;

                // Белая
                case 1:
                    l = Color.WHITE;
                    channel.enableLights(true);
                    channel.setLightColor(l);
                    break;

                // Синяя
                case 2:
                    l = Color.parseColor("#0000FF");
                    channel.enableLights(true);
                    channel.setLightColor(l);
                    break;

                // Красная
                case 3:
                    l = Color.RED;
                    channel.enableLights(true);
                    channel.setLightColor(l);
                    break;

                // Зелёная
                case 4:
                    l = Color.GREEN;
                    channel.enableLights(true);
                    channel.setLightColor(l);
                    break;
            }
            // Добавление вибрации
            channel.enableVibration(vibration.isChecked());
            // Добавление создаваемого канала уведомлений в группу
            switch (groupId.getSelectedItemPosition()) {
                // Вне группы
                default:
                    gI = "null";
                    break;

                // Lord_1
                case 1:
                    gI = notificationGroups.get(0);
                    channel.setGroup(gI);
                    break;

                // Lord_2
                case 2:
                    gI = notificationGroups.get(1);
                    channel.setGroup(gI);
                    break;

                // Lord_3
                case 3:
                    gI = notificationGroups.get(2);
                    channel.setGroup(gI);
                    break;

                // Lord_4
                case 4:
                    gI = notificationGroups.get(3);
                    channel.setGroup(gI);
                    break;
            }
            // Установка звука уведомлений каналу
            switch (soundId.getSelectedItemPosition()) {
                // Убрать звук
                case 0:
                    sI = 0;
                    channel.setSound(null, null);
                    break;

                // Оставить по умолчанию
                default:
                    sI = 1;
                    break;

                // Игра с огнём (обрезанная)
                case 2:
                    sI = 2;
                    channel.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.game_with_fire_cut),
                            new AudioAttributes.Builder()
                                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                    .build());
                    break;

                // Song of the Pale Lady (обрезанная)
                case 3:
                    sI = 3;
                    channel.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.song_of_the_pale_lady_cut),
                            new AudioAttributes.Builder()
                                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                    .build());
                    break;
            }
            notificationManager.createNotificationChannel(channel);

            if (bdService.addChannel(notificationManager.getNotificationChannels().size() - 1,
                    name.getText().toString(),
                    iL,
                    description.getText().toString(),
                    l,
                    vibration.isChecked() ? 1 : 0,
                    gI,
                    sI) != -1) {
                Toast.makeText(this, "Канал успешно создан!", Toast.LENGTH_SHORT).show();
                finish();
            } else Toast.makeText(this, "Ошибка записи в БД!", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Назовите как-нибудь канал! Пустое имя недопустимо!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!bdBound) return;
        unbindService(bdServiceConn);
        bdBound = false;
    }

    /**
     * Функция обработки нажатия на layout со {@link #lightColor спиннером выбора цвета индикации} и подсказкой.
     * <p>
     * Открывает {@link #lightColor спиннер выбора цвета индикации}
     * </p>
     *
     * @param view виджет layout
     */
    public void click0(View view) {
        lightColor.performClick();
    }

    /**
     * Функция обработки нажатия на layout со {@link #groupId спиннером выбора группы уведомлений} и подсказкой.
     * <p>
     * Открывает {@link #groupId спиннер выбора группы уведомлений}
     * </p>
     *
     * @param view виджет layout
     */
    public void click1(View view) {
        groupId.performClick();
    }

    /**
     * Функция обработки нажатия на layout со {@link #soundId спиннером выбора мелодии} и подсказкой.
     * <p>
     * Открывает {@link #soundId спиннер выбора мелодии}
     * </p>
     *
     * @param view виджет layout
     */
    public void click2(View view) {
        soundId.performClick();
    }
}