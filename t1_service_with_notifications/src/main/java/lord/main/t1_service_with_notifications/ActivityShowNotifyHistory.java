package lord.main.t1_service_with_notifications;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import lord.main.t1_service_with_notifications.db.DbManager;
import lord.main.t1_service_with_notifications.supporting_classes.RecyclerViewAdapter;

/**
 * Активность демонстрации истории уведомлений
 */
public class ActivityShowNotifyHistory extends AppCompatActivity {

    /**
     * Виджет для демонстрации списков элементов
     */
    RecyclerView list;
    /**
     * Адаптер для заполнения {@link #list}
     */
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notify_history);

        list = findViewById(R.id.notify_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter = new RecyclerViewAdapter(this, new DbManager(this)));
    }
}