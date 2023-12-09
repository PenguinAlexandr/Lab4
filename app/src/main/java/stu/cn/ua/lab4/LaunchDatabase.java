package stu.cn.ua.lab4;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Launch.class}, version = 1)
public abstract class LaunchDatabase extends RoomDatabase {

    public abstract LaunchDao launchDao();

    private static LaunchDatabase instance;

    public static synchronized LaunchDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            LaunchDatabase.class, "launch_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
