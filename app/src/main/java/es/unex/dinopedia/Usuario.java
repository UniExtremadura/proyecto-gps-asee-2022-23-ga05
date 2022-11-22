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
    @PrimaryKey (autoGenerate=true)
    private long id;

    @NonNull
    private String name;

    private boolean modo;

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
    public Usuario(long id, String name, boolean modo) {
        super();
        this.id = id;
        this.name = name;
        this.modo = modo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isModo() {
        return modo;
    }

    public void setModo(boolean modo) {
        this.modo = modo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id == usuario.id && modo == usuario.modo && name.equals(usuario.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, modo);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", modo=" + modo +
                '}';
    }
}
