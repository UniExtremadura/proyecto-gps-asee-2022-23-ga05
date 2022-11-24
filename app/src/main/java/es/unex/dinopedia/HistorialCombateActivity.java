package es.unex.dinopedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import es.unex.dinopedia.roomdb.HistorialCombateDatabase;
import es.unex.dinopedia.roomdb.LogroDatabase;

public class HistorialCombateActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private HistorialCombateAdapter mAdapter;
    private List<HistorialCombate> listaCombates;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_combate);
        mAdapter = new HistorialCombateAdapter(HistorialCombateActivity.this, new HistorialCombateAdapter.OnItemClickListener() {
            @Override public void onItemClick(HistorialCombate item) {
                //Snackbar.make(view, "Item "+item.getName()+" Clicked", Snackbar.LENGTH_LONG).show();
            }
        });

        mRecyclerView = findViewById(R.id.rHistorial);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(HistorialCombateActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                HistorialCombateDatabase database = HistorialCombateDatabase.getInstance(HistorialCombateActivity.this);
                listaCombates = database.getDao().getAll();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mAdapter.getItemCount()==0)
            if(listaCombates!=null)
                mAdapter.load(listaCombates);
    }
}