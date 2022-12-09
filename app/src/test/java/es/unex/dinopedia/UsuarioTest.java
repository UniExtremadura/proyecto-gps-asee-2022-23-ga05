package es.unex.dinopedia;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import es.unex.dinopedia.Model.Usuario;

public class UsuarioTest {

    private Usuario usuario;

    @Before
    public void init(){
        usuario = new Usuario();
        usuario.setName("user");
        usuario.setId(12);
        usuario.setInfoDino(false);
        usuario.setModo(false);
    }

    @Test
    public void getName() {
        assertEquals("user", usuario.getName());
    }

    @Test
    public void setName() {
        assertEquals("user", usuario.getName());
        usuario.setName("user2");
        assertEquals("user2", usuario.getName());
    }

    @Test
    public void getId() {
        assertEquals(12, usuario.getId());
    }

    @Test
    public void setId() {
        assertEquals(12, usuario.getId());
        usuario.setId(13);
        assertEquals(13, usuario.getId());
    }

    @Test
    public void getInfoDino() {
        assertEquals(false, usuario.isInfoDino());
    }

    @Test
    public void setInfoDino() {
        assertEquals(false, usuario.isInfoDino());
        usuario.setInfoDino(true);
        assertEquals(true, usuario.isInfoDino());
    }

    @Test
    public void getModo() {
        assertEquals(false, usuario.isModo());
    }

    @Test
    public void setModo() {
        assertEquals(false, usuario.isModo());
        usuario.setModo(true);
        assertEquals(true, usuario.isModo());
    }

}
