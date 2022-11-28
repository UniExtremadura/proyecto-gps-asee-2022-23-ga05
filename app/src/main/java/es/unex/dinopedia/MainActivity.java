package es.unex.dinopedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.unex.dinopedia.roomdb.DinosaurioDatabase;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bEnciclopedia = findViewById(R.id.bEnciclopedia);
        Button bCombate = findViewById(R.id.bCombate);

        DinosaurioAdapter mAdapter = new DinosaurioAdapter(MainActivity.this, new DinosaurioAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Dinosaurio item) {
                //Snackbar.make(view, "Item "+item.getName()+" Clicked", Snackbar.LENGTH_LONG).show();
            }
        });

        final List<Dinosaurio>[] dino = new List[]{new ArrayList<>()};

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                bCombate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, CombateActivity.class);
                        startActivity(intent);
                    }
                });

                bEnciclopedia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, EnciclopediaActivity.class);
                        startActivity(intent);
                    }
                });

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

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        DinosaurioDatabase database = DinosaurioDatabase.getInstance(MainActivity.this);
                        dino[0] = database.getDao().getAll();
                        if (dino[0].size() != 0) {
                            AppExecutors.getInstance().mainThread().execute(() -> mAdapter.load(dino[0]));
                        }
                    }
                });


            }
        });
    }
}

