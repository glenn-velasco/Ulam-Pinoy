package com.pat.ulampinoy;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;

import com.pat.ulampinoy.User.User;
import com.pat.ulampinoy.User.UserDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {User.class},
        version = 1, autoMigrations = {
//        @AutoMigration(from = 1, to = 2)
})
public abstract class AppDatabase extends androidx.room.RoomDatabase {

        public abstract UserDao userDao();

        private static volatile AppDatabase INSTANCE;

        public static final ExecutorService executorService =
                Executors.newFixedThreadPool(4);

        public static AppDatabase getDatabase(final Context context) {
                if (INSTANCE == null) {
                        synchronized (AppDatabase.class) {
                                if (INSTANCE == null) {
                                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                                        AppDatabase.class, "ulam_pinoy")
                                                .fallbackToDestructiveMigration()
                                                .build();
                                }
                        }
                }
                return INSTANCE;
        }
}
