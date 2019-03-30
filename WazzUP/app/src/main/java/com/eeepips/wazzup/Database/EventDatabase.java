package com.eeepips.wazzup.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


@Database(entities = {Event.class}, version = 1)
public abstract class EventDatabase extends RoomDatabase {

    private static EventDatabase instance;

    public abstract EventDao eventDao();

    public static synchronized EventDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    EventDatabase.class, "event_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private EventDao eventDao;

        private PopulateDbAsyncTask(EventDatabase db) {
            eventDao = db.eventDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            eventDao.insert(new Event("Title 1", "Description 1", "Venue 1", "mm/dd/yyyy", "hh:mm", 1));
            eventDao.insert(new Event("Title 2", "Description 2", "Venue 2", "mm/dd/yyyy", "hh:mm", 2));
            eventDao.insert(new Event("Title 3", "Description 3", "Venue 3", "mm/dd/yyyy", "hh:mm", 3));
            return null;
        }
    }

}
