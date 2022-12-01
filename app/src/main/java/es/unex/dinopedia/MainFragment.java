package es.unex.dinopedia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;
import es.unex.dinopedia.databinding.ActivityMainBinding;
import es.unex.dinopedia.roomdb.UsuarioDatabase;

public class MainFragment extends Fragment {

    private View vista;
    private Context context;
    ActivityMainBinding binding;
    private DinosaurioAdapter mAdapter;
    private boolean sesionIniciada;
    private List<Dinosaurio> dinoList;

    public MainFragment() {
        // Required empty public constructor
    }


    public MainFragment (Context cont, ActivityMainBinding bind) {
        context = cont;
        dinoList=new ArrayList<>();
        mAdapter = new DinosaurioAdapter(context, item -> {});
        binding = bind;
        AppExecutors.getInstance().diskIO().execute(() -> {
            UsuarioDatabase database = UsuarioDatabase.getInstance(context);
            if(database.getDao().getUsuario()!=null)
                sesionIniciada=true;
            else
                sesionIniciada=false;
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        vista = rootView;
        Button bIniciarSesion = rootView.findViewById(R.id.bIniciarSesion);
        Button bCuenta = rootView.findViewById(R.id.bCuenta);

        bIniciarSesion.setOnClickListener(v -> {
            Intent intent = new Intent(context, IniciarSesionActivity.class);
            startActivityForResult(intent, 1);
        });

        bCuenta.setOnClickListener(v -> AppExecutors.getInstance().diskIO().execute(() -> {
            UsuarioDatabase database = UsuarioDatabase.getInstance(context);
            Intent intent = new Intent(context, CuentaActivity.class);
            intent.putExtra("USUARIO", database.getDao().getUsuario().getName());
            AppExecutors.getInstance().mainThread().execute(()->startActivityForResult(intent, 2));
        }));

        return rootView;
    }

    /*
    @Override
    public void onClick(View v) {
    }
    */

    public void mostrarBotones(){
        binding.bottomNavigationView.getMenu().getItem(3).setVisible(sesionIniciada);
        binding.bottomNavigationView.getMenu().getItem(4).setVisible(sesionIniciada);
        Button bCuenta = vista.findViewById(R.id.bCuenta);
        Button bIniciarSesion = vista.findViewById(R.id.bIniciarSesion);
        if(sesionIniciada==true) {
            bCuenta.setVisibility(vista.VISIBLE);
            bIniciarSesion.setVisibility(vista.INVISIBLE);
        }
        else{
            bCuenta.setVisibility(vista.INVISIBLE);
            bIniciarSesion.setVisibility(vista.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mostrarBotones();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            AppExecutors.getInstance().diskIO().execute(() -> {
                UsuarioDatabase database = UsuarioDatabase.getInstance(context);
                if(database.getDao().getUsuario()!=null)
                    sesionIniciada=true;
                else
                    sesionIniciada=false;
            });
        }
        if(requestCode == 2){
            AppExecutors.getInstance().diskIO().execute(() -> {
                UsuarioDatabase database = UsuarioDatabase.getInstance(context);
                if(database.getDao().getUsuario()!=null)
                    sesionIniciada=true;
                else
                    sesionIniciada=false;
            });
        }
    }

}