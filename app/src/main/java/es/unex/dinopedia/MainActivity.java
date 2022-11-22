package es.unex.dinopedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.unex.dinopedia.databinding.ActivityMainBinding;
import es.unex.dinopedia.roomdb.DinosaurioDatabase;

public class MainActivity extends AppCompatActivity implements MainActivityInterface{

    ActivityMainBinding binding;
    FragmentManager fragmentManager = getSupportFragmentManager();
    DinosaurioAdapter mAdapter = new DinosaurioAdapter(MainActivity.this, new DinosaurioAdapter.OnItemClickListener() {
        @Override public void onItemClick(Dinosaurio item) {
            //Snackbar.make(view, "Item "+item.getName()+" Clicked", Snackbar.LENGTH_LONG).show();
        }
    });
    List<Dinosaurio> dino = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainFragment mF = new MainFragment(MainActivity.this, binding);
        replaceFragment(mF);

        EnciclopediaFragment eF = new EnciclopediaFragment(MainActivity.this);
        FavoritoFragment fF = new FavoritoFragment(MainActivity.this);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.principal:
                    replaceFragment(mF);
                    break;
                case R.id.enciclopedia:
                    replaceFragment(eF);
                    break;
                case R.id.batalla:
                    replaceFragment(new CombateFragment());
                    break;
                case R.id.favorito:
                    replaceFragment(fF);
                    break;
                case R.id.logros:
                    replaceFragment(new AlbumFragment());
                    break;
            }
            return true;
        });


    }

    @Override
    public void classDinosaurio(Dinosaurio d) {
        Intent intent = new Intent(MainActivity.this, DinosaurioInfoActivity.class);
        intent.putExtra("id", d.getId());
        startActivity(intent);
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }


}