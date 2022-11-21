package es.unex.dinopedia;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import es.unex.dinopedia.databinding.ActivityMainBinding;
import es.unex.dinopedia.roomdb.DinosaurioDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View vista;
    private Context context;
    ActivityMainBinding binding;
    private DinosaurioAdapter mAdapter;
    private DateFormat formatoFecha;
    private Date fecha;
    private boolean sesionIniciada;
    private int dinosaurioDelDia;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Dinosaurio> dinoList;
    private List<Dinosaurio> copiaDinosaurio;

    public MainFragment(){
    }

    public MainFragment(Context cont, ActivityMainBinding bind) {
        context = cont;
        dinoList=new ArrayList<>();
        mAdapter = new DinosaurioAdapter(context, new DinosaurioAdapter.OnItemClickListener() {
            @Override public void onItemClick(Dinosaurio item) {
                //Snackbar.make(view, "Item "+item.getName()+" Clicked", Snackbar.LENGTH_LONG).show();
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

        if(dinoList!=null) {
            if (dinoList.size() != 0) {
                Random random_method = new Random();
                dinosaurioDelDia = random_method.nextInt(copiaDinosaurio.size());
            } else {
                dinosaurioDelDia = 0;
            }
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2, Context cont, ActivityMainBinding bind) {
        MainFragment fragment = new MainFragment(cont, bind);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        vista = rootView;

        return rootView;
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        //ft.replace(R.id.frame_container, fragment).addToBackStack(null).commit();

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