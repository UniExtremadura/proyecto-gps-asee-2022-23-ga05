package es.unex.dinopedia;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "Usuario")
public class Usuario {

    @Ignore
    public static final String ITEM_SEP = System.getProperty("line.separator");
    @SerializedName("name")
    @Expose
    @PrimaryKey
    @NonNull
    private String name;

    /**
     * No args constructor for use in serialization
     *
     */
    @Ignore
    public Usuario() {
    }

    /**
     * @param name
     */
    public Usuario(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dinosaurio{" +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String toLog() {
        return "Name:" + name + ITEM_SEP;
    }
}
