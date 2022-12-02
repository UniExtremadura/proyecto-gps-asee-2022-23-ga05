package es.unex.dinopedia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;
import es.unex.dinopedia.databinding.ActivityMainBinding;
import es.unex.dinopedia.roomdb.UsuarioDatabase;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import es.unex.dinopedia.databinding.ActivityMainBinding;
import es.unex.dinopedia.roomdb.DinosaurioDatabase;


public class MainFragment extends Fragment {

    private View vista;
    private Context context;
    ActivityMainBinding binding;
    private DinosaurioAdapter mAdapter;
    private boolean sesionIniciada;
    private DateFormat formatoFecha;
    private Date fecha;
    private int dinosaurioDelDia;
    private List<Dinosaurio> dinoList;
    private List<Dinosaurio> copiaDinosaurio;

    public MainFragment(){
    }

    public MainFragment(Context cont, ActivityMainBinding bind) {
        context = cont;
        dinoList=new ArrayList<>();
        mAdapter = new DinosaurioAdapter(context, new DinosaurioAdapter.OnItemClickListener() {
            @Override public void onItemClick(Dinosaurio item) {
            }
        });
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                DinosaurioDatabase database = DinosaurioDatabase.getInstance(context);
                dinoList = database.getDao().getAll();
                AppExecutors.getInstance().mainThread().execute(()->copiaDinosaurio=dinoList);
            }
        });
        formatoFecha = new SimpleDateFormat("dd/MM/yy");
        long momento = System.currentTimeMillis();
        fecha = new Date(momento);
        binding = bind;

        AppExecutors.getInstance().diskIO().execute(() -> {
            UsuarioDatabase database = UsuarioDatabase.getInstance(context);
            if(database.getDao().getUsuario()!=null)
                sesionIniciada=true;
            else
                sesionIniciada=false;
        });

        if(copiaDinosaurio!=null) {
            if (copiaDinosaurio.size() != 0) {
                Random random_method = new Random();
                dinosaurioDelDia = random_method.nextInt(copiaDinosaurio.size());
            } else {
                dinosaurioDelDia = 0;
            }
        }
    }


    public static MainFragment newInstance(Context cont, ActivityMainBinding bind) {
        MainFragment fragment = new MainFragment(cont, bind);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
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

    public void elegirDinosaurio(){
        String nombre;
        Random random_method = new Random();
        if(copiaDinosaurio!=null) {
            if (copiaDinosaurio.size() != 0) {
                int nuevoDinosaurio = random_method.nextInt(copiaDinosaurio.size());
                long momento = System.currentTimeMillis();
                Date fechaActual = new Date(momento);
                String fechaActualS = formatoFecha.format(fechaActual);
                String fechaS = formatoFecha.format(fecha);
                if (!fechaS.equals(fechaActualS)) {
                    fecha = fechaActual;
                    nuevoDinosaurio = random_method.nextInt(copiaDinosaurio.size());
                    Log.d("fecha", fechaS);
                    dinosaurioDelDia = nuevoDinosaurio;
                }
                Dinosaurio d = dinoList.get(dinosaurioDelDia);
                Log.d("HOLA", d.getName());
                nombre = d.getName();

                if (dinoList.size() != 0) {
                    final TextView dinoDia = (TextView) vista.findViewById(R.id.nombreDino);
                    dinoDia.setText(nombre);
                }
            }
        }
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
        if(dinoList.size()!=0){
            elegirDinosaurio();
        }
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