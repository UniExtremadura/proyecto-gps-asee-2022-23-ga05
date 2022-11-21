package es.unex.dinopedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bEnciclopedia = findViewById(R.id.bEnciclopedia);
        Button bCombate = findViewById(R.id.bCombate);

        AppExecutors.getInstance().diskIO().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        LogroDatabase.getInstance(MainActivity.this).getDao().deleteAll();

                        if (LogroDatabase.getInstance(MainActivity.this).getDao().count() == 0) {
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.logros)));
                            String receiveString = "";
                            StringBuilder stringBuilder = new StringBuilder();

                            while (true) {
                                try {
                                    if (!((receiveString = bufferedReader.readLine()) != null)) break;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                stringBuilder.append("\n").append(receiveString);
                            }

                            String read = stringBuilder.toString();

                            List<Logro> logro = Arrays.asList(new Gson().fromJson(read, Logro[].class));
                            for (int i = 0; i < logro.size(); i++) {
                                Logro l = logro.get(i);
                                LogroDatabase.getInstance(MainActivity.this).getDao().insert(l);
                            }
                        }

        bEnciclopedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EnciclopediaActivity.class);
                startActivity(intent);
            }
        });


        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //DinosaurioDatabase.getInstance(MainActivity.this).getDao().deleteAll();


                if (DinosaurioDatabase.getInstance(MainActivity.this).getDao().count() == 0) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.jurassicpark)));
                    String receiveString = "";
                    StringBuilder stringBuilder = new StringBuilder();

                    while (true) {
                        try {
                            if (!((receiveString = bufferedReader.readLine()) != null)) break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        stringBuilder.append("\n").append(receiveString);
                    }

                    String read = stringBuilder.toString();

                    List<Dinosaurio> dino = Arrays.asList(new Gson().fromJson(read, Dinosaurio[].class));
                    for (int i = 0; i < dino.size(); i++) {
                        Dinosaurio d = dino.get(i);
                        DinosaurioDatabase.getInstance(MainActivity.this).getDao().insert(d);
                    }
                }
            }
        });

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                DinosaurioDatabase database = DinosaurioDatabase.getInstance(MainActivity.this);
                dino = database.getDao().getAll();
                if(dino.size()!=0){
                    AppExecutors.getInstance().mainThread().execute(()->mAdapter.load(dino));
                }
            }
        });

    }
}