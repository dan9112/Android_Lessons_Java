package lord.main.t1_service_with_notifications.supporting_classes;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import lord.main.t1_service_with_notifications.R;
import lord.main.t1_service_with_notifications.db.DbManager;

/**
 * Клас-адаптер для заполнения {@link RecyclerView виджета списка} {@link RecyclerViewItem элементами}
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    /**
     * Список {@link RecyclerViewItem элементов}, который отображается в {@link RecyclerView виджете списка}
     */
    ArrayList<RecyclerViewItem> notifies;
    /**
     * Контекст, в котором создан {@link RecyclerView виджет списка}
     */
    Context context;
    /**
     * Объект для управления БД
     */
    DbManager manager;

    public RecyclerViewAdapter(Context context, DbManager manager) {
        this.manager = manager;
        manager.open();
        notifies = manager.getAllHistory();
        manager.close();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notify_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.setData(notifies.get(position), position);
    }

    @Override
    public int getItemCount() {
        return notifies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView id, channelId, title, text;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.imageView);
            id = itemView.findViewById(R.id.not_id);
            channelId = itemView.findViewById(R.id.not_channel_id);
            title = itemView.findViewById(R.id.not_title);
            text = itemView.findViewById(R.id.not_text);
        }

        /**
         * Функция заполнения элемента {@link RecyclerView виджета списка}
         *
         * @param item {@link RecyclerViewItem элемент списка} с данными, которые необходимо продемонстрировать пользователю
         */
        public void setData(RecyclerViewItem item, int position) {
            icon.setImageResource(item.iconId);
            icon.setColorFilter(item.iconColor, PorterDuff.Mode.MULTIPLY);//SRC_IN
            id.setText(String.valueOf(item.id));
            channelId.setText(item.channelId);
            title.setText(item.title);
            text.setText(item.text);
            itemView.setOnClickListener(v -> {
                AlertDialog.Builder adb = new AlertDialog.Builder(context);
                adb.setMessage("Вы уверены, что хотите удалить строку из истории?");
                adb.setCancelable(true);
                adb.setPositiveButton("Да", (dialog, which) -> {
                    manager.open();
                    manager.removeHistoryRow(item.id);
                    manager.close();
                    notifies.remove(position);
                    notifyItemRemoved(position);
                });
                adb.setNegativeButton("Нет", null);
                adb.show();
            });
        }
    }
}
