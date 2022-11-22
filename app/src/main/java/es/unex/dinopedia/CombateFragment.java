package es.unex.dinopedia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import es.unex.dinopedia.roomdb.DinosaurioDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CombateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CombateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private static DinosaurioAdapter mAdapter;
    private Spinner mSpinnerdino1;
    private Spinner mSpinnerdino2;
    private List<String> dinoListNombres = new ArrayList<>();
    private static Context context;
    private ArrayAdapter adp;
    private Dinosaurio dinosaurio1;
    private Dinosaurio dinosaurio2;

    public CombateFragment(Context cont) {
        // Required empty public constructor
        context = cont;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment CombateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CombateFragment newInstance(Context cont) {
        CombateFragment fragment = new CombateFragment(cont);
        Bundle args = new Bundle();
        context = cont;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewMain = inflater.inflate(R.layout.fragment_combate, container, false);

        mSpinnerdino1 = (Spinner) viewMain.findViewById(R.id.mSpinnerdino1);
        mSpinnerdino2 = (Spinner) viewMain.findViewById(R.id.mSpinnerdino2);

        //adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                DinosaurioDatabase database = DinosaurioDatabase.getInstance(context);
                dinoListNombres=database.getDao().getNombres();
            }
        });

        Button bCombate = viewMain.findViewById(R.id.bCombate);
        bCombate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dino1 = mSpinnerdino1.getSelectedItem().toString();
                String dino2 = mSpinnerdino2.getSelectedItem().toString();
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        DinosaurioDatabase database = DinosaurioDatabase.getInstance(context);
                        dinosaurio1=database.getDao().getDinosaurioString(dino1);
                        dinosaurio2=database.getDao().getDinosaurioString(dino2);
                    }
                });
                combatir();
            }
        });

        return viewMain;
    }

    public void cargarSpinner(){
        adp = new ArrayAdapter<String>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dinoListNombres);
        if (adp!= null) {
            mSpinnerdino1.setAdapter(adp);
            mSpinnerdino2.setAdapter(adp);
        }
    }

    public void combatir(){
        if(dinosaurio1!=null && dinosaurio2!=null) {
            Intent intent = new Intent(context, CombateResultActivity.class);
            if (Float.parseFloat(dinosaurio1.getLengthMeters()) < Float.parseFloat(dinosaurio2.getLengthMeters())) {
                intent.putExtra("GANADOR", dinosaurio2.getName());

            }
            if (Float.parseFloat(dinosaurio1.getLengthMeters()) > Float.parseFloat(dinosaurio2.getLengthMeters())) {
                intent.putExtra("GANADOR", dinosaurio1.getName());

            }
            if (Float.parseFloat(dinosaurio1.getLengthMeters()) == Float.parseFloat(dinosaurio2.getLengthMeters())) {
                intent.putExtra("EMPATE1", dinosaurio1.getName());
                intent.putExtra("EMPATE2", dinosaurio2.getName());

            }
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarSpinner();

    }
}
