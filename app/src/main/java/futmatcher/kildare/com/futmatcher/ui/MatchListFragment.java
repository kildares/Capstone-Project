package futmatcher.kildare.com.futmatcher.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import futmatcher.kildare.com.futmatcher.R;
import futmatcher.kildare.com.futmatcher.model.Match;
import futmatcher.kildare.com.futmatcher.persistence.FutMatcherFirebaseDatabase;
import futmatcher.kildare.com.futmatcher.recyclerview.MatchAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MatchListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchListFragment extends Fragment implements MatchAdapter.OnMatchItemClickListener {

    private RecyclerView mRVMatches;
    private FloatingActionButton mFab;
    private MatchListFragmentInteraction mListener;
    private MatchAdapter mAdapter;

    public MatchListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MatchListFragment.
     */
    public static MatchListFragment newInstance(MatchListFragmentInteraction listener) {
        MatchListFragment fragment = new MatchListFragment();

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

                Toast.makeText(getActivity(),Integer.toString(mRVMatches.getAdapter().getItemCount()),Toast.LENGTH_SHORT).show();

                if(mListener != null)
                    mListener.onCreateMatchButtonPressed();
            }
        });


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRVMatches.setLayoutManager(layoutManager);

        mAdapter = new MatchAdapter(getActivity(), new ArrayList<Match>(), this);
        FutMatcherFirebaseDatabase.getInstance().addChildEventListenerToReference(mAdapter);
        mRVMatches.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof MatchListFragmentInteraction){
            mListener = (MatchListFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                + " must implement MatchListFragmentInteraction");
        }
    }

    @Override
    public void onClickMatchItem(Match match) {
        if(mListener != null)
            mListener.onClickMatchItem(match);
    }

    public interface MatchListFragmentInteraction{
        void onCreateMatchButtonPressed();
        void onClickMatchItem(Match match);
    }


}
