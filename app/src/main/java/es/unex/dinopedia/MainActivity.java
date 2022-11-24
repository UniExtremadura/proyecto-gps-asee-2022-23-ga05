package es.unex.dinopedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.unex.dinopedia.databinding.ActivityMainBinding;
import es.unex.dinopedia.roomdb.DinosaurioDatabase;
import es.unex.dinopedia.roomdb.HistorialCombateDatabase;
import es.unex.dinopedia.roomdb.LogroDatabase;

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
                LogroDatabase.getInstance(MainActivity.this).getDao().deleteAll();
                HistorialCombateDatabase.getInstance(MainActivity.this).getDao().deleteAll();
                /*if(UsuarioDatabase.getInstance(MainActivity.this).getDao().getUsuario()!=null) {
                    //UsuarioDatabase.getInstance(MainActivity.this).getDao().deleteAll();
                    UsuarioDatabase database = UsuarioDatabase.getInstance(MainActivity.this);
                    Usuario u = database.getDao().getUsuario();
                    //database.getDao().deleteUsuarioID(u.getId());
                }*/
                quitarFavoritos();
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

        List<Logro> logro = new ArrayList<>();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainFragment mF = new MainFragment(MainActivity.this, binding);
        replaceFragment(mF);

        EnciclopediaFragment eF = new EnciclopediaFragment(MainActivity.this);
        FavoritoFragment fF = new FavoritoFragment(MainActivity.this);
        CombateFragment cF = new CombateFragment(MainActivity.this);
        AlbumFragment aF = new AlbumFragment(MainActivity.this);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.principal:
                    replaceFragment(mF);
                    break;
                case R.id.enciclopedia:
                    replaceFragment(eF);
                    break;
                case R.id.batalla:
                    replaceFragment(cF);
                    break;
                case R.id.favorito:
                    replaceFragment(fF);
                    break;
                case R.id.logros:
                    replaceFragment(aF);
                    break;
            }
            return true;
        });


    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void classDinosaurio(Dinosaurio d) {
        Intent intent = new Intent(MainActivity.this, DinosaurioInfoActivity.class);
        intent.putExtra("id", d.getId());
        startActivity(intent);
    }

    public void quitarFavoritos(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                DinosaurioDatabase database = DinosaurioDatabase.getInstance(MainActivity.this);
                database.getDao().quitarFavorite();
            }
        });
    }
}