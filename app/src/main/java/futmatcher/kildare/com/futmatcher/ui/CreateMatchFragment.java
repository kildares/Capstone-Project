package futmatcher.kildare.com.futmatcher.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import futmatcher.kildare.com.futmatcher.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateMatchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateMatchFragment extends Fragment {

    public CreateMatchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CreateMatchFragment.
     */
    public static CreateMatchFragment newInstance() {
        CreateMatchFragment fragment = new CreateMatchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_match, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}
