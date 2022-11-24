package es.unex.dinopedia.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.dinopedia.Usuario;

@Dao
public interface UsuarioDao {

    @Query("SELECT * FROM Usuario")
    List<Usuario> getAll();

    @Query("SELECT * FROM Usuario")
    Usuario getUsuario();

    @Insert
    long insert(Usuario item);

    @Query("DELETE FROM Usuario")
    void deleteAll();

    @Query("DELETE FROM Usuario WHERE id=:ID")
    void deleteUsuarioID(long ID);

    @Query("UPDATE Usuario SET modo =:MODO WHERE id=:ID")
    void updateModoUsuario(long ID, boolean MODO);

    @Update
    int update(Usuario item);
}