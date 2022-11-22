package es.unex.dinopedia.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.unex.dinopedia.Usuario;

@Database(entities = {Usuario.class}, version  =1)
public abstract class UsuarioDatabase extends RoomDatabase {
    private static UsuarioDatabase instance;

    public static UsuarioDatabase getInstance(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context, UsuarioDatabase.class, "usuario.db").build();
        }
        return instance;
    }

    public abstract UsuarioDao getDao();
}
