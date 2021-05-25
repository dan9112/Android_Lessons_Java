package lord.main.t1_service_with_notifications;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;

import java.util.ArrayList;

import lord.main.t1_service_with_notifications.db.DbManager;

/**
 * Сервис взаимодействия с БД
 */
public class ServiceBdConnection extends Service {

    /**
     * Объект для взаимодействия с сервисом
     */
    Binder binder = new Binder();
    /**
     * объект для управления БД
     */
    DbManager manager = new DbManager(this);

    @Override
    public void onCreate() {
        super.onCreate();
        manager.open();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        manager.close();
    }

    /**
     * Функция сохранения уведомления в таблицу БД с историей уведомлений
     *
     * @param channelId    уникальный идентификатор канала уведомлений
     * @param title        заголовок уведомления
     * @param text         текст уведомления
     * @param iconRes      ссылка на иконку в ресурсах
     * @param iconColorRes ссылка на цвет иконки в ресурсах
     * @return уникальный идентификатор строки в таблице, либо -1, если добавление не произошло
     */
    int saveNotification(String channelId, String title, String text, int iconRes, int iconColorRes) {
        return manager.registerNotification(channelId,
                title,
                text,
                iconRes,
                iconColorRes);
    }

    /**
     * Функция возвращает список, состоящий из подсписков наименований каналов уведомлений и их уникальными идентификаторами
     *
     * @return возвращает двухуровневый список
     */
    ArrayList<ArrayList<String>> getAllChannels() {
        ArrayList<ArrayList<String>> list = new ArrayList<>();// объединённый список, который возвращается в результате
        ArrayList<String> names = new ArrayList<>();// список со всеми наименованиями каналов
        ArrayList<String> ids = new ArrayList<>();// список со всеми уникальными идентификаторами каналов
        Cursor c = manager.getAllNotificationChannelNames();
        if (c != null) {
            do {
                names.add(c.getString(0));
                ids.add(c.getString(1));
            } while (c.moveToNext());
            c.close();
            list.add(names);
            list.add(ids);
            return list;
        } else {
            // уведомление
            return null;
        }
    }

    /**
     * Функция добавляет в таблицу каналов уведомлений новую строку
     *
     * @param count           количество каналов уведомлений в приложении минус один для получения свободного индекса
     * @param name            наименование
     * @param importanceLevel уровень важности
     * @param description     описание
     * @param lights          цвет светового сигнала или 13, если отключено
     * @param vibration       1 - включена вибрация, 0 - отключена
     * @param groupId         уникальный идентификатор группы каналов или "null", если канал не сгруппирован
     * @param sound           код мелодии: 0 - нет, 1 - по умолчанию, остальные по порядку в ресурсах
     * @return уникальный идентификатор строки в таблице, либо -1, если добавление не произошло
     */
    long addChannel(int count, String name, int importanceLevel, String description, int lights, int vibration, String groupId, int sound) {
        return manager.registerNotificationChannel("My app notification channel " + count,
                name,
                importanceLevel,
                description,
                lights,
                vibration,
                groupId,
                sound);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    /**
     * Класс для реализации функций, доступных из связанной с сервисом активности
     */
    public class Binder extends android.os.Binder {
        /**
         * Функция возвращает объект сервиса для использования его функций
         *
         * @return объект сервиса
         */
        public ServiceBdConnection getService() {
            return ServiceBdConnection.this;
        }
    }
}