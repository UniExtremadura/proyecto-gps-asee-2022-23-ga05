package es.unex.dinopedia.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.dinopedia.Dinosaurio;

@Dao
public interface DinosaurioDao {

    @Query("SELECT * FROM Dinosaurio")
    List<Dinosaurio> getAll();

    @Query("SELECT * FROM Dinosaurio WHERE id=:ID")
    Dinosaurio getDinosaurioId(long ID);

    @Insert
    long insert(Dinosaurio item);

    @Query("DELETE FROM Dinosaurio")
    void deleteAll();

    @Update
    int update(Dinosaurio item);

    @Query("SELECT COUNT (id) FROM Dinosaurio")
    int count();

    @Query("SELECT * FROM Dinosaurio WHERE favorite='1'")
    List<Dinosaurio> getFavorito();

    @Query("UPDATE Dinosaurio SET favorite='0'")
    void quitarFavorite();
}
