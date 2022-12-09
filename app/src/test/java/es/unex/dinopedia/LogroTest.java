package es.unex.dinopedia;
import es.unex.dinopedia.Model.Logro;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class LogroTest {

    private Logro logro;

    @Before
    public void init() {
        logro = new Logro();
        logro.setName("Inicia Sesión en la aplicación");
        logro.setChecked("0");
    }

    @Test
    public void getNameTest() {
        assertEquals("Inicia Sesión en la aplicación",logro.getName());
    }

    @Test
    public void setNameTest() {
        assertEquals("Inicia Sesión en la aplicación",logro.getName());
        logro.setName("Realiza tu primer combate con la aplicación");
        assertEquals("Realiza tu primer combate con la aplicación",logro.getName());
    }

    @Test
    public void getCheckedTest() {
        assertEquals("0",logro.getChecked());
    }

    @Test
    public void setCheckedTest() {
        assertEquals("0",logro.getChecked());
        logro.setChecked("1");
        assertEquals("1",logro.getChecked());
    }
}