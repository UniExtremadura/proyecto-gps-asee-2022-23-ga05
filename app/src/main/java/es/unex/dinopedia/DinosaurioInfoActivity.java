package es.unex.dinopedia;

import static es.unex.dinopedia.R.*;
import static es.unex.dinopedia.R.id.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import es.unex.dinopedia.roomdb.DinosaurioDatabase;
import es.unex.dinopedia.roomdb.LogroDatabase;
import es.unex.dinopedia.roomdb.UsuarioDatabase;

public class DinosaurioInfoActivity extends AppCompatActivity {

    private TextView tNombreD;
    private TextView tDietaD;
    private TextView tLivedInD;
    private TextView tTypeD;
    private TextView tSpeciesD;
    private TextView tPeriodNameD;
    private TextView tLengthMetersD;
    private Bundle bundle;
    private Button bFavorite;
    private boolean infoDino=false;
    private List<Dinosaurio> dinoList = new ArrayList<>();
    private String body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_info_dinosaurio);
        bundle = getIntent().getExtras();
        View v = this.findViewById(android.R.id.content);

        Switch swFavorito = findViewById(R.id.sFavorito);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Usuario u = UsuarioDatabase.getInstance(DinosaurioInfoActivity.this).getDao().getUsuario();
                if(u!=null)
                    infoDino=u.isInfoDino();
            }
        });

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                DinosaurioDatabase database = DinosaurioDatabase.getInstance(DinosaurioInfoActivity.this);
                Dinosaurio d = database.getDao().getDinosaurioId(bundle.getLong("id"));
                AppExecutors.getInstance().mainThread().execute(()->actualizarDinosaurio(d));
                AppExecutors.getInstance().mainThread().execute(()->mostrarImagenes(d));
            }
        });

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

        Button bt = findViewById(bCompartir);

        bt.setOnClickListener(new View.OnClickListener () {

            @Override
            public void onClick(View v){
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                body = "¡Mira que dinosaurio más interesante! -> https://acortar.link/RzE6K";
                myIntent.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(myIntent, "Share Using"));

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

        Switch swFavorito = findViewById(R.id.sFavorito);

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
                    if(database.getDao().getFavorito().size()>=1) {
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                LogroDatabase database2 = LogroDatabase.getInstance(DinosaurioInfoActivity.this);
                                Logro l = database2.getDao().getLogro("Marca tu primer dinosaurio favorito");
                                l.setChecked("1");
                                database2.getDao().update(l);
                            }
                        });
                    }
                }
            });
            }
        });
    }

    public void mostrarImagenes(Dinosaurio d){
        if(infoDino){
            View v = this.findViewById(android.R.id.content);
            if(d.getDiet()!=null) {
                if (d.getDiet().equals("Carnivoro")) {
                    ImageView iV = findViewById(iVCarnivoro);
                    tDietaD.setVisibility(v.INVISIBLE);
                    iV.setVisibility(v.VISIBLE);
                }
                if (d.getDiet().equals("Herbivoro")) {
                    ImageView iV = findViewById(iVHerbivoro);
                    tDietaD.setVisibility(v.INVISIBLE);
                    iV.setVisibility(v.VISIBLE);
                }
                if (d.getDiet().equals("Omnivoro")) {
                    ImageView iV = findViewById(iVOmnivoro);
                    tDietaD.setVisibility(v.INVISIBLE);
                    iV.setVisibility(v.VISIBLE);
                }
            }
            if(d.getPeriodName()!=null) {
                if (d.getPeriodName().equals("Jurasico")) {
                    ImageView iV = findViewById(iVJurasico);
                    tPeriodNameD.setVisibility(v.INVISIBLE);
                    iV.setVisibility(v.VISIBLE);
                }
                if (d.getPeriodName().equals("Cretacico")) {
                    ImageView iV = findViewById(iVCretacico);
                    tPeriodNameD.setVisibility(v.INVISIBLE);
                    iV.setVisibility(v.VISIBLE);
                }
                if (d.getPeriodName().equals("Triasico")) {
                    ImageView iV = findViewById(iVTriasico);
                    tPeriodNameD.setVisibility(v.INVISIBLE);
                    iV.setVisibility(v.VISIBLE);
                }
            }
        }
    }
}