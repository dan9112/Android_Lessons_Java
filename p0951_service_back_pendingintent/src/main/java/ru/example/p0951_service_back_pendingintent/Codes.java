package ru.example.p0951_service_back_pendingintent;

import android.app.PendingIntent;

/**
 * Класс для хранения кодов различных запросов модуля
 */
interface Codes {
    /**
     * Код для типа возвращаемого сообщения "Задача начала работу"
     */
    int STATUS_START = 100,
    /**
     * Код для типа возвращаемого сообщения "Задача завершила работу"
     */
    STATUS_FINISH = 200;

    /**
     * Ключ для передаваемого параметра "Время задержки"
     */
    String PARAM_TIME = "time",
    /**
     * Ключ для передаваемого параметра {@link PendingIntent} для предоставления права
     * взаимодействия с текущей активностью
     */
    PARAM_PINTENT = "pendingIntent",
    /**
     * Ключ для возвращаемого параметра "Результат"
     */
    PARAM_RESULT = "result",

    /**
     * Тег логов класса
     */
    LOG_TAG = "myLogs";
}
