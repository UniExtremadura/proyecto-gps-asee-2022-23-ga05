package es.unex.dinopedia;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Model.Logro;
import es.unex.dinopedia.Networking.Api;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkingTest {

    @Test
    public void getDinosauriosTest() throws IOException {

        Dinosaurio dino1 = new Dinosaurio();
        dino1.setId(0);
        dino1.setName("aardonyx");
        dino1.setDiet("Herbivoro");
        dino1.setLivedin("South Africa");
        dino1.setType("sauropod");
        dino1.setSpecies("celestae");
        dino1.setPeriodname("Jurasico");
        dino1.setLengthmeters("8");
        dino1.setFavorite("0");

        Dinosaurio dino2 = new Dinosaurio();
        dino2.setId(1);
        dino2.setName("abelisaurus");
        dino2.setDiet("Carnivoro");
        dino2.setLivedin("Argentina");
        dino2.setType("large theropod");
        dino2.setSpecies("comahuensis");
        dino2.setPeriodname("Cretacico");
        dino2.setLengthmeters("9");
        dino2.setFavorite("0");

        List<Dinosaurio> dinoList = new ArrayList<>();
        dinoList.add(dino1);
        dinoList.add(dino2);

        ObjectMapper objectMapper = new ObjectMapper();

        MockWebServer mockWebServer = new MockWebServer();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MockResponse mockedResponse = new MockResponse();
        mockedResponse.setResponseCode(200);
        mockedResponse.setBody(objectMapper.writeValueAsString(dinoList));
        mockWebServer.enqueue(mockedResponse);

        Api service = retrofit.create(Api.class);

        Call<List<Dinosaurio>> call = service.getListDinosaurios();

        Response<List<Dinosaurio>> response = call.execute();


        assertTrue(response != null);
        assertTrue(response.isSuccessful());


        List<Dinosaurio> dinosauriosResponse = response.body();
        assertFalse(dinosauriosResponse.isEmpty());
        assertTrue(dinosauriosResponse.size()==2);

        assertTrue(dinosauriosResponse.contains(dino1));
        assertTrue(dinosauriosResponse.contains(dino2));


        mockWebServer.shutdown();
    }

    @Test
    public void getLogrosTest() throws IOException {

        Logro logro1 = new Logro();
        logro1.setId(0);
        logro1.setName("Aprobar GPS");
        logro1.setChecked("0");

        Logro logro2 = new Logro();
        logro2.setId(1);
        logro2.setName("Aprobar ASEE");
        logro2.setChecked("0");

        List<Logro> logroList = new ArrayList<>();
        logroList.add(logro1);
        logroList.add(logro2);

        ObjectMapper objectMapper = new ObjectMapper();

        MockWebServer mockWebServer = new MockWebServer();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MockResponse mockedResponse = new MockResponse();
        mockedResponse.setResponseCode(200);
        mockedResponse.setBody(objectMapper.writeValueAsString(logroList));
        mockWebServer.enqueue(mockedResponse);

        Api service = retrofit.create(Api.class);

        Call<List<Logro>> call = service.listLogros();

        Response<List<Logro>> response = call.execute();


        assertTrue(response != null);
        assertTrue(response.isSuccessful());


        List<Logro> logrosResponse = response.body();
        assertFalse(logrosResponse.isEmpty());
        assertTrue(logrosResponse.size()==2);
        assertTrue(logrosResponse.contains(logro1));
        assertTrue(logrosResponse.contains(logro2));
        mockWebServer.shutdown();
    }
}
