package futmatcher.kildare.com.futmatcher.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import futmatcher.kildare.com.futmatcher.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MatchListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchListFragment extends Fragment{

    RecyclerView mRVMatches;
    FloatingActionButton mFab;
    CreateMatchButton mListener;

    public MatchListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MatchListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MatchListFragment newInstance(CreateMatchButton listener) {
        MatchListFragment fragment = new MatchListFragment();

        fragment.addCreateMatchButtonListener(listener);

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

        View view = inflater.inflate(R.layout.fragment_match_list, container, false);
        mRVMatches = view.findViewById(R.id.rv_match_list);
        mFab = view.findViewById(R.id.fab_create_match);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null)
                    mListener.onCreateMatchButtonPressed();
            }
        });

        return view;
    }

    public void addCreateMatchButtonListener(CreateMatchButton listener){
        mListener = listener;
    }

    public interface CreateMatchButton{
        void onCreateMatchButtonPressed();
    }

}
