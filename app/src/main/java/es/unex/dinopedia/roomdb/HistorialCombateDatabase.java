package es.unex.dinopedia.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.unex.dinopedia.HistorialCombate;

@Database(entities = {HistorialCombate.class}, version  =1)
public abstract class HistorialCombateDatabase extends RoomDatabase {
    private static HistorialCombateDatabase instance;

    public static HistorialCombateDatabase getInstance(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context, HistorialCombateDatabase.class, "historialCombate.db").build();
        }
        return instance;
    }

    public abstract HistorialCombateDao getDao();
}
