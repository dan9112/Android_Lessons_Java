package lord.main.t0_notificationchannels;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

/**
 * Главная активность приложения
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Уникальный идентификатор 0 канала уведомлений
     */
    public static final String NOTIFICATION_CHANNEL_0_ID = "666",
    /**
     * Наименование 0 канала уведомлений
     */
    NOTIFICATION_CHANNEL_0_NAME = "Важный канал",
    /**
     * Уникальный идентификатор 1 канала уведомлений
     */
    NOTIFICATION_CHANNEL_1_ID = "911",
    /**
     * Наименование 1 канала уведомлений
     */
    NOTIFICATION_CHANNEL_1_NAME = "Другой канал",
    /**
     * Уникальный идентификатор группы каналов уведомлений
     */
    NOTIFICATION_CHANNEL_GROUP_ID = "112",
    /**
     * Наименование группы каналов уведомлений
     */
    NOTIFICATION_CHANNEL_GROUP_NAME = "Lord",
    /**
     * Ключ доступа к коду, по которому можно выяснить из какого уведомления была вызвана {@link MainActivity активность}
     */
    INTENT_KEY = "code";
    /**
     * Код запроса {@link MainActivity активности} из важного уведомления
     */
    public static final int IMPORTANT_REQUEST_CODE = 666,
    /**
     * Код запроса {@link MainActivity активности} из другого уведомления
     */
    ANOTHER_REQUEST_CODE = 999;
    /**
     * Намерение вызова активности/сервиса.
     * <p>
     * В данном случае, вызывает эту же активность
     * </p>
     */
    private static Intent intent;

    /**
     * Создание переменной с набором атрибутов создаваемого уведомления
     *
     * @param id          уникальный идентификатор
     * @param title       заголовок
     * @param text        основной текст
     * @param icon        идентификатор изображения в ресурсах
     * @param color       цвет RGB изображения
     * @param requestCode код возврата
     * @return набор атрибутов уведомления
     */
    private NotificationCompat.Builder buildNotification(String id, String title, String text, int icon, int color, int requestCode) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(INTENT_KEY, requestCode);

        return new NotificationCompat.Builder(this, id)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(icon)
                .setColor(color)
                .setContentIntent(PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setAutoCancel(true)
                .setShowWhen(true);
    }

    /**
     * Создание канала уведомлений
     *
     * @param id                   уникальный идентификатор канала
     * @param name                 наименование канала
     * @param importanceLevel      уровень важность сообщений данного канала
     * @param description          описание канала
     * @param enableLights         флаг активности диодов при получении уведомлений по каналу (при наличии на устройстве соответствующего функционала)
     * @param lightColor           цвет RGB света диодов при активации
     * @param enableVibration      флаг активности вибрации при получении уведомлений по каналу
     * @param lockscreenVisibility мод отображения полученных уведомлений по каналу на экране блокировки
     * @param groupID              уникальный идентификатор группы, в которую входит канал
     * @param raw                  идентификатор звуковой дорожки в ресурсах
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(String id, String name, int importanceLevel, String description, boolean enableLights, Integer lightColor, boolean enableVibration, int lockscreenVisibility, String groupID, int raw) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(id, name, importanceLevel);
        if (description.length() > 0) channel.setDescription(description);
        channel.enableLights(enableLights);
        if (lightColor != null) channel.setLightColor(lightColor);
        channel.enableVibration(enableVibration);
        channel.setLockscreenVisibility(lockscreenVisibility);
        if (groupID.length() > 0) channel.setGroup(groupID);
        if (raw > -1) {
            channel.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + raw),
                    new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build());
        }
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        switch (getIntent().getIntExtra(INTENT_KEY, -13)) {
            case IMPORTANT_REQUEST_CODE:
                Toast.makeText(this, "Активность вызвана важным уведомлением", Toast.LENGTH_SHORT).show();
                break;
            case ANOTHER_REQUEST_CODE:
                Toast.makeText(this, "Активность вызвана другим уведомлением", Toast.LENGTH_SHORT).show();
                break;
        }


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Поддержка каналов уведомлений появилась только в 26 версии SDK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Регистрация группы каналов уведомлений
            notificationManager.createNotificationChannelGroup(
                    new NotificationChannelGroup(NOTIFICATION_CHANNEL_GROUP_ID, NOTIFICATION_CHANNEL_GROUP_NAME)
            );

            // Создание 0 канала уведомлений
            createNotificationChannel(NOTIFICATION_CHANNEL_0_ID,
                    NOTIFICATION_CHANNEL_0_NAME,
                    NotificationManager.IMPORTANCE_HIGH,
                    "Он делает что-то важное и это хорошо",
                    true,
                    Color.WHITE,
                    true,
                    Notification.VISIBILITY_PRIVATE,
                    NOTIFICATION_CHANNEL_GROUP_ID,
                    R.raw.song_of_the_pale_lady_cut);

            // Создание 1 канала уведомлений
            createNotificationChannel(NOTIFICATION_CHANNEL_1_ID,
                    NOTIFICATION_CHANNEL_1_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT,
                    "Он делает что-то другое, что тоже неплохо",
                    false,
                    null,
                    false,
                    Notification.VISIBILITY_PUBLIC,
                    NOTIFICATION_CHANNEL_GROUP_ID,
                    R.raw.game_with_fire_cut);
        } else
            Toast.makeText(this, "Ваша версия не поддерживает создание каналов для уведомлений!\nКаждое уведомление настраивалось индивидуально", Toast.LENGTH_SHORT).show();


        Button btnCreateNotification = findViewById(R.id.btnCreateNotification);
        btnCreateNotification.setOnClickListener(v -> {

            NotificationCompat.Builder builder = buildNotification(NOTIFICATION_CHANNEL_0_ID,
                    "Очень важное уведомление",
                    "[Might & Magic 7 OST - Song of the Pale Lady]: Оно слишком важно, чтобы...",
                    R.drawable.ic_text,
                    Color.WHITE,
                    IMPORTANT_REQUEST_CODE);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
                builder.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.song_of_the_pale_lady_cut));

            notificationManager.notify(0, builder.build());
        });

        Button btnCreateNotification1 = findViewById(R.id.btnCreateNotification1);
        btnCreateNotification1.setOnClickListener(v -> {
            NotificationCompat.Builder builder = buildNotification(NOTIFICATION_CHANNEL_1_ID,
                    "Другое уведомление в другом канале",
                    "[Эпидемия - Игра с огнём(Ария)]: Другое мнение по...",
                    R.drawable.ic_info,
                    Color.CYAN,
                    ANOTHER_REQUEST_CODE);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
                builder.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.game_with_fire_cut));

            notificationManager.notify(1, builder.build());
        });
    }
}