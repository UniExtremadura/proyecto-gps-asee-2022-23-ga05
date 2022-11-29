package es.unex.dinopedia;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        vista = rootView;
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

    @Override
    public void onResume() {
        super.onResume();

            if(dinoList.size()!=0){
                elegirDinosaurio();
            }

    }
}