package com.dobrucali.bestmovies.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {MovieEntry.class}, version = 2)
public abstract class MovieDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "movie";

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static volatile MovieDatabase sInstance;

    public static MovieDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDatabase.class, MovieDatabase.DATABASE_NAME).build();
                }
            }
        }
        return sInstance;
    }
    public abstract MovieDao movieDao();

}
