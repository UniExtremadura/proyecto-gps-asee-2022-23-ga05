package es.unex.dinopedia;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import es.unex.dinopedia.roomdb.DinosaurioDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DinosaurioAdapter mAdapter;
    private final Context context;
    private List<Dinosaurio> dinoList;

    public FavoritoFragment(Context cont) {
        // Required empty public constructor
        context = cont;
        mAdapter = new DinosaurioAdapter(context, new DinosaurioAdapter.OnItemClickListener() {
            @Override public void onItemClick(Dinosaurio item) {
                //Snackbar.make(view, "Item "+item.getName()+" Clicked", Snackbar.LENGTH_LONG).show();
            }
        });
        dinoList = new ArrayList<>();
        cargarDinosaurios();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritoFragment newInstance(String param1, String param2, Context context) {
        FavoritoFragment fragment = new FavoritoFragment(context);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewMain = inflater.inflate(R.layout.fragment_favorito, container, false);

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
                MainActivityInterface activity = (MainActivityInterface) FavoritoFragment.this.getActivity();
                activity.classDinosaurio(item);
            }
        });

        // - Attach the adapter to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        return viewMain;
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarDinosaurios();
        mAdapter.load(dinoList);
    }


    public void cargarDinosaurios(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                DinosaurioDatabase database = DinosaurioDatabase.getInstance(context);
                dinoList = database.getDao().getFavorito();
            }
        });
    }
}