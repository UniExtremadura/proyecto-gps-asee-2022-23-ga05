package es.unex.dinopedia;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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

public class EnciclopediaFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DinosaurioAdapter mAdapter;
    private Context context;
    private List<Dinosaurio> dinoList;
    private List<Dinosaurio> dinoOpciones;
    private Spinner opciones;
    private boolean aplicar=false;

    public EnciclopediaFragment(){
    }

    public EnciclopediaFragment(Context cont) {
        // Required empty public constructor
        context = cont;
        dinoList=new ArrayList<>();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                DinosaurioDatabase database = DinosaurioDatabase.getInstance(context);
                dinoList = database.getDao().getAll();
            }
        });
        mAdapter = new DinosaurioAdapter(context, new DinosaurioAdapter.OnItemClickListener() {
            @Override public void onItemClick(Dinosaurio item) {
                //Snackbar.make(view, "Item "+item.getName()+" Clicked", Snackbar.LENGTH_LONG).show();
            }
        });
    }


    public static EnciclopediaFragment newInstance(Context context) {
        EnciclopediaFragment fragment = new EnciclopediaFragment(context);
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
        View viewMain = inflater.inflate(R.layout.fragment_enciclopedia, container, false);

        mRecyclerView = viewMain.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        // - Set a Linear Layout Manager to the RecyclerView
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DinosaurioAdapter(context, new DinosaurioAdapter.OnItemClickListener() {
            @Override public void onItemClick(Dinosaurio item) {
                //Snackbar.make(view, "Item "+item.getName()+" Clicked", Snackbar.LENGTH_LONG).show();
                MainActivityInterface activity = (MainActivityInterface) EnciclopediaFragment.this.getActivity();
                activity.classDinosaurio(item);
            }
        });

        // - Attach the adapter to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        opciones = viewMain.findViewById(R.id.sOpciones);
        ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(context, R.array.opciones, android.R.layout.simple_spinner_item);
        opciones.setAdapter(adp);

        Button bAplicar = viewMain.findViewById(R.id.bAplicar);
        bAplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        DinosaurioDatabase database = DinosaurioDatabase.getInstance(context);
                        dinoOpciones=database.getDao().getOpciones(opciones.getSelectedItem().toString());
                    }
                });
                aplicar=true;
            }
        });

        Button bRestaurar = viewMain.findViewById(R.id.bRestaurar);
        bRestaurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aplicar=false;
            }
        });

        return viewMain;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mAdapter.getItemCount()==0)
            if(aplicar)
                mAdapter.load(dinoOpciones);
            else
                mAdapter.load(dinoList);
    }
}