package es.unex.dinopedia;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import es.unex.dinopedia.Model.HistorialCombate;

public class HistorialCombateTest {
    private HistorialCombate historialCombate;

    @Before
    public void init(){
        historialCombate = new HistorialCombate();
        historialCombate.setId(0);
        historialCombate.setDinosaurio1("aardonyx");
        historialCombate.setDinosaurio2("aardonyx");
        historialCombate.setEstado("Empate");
    }

    @Test
    public void getDinosaurio1() {
        assertEquals("aardonyx", historialCombate.getDinosaurio1());
    }

    @Test
    public void setDinosaurio1() {
        assertEquals("aardonyx", historialCombate.getDinosaurio1());
        historialCombate.setDinosaurio1("abelisaurus");
        assertEquals("abelisaurus", historialCombate.getDinosaurio1());
    }

    @Test
    public void getDinosaurio2() {
        assertEquals("aardonyx", historialCombate.getDinosaurio1());
    }

    @Test
    public void setDinosaurio2() {
        assertEquals("aardonyx", historialCombate.getDinosaurio1());
        historialCombate.setDinosaurio1("abelisaurus");
        assertEquals("abelisaurus", historialCombate.getDinosaurio1());
    }

    @Test
    public void getEstado() {
        assertEquals("Empate", historialCombate.getEstado());
    }

    @Test
    public void setEstado() {
        assertEquals("Empate", historialCombate.getEstado());
        historialCombate.setEstado("Gana dino 1");
        assertEquals("Gana dino 1", historialCombate.getEstado());
    }

}
