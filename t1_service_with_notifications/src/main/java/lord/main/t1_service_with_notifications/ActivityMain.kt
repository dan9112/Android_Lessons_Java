package lord.main.t1_service_with_notifications

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * Главная активность приложения
 */
class ActivityMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * Функция обработки нажатия на кнопку создания нового канала уведомлений.
     *
     * Запускает [активность создания канала уведомлений][ActivityAddChannel]
     *
     * @param view виджет кнопки
     */
    fun onAddClick(view: View) {
        startActivity(Intent(this, ActivityAddChannel::class.java))
    }

    /**
     * Функция обработки нажатия на кнопку создания нового уведомления.
     *
     * Запускает [активность создания нового уведомления][ActivityCreateNotification]
     *
     * @param view виджет кнопки
     */
    fun onCreateClick(view: View) {
        startActivity(Intent(this, ActivityCreateNotification::class.java))
    }

    /**
     * Функция обработки нажатия на кнопку просмотра истории уведомлений.
     *
     * Запускает [активность демонстрации истории уведомлений][ActivityShowNotifyHistory]
     *
     * @param view виджет кнопки
     */
    fun onShowClick(view: View) {
        startActivity(Intent(this, ActivityShowNotifyHistory::class.java))
    }

    /**
     * Функция обработки нажатия на кнопку просмотра справочной информации о приложении.
     *
     * Запускает активность со справочной информацией?
     *
     * @param view виджет кнопки
     */
    fun help(view: View) {
        //Справочная активность
    }
}