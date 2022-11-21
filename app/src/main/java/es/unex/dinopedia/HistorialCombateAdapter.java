package es.unex.dinopedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistorialCombateAdapter extends RecyclerView.Adapter<HistorialCombateAdapter.ViewHolder> {
    private List<HistorialCombate> mItems = new ArrayList<HistorialCombate>();
    Context mContext;

    public interface OnItemClickListener {
        void onItemClick(HistorialCombate item);     //Type of the element to be returned
    }

    private final OnItemClickListener listener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public HistorialCombateAdapter(Context context, OnItemClickListener listener) {
        mContext = context;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // - Inflate the View for every element
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.historial_combate_info, parent, false);

        return new ViewHolder(mContext,v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mItems.get(position),listener);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return mItems.size();
    }

    public void add(HistorialCombate item) {

        mItems.add(item);
        notifyDataSetChanged();

    }

    public void clear(){

        mItems.clear();
        notifyDataSetChanged();

    }

    public void load(List<HistorialCombate> items){

        mItems.clear();
        mItems = items;
        notifyDataSetChanged();

    }

    public Object getItem(int pos) {
        return mItems.get(pos);
    }

     static class ViewHolder extends RecyclerView.ViewHolder {

         private final Context mContext;

        private final TextView nombre;

        public ViewHolder(Context context, View itemView) {
            super(itemView);

            mContext = context;

            // - Get the references to every widget of the Item View

            nombre =  itemView.findViewById(R.id.tHistorial);
        }

        public void bind(final HistorialCombate hC, final OnItemClickListener listener) {

            // - Display Nombre in TextView
            nombre.setText(hC.getDinosaurio1()+ "-" + hC.getDinosaurio2() + "-" + hC.getEstado());

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(hC);
                }
            });
        }
    }

}
