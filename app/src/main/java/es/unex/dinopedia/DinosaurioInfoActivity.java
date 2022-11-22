package es.unex.dinopedia;

import static es.unex.dinopedia.R.*;
import static es.unex.dinopedia.R.id.*;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.unex.dinopedia.roomdb.DinosaurioDatabase;
import es.unex.dinopedia.roomdb.UsuarioDatabase;


public class DinosaurioInfoActivity extends AppCompatActivity {

    private TextView tNombreD;
    private TextView tDietaD;
    private TextView tLivedInD;
    private TextView tTypeD;
    private TextView tSpeciesD;
    private TextView tPeriodNameD;
    private TextView tLengthMetersD;
    private Button bFavorite;
    private Bundle bundle;
    private List<Dinosaurio> dinoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_info_dinosaurio);
        bundle = getIntent().getExtras();
        View v = this.findViewById(android.R.id.content);

        Switch swFavorito = findViewById(id.sFavorito);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                UsuarioDatabase database = UsuarioDatabase.getInstance(DinosaurioInfoActivity.this);
                if(database.getDao().getUsuario()!=null)
                    swFavorito.setVisibility(v.VISIBLE);
            }
        });

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                DinosaurioDatabase database = DinosaurioDatabase.getInstance(DinosaurioInfoActivity.this);
                Dinosaurio d = database.getDao().getDinosaurioId(bundle.getLong("id"));
                if(d.getFavorite().equals("0")){
                    AppExecutors.getInstance().mainThread().execute(()->swFavorito.setChecked(false));
                }
                else{
                    AppExecutors.getInstance().mainThread().execute(()->swFavorito.setChecked(true));
                }
                AppExecutors.getInstance().mainThread().execute(()->actualizarDinosaurio(d));
                AppExecutors.getInstance().mainThread().execute(()->cambiarFavorito(d));
            }
        });
    }

    public void actualizarDinosaurio(Dinosaurio d){
        tNombreD = (TextView) findViewById(tNombre);
        tDietaD = (TextView) findViewById(tDieta);
        tLivedInD = (TextView) findViewById(tLivedIn);
        tTypeD = (TextView) findViewById(tType);
        tSpeciesD = (TextView) findViewById(tSpecies);
        tPeriodNameD = (TextView) findViewById(tPeriodName);
        tLengthMetersD = (TextView) findViewById(tLengthMeters);

        tNombreD.setText(d.getName());
        tDietaD.setText(d.getDiet());
        tLivedInD.setText(d.getLivedIn());
        tTypeD.setText(d.getType());
        tSpeciesD.setText(d.getSpecies());
        tPeriodNameD.setText(d.getPeriodName());
        tLengthMetersD.setText(d.getLengthMeters());
    }

    public void cambiarFavorito(Dinosaurio d){
        DinosaurioDatabase database = DinosaurioDatabase.getInstance(DinosaurioInfoActivity.this);

        Switch swFavorito = findViewById(id.sFavorito);

        swFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    if(swFavorito.isChecked()){
                        d.setFavorite("1");
                        database.getDao().update(d);
                    }
                    else{
                        d.setFavorite("0");
                        database.getDao().update(d);
                    }
                }
            });
            }
        });
    }
}