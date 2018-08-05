package futmatcher.kildare.com.futmatcher.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import futmatcher.kildare.com.futmatcher.R;
import futmatcher.kildare.com.futmatcher.model.Match;
import futmatcher.kildare.com.futmatcher.persistence.FutMatcherFirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateMatchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateMatchFragment extends Fragment {

    Button mCreateButton;
    EditText mMatchTitle;
    EditText mMatchDate;
    EditText mMatchLocation;
    EditText mMinPlayers;
    EditText mMaxPlayers;
    Spinner mNumPlayers;

    public static final String LOG_TAG = "CreateMatchFragment";

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
        View view = inflater.inflate(R.layout.fragment_create_match, container, false);

        mCreateButton =  view.findViewById(R.id.bt_create_match);

        mMatchTitle = view.findViewById(R.id.et_match_title);
        mMatchLocation = view.findViewById(R.id.et_match_location);
        mMatchDate = view.findViewById(R.id.et_match_date);
        mMaxPlayers = view.findViewById(R.id.et_max_players);
        mMinPlayers = view.findViewById(R.id.et_min_players);
        mNumPlayers = view.findViewById(R.id.sp_num_players);

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = mMatchTitle.getText().toString();
                String location = mMatchLocation.getText().toString();
                String date = mMatchDate.getText().toString();
                if(!title.isEmpty() && !location.isEmpty() && !date.isEmpty() && isValidMinPlayers() && isValidMaxPlayers()){
                    createMatch();
                }
                else{
                    Log.i(LOG_TAG ,"Unable to create match");
                    Toast.makeText(getActivity(),getActivity().getString(R.string.toast_missing_field),Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    public boolean isValidMaxPlayers()
    {
        Integer num = Integer.parseInt(mNumPlayers.getSelectedItem().toString());
        Integer maxPlayers = Integer.parseInt(mMaxPlayers.getText().toString());
        Integer minPlayers = Integer.parseInt(mMinPlayers.getText().toString());

        return maxPlayers >= num && maxPlayers >= minPlayers;
    }

    public boolean isValidMinPlayers()
    {
        Integer num = Integer.parseInt(mNumPlayers.getSelectedItem().toString());
        Integer minPlayers = Integer.parseInt(mMinPlayers.getText().toString());
        return minPlayers <= (2*num);
    }

    public void createMatch()
    {
        String title = mMatchTitle.getText().toString();
        String location = mMatchLocation.getText().toString();
        String date = mMatchDate.getText().toString();
        String maxPlayers = mMaxPlayers.getText().toString();
        String minPlayers = mMinPlayers.getText().toString();
        Match match = new Match(title, location, date, mNumPlayers.getSelectedItem().toString(), minPlayers, maxPlayers);

        FutMatcherFirebaseDatabase firebaseDatabase = FutMatcherFirebaseDatabase.getInstance();
        firebaseDatabase.addMatch(match);
        Log.i(LOG_TAG,"Creating Match");
    }


}
