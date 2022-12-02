package es.unex.dinopedia;

import static es.unex.dinopedia.R.*;
import static es.unex.dinopedia.R.id.*;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import es.unex.dinopedia.roomdb.DinosaurioDatabase;

public class DinosaurioInfoActivity extends AppCompatActivity {

    private TextView tNombreD;
    private TextView tDietaD;
    private TextView tLivedInD;
    private TextView tTypeD;
    private TextView tSpeciesD;
    private TextView tPeriodNameD;
    private TextView tLengthMetersD;
    private Bundle bundle;
    private List<Dinosaurio> dinoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_info_dinosaurio);
        bundle = getIntent().getExtras();
        View v = this.findViewById(android.R.id.content);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                DinosaurioDatabase database = DinosaurioDatabase.getInstance(DinosaurioInfoActivity.this);
                Dinosaurio d = database.getDao().getDinosaurioId(bundle.getLong("id"));
                AppExecutors.getInstance().mainThread().execute(()->actualizarDinosaurio(d));
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
}