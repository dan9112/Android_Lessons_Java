package lord.main.t1_service_with_notifications.room_db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TableChannels.class, TableHistory.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TableChannelsDao channelsDao();

    public abstract TableHistoryDao historyDao();
}
