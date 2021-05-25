package lord.main.t1_service_with_notifications

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import lord.main.t1_service_with_notifications.db.DbManager
import lord.main.t1_service_with_notifications.supporting_classes.RecyclerViewAdapter

/**
 * Активность демонстрации истории уведомлений
 */
class ActivityShowNotifyHistory : AppCompatActivity() {
    /**
     * Виджет для демонстрации списков элементов
     */
    private lateinit var list: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_notify_history)
        list = findViewById(R.id.notify_list)
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = RecyclerViewAdapter(this, DbManager(this))
    }
}