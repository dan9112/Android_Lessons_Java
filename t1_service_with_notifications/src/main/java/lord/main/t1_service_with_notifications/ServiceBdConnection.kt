package lord.main.t1_service_with_notifications

import android.app.Service
import android.content.Intent
import android.os.IBinder
import lord.main.t1_service_with_notifications.db.DbManager
import java.util.*

/**
 * Сервис взаимодействия с БД
 */
class ServiceBdConnection : Service() {
    /**
     * Объект для взаимодействия с сервисом
     */
    private var binder: Binder = Binder()

    /**
     * Объект для управления БД
     */
    private var manager = DbManager(this)
    override fun onCreate() {
        super.onCreate()
        manager.open()
    }

    override fun onDestroy() {
        super.onDestroy()
        manager.close()
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
    fun saveNotification(
        channelId: String,
        title: String,
        text: String,
        iconRes: Int,
        iconColorRes: Int
    ): Int {
        return manager.registerNotification(
            channelId,
            title,
            text,
            iconRes,
            iconColorRes
        )
    }

    /**
     * Функция возвращает список, состоящий из подсписков наименований каналов уведомлений и их уникальными идентификаторами
     *
     * @return возвращает двухуровневый список
     */
    fun allChannels(): ArrayList<ArrayList<String>>? {
        return manager.allNotificationChannelNames
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
    fun addChannel(
        count: Int,
        name: String,
        importanceLevel: Int,
        description: String,
        lights: Int,
        vibration: Int,
        groupId: String,
        sound: Int
    ): Long {
        return manager.registerNotificationChannel(
            "My app notification channel $count",
            name,
            importanceLevel,
            description,
            lights,
            vibration,
            groupId,
            sound
        )
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    /**
     * Класс для реализации функций, доступных из связанной с сервисом активности
     */
    inner class Binder : android.os.Binder() {
        /**
         * Функция возвращает объект сервиса для использования его функций
         *
         * @return объект сервиса
         */
        val service: ServiceBdConnection
            get() = this@ServiceBdConnection
    }
}