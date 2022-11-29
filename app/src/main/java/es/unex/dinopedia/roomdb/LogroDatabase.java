package es.unex.dinopedia.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.unex.dinopedia.Logro;

@Database(entities = {Logro.class}, version  =1)
public abstract class LogroDatabase extends RoomDatabase {
    private static LogroDatabase instance;

    public static LogroDatabase getInstance(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context, LogroDatabase.class, "Logro.db").build();
        }
        return instance;
    }

    public abstract LogroDao getDao();
}
