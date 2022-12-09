package es.unex.dinopedia;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import es.unex.dinopedia.Model.Dinosaurio;

public class DinosaurioTest {
    
    private Dinosaurio dinosaurio;

    @Before
    public void init(){
        dinosaurio = new Dinosaurio();
        dinosaurio.setName("aardonyx");
        dinosaurio.setDiet("Herbivoro");
        dinosaurio.setLivedin("South Africa");
        dinosaurio.setType("sauropod");
        dinosaurio.setSpecies("celestae");
        dinosaurio.setPeriodname("Jurasico");
        dinosaurio.setLengthmeters("8");
        dinosaurio.setFavorite("0");
    }

    @Test
    public void getName() {
        assertEquals("aardonyx", dinosaurio.getName());
    }

    @Test
    public void setName() {
        assertEquals("aardonyx", dinosaurio.getName());
        dinosaurio.setName("abelisaurus");
        assertEquals("abelisaurus", dinosaurio.getName());
    }

    @Test
    public void getDiet() {
        assertEquals("Herbivoro", dinosaurio.getDiet());
    }

    @Test
    public void setDiet() {
        assertEquals("Herbivoro", dinosaurio.getDiet());
        dinosaurio.setDiet("Carnivoro");
        assertEquals("Carnivoro", dinosaurio.getDiet());
    }

    @Test
    public void getLivedIn() {
        assertEquals("South Africa", dinosaurio.getLivedin());
    }

    @Test
    public void setLivedIn() {
        assertEquals("South Africa", dinosaurio.getLivedin());
        dinosaurio.setLivedin("Argentina");
        assertEquals("Argentina", dinosaurio.getLivedin());
    }

    @Test
    public void getType() {
        assertEquals("sauropod", dinosaurio.getType());
    }

    @Test
    public void setType() {
        assertEquals("sauropod", dinosaurio.getType());
        dinosaurio.setType("large theropod");
        assertEquals("large theropod", dinosaurio.getType());
    }

    @Test
    public void getSpecies() {
        assertEquals("celestae", dinosaurio.getSpecies());
    }

    @Test
    public void setSpecies() {
        assertEquals("celestae", dinosaurio.getSpecies());
        dinosaurio.setSpecies("comahuensis");
        assertEquals("comahuensis", dinosaurio.getSpecies());
    }

    @Test
    public void getPeriodName() {
        assertEquals("Jurasico", dinosaurio.getPeriodname());
    }

    @Test
    public void setPeriodName() {
        assertEquals("Jurasico", dinosaurio.getPeriodname());
        dinosaurio.setPeriodname("Cretacico");
        assertEquals("Cretacico", dinosaurio.getPeriodname());
    }

    @Test
    public void getLengthMeters() {
        assertEquals("8", dinosaurio.getLengthmeters());
    }

    @Test
    public void setLengthMeters() {
        assertEquals("8", dinosaurio.getLengthmeters());
        dinosaurio.setLengthmeters("9");
        assertEquals("9", dinosaurio.getLengthmeters());
    }

    @Test
    public void getFavorite() {
        assertEquals("0", dinosaurio.getFavorite());
    }

    @Test
    public void setFavorite() {
        assertEquals("0", dinosaurio.getFavorite());
        dinosaurio.setFavorite("1");
        assertEquals("1", dinosaurio.getFavorite());
    }
}
