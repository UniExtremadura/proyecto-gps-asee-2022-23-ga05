package es.unex.dinopedia;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


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

    private static Context context;

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
    public void onResume() {
        super.onResume();

    }
}
