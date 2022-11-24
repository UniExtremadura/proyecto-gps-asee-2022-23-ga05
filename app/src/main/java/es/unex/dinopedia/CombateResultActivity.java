package es.unex.dinopedia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import es.unex.dinopedia.roomdb.DinosaurioDatabase;

public class CombateResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combate_result);
        String ganador = getIntent().getStringExtra("GANADOR");
        String empate1 = getIntent().getStringExtra("EMPATE1");
        String empate2 = getIntent().getStringExtra("EMPATE2");

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                DinosaurioDatabase database = DinosaurioDatabase.getInstance(CombateResultActivity.this);
                TextView tResultado = findViewById(R.id.tResultado);
                if(ganador!=null){
                    AppExecutors.getInstance().mainThread().execute(()->tResultado.setText("El ganador del combate es: "+ ganador));
                }
                else{
                    AppExecutors.getInstance().mainThread().execute(()->tResultado.setText("El combate ha resultado en empate: " + empate1 + " y " + empate2 + " tienen la misma fuerza"));
                }
            }
        });
    }
}