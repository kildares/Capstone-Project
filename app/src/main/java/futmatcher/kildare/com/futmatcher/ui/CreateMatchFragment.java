package futmatcher.kildare.com.futmatcher.ui;

import android.content.Context;
import android.content.Intent;
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

import java.time.DateTimeException;
import java.time.LocalDate;

import futmatcher.kildare.com.futmatcher.R;
import futmatcher.kildare.com.futmatcher.model.Match;
import futmatcher.kildare.com.futmatcher.persistence.FirebaseIntentService;

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
    CreateMatchFragmentInteraction mListener;

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

        if(savedInstanceState != null){
        }

        View view = inflater.inflate(R.layout.fragment_create_match, container, false);

        mCreateButton =  view.findViewById(R.id.bt_create_match);

        mMatchTitle = view.findViewById(R.id.et_match_title);
        mMatchLocation = view.findViewById(R.id.et_match_location);
        mMatchDate = view.findViewById(R.id.et_match_date);
        mMaxPlayers = view.findViewById(R.id.et_max_players);
        mMinPlayers = view.findViewById(R.id.et_min_players);
        mNumPlayers = view.findViewById(R.id.sp_num_players);

        mCreateButton.setOnClickListener(listener -> {

			String title = mMatchTitle.getText().toString();
			String location = mMatchLocation.getText().toString();
			String date = mMatchDate.getText().toString();
			if(isValidTitle(title) && isValidLocation(location) && isValidDate(date) && isValidMinPlayers() && isValidMaxPlayers()){
				createMatch();
                mListener.onMatchCreated();
			}
			else{
				Log.i(LOG_TAG ,getActivity().getString(R.string.error_create_match));
				Toast.makeText(getActivity(),getActivity().getString(R.string.toast_missing_field),Toast.LENGTH_SHORT).show();
			}
		});

        return view;
    }

    public boolean isValidTitle(String title){

        if(title.isEmpty())
            return false;
        if(title.length() < 5){
            Toast.makeText(getActivity(),getActivity().getString(R.string.match_title_length_too_small),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean isValidLocation(String location){
        if(location.isEmpty())
            return false;
        if(location.length() < 5){
            Toast.makeText(getActivity(),getActivity().getString(R.string.match_location_length_too_small),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean isValidDate(String date){
        if(date.length() != 8)
            return false;

        Integer day = Integer.parseInt(date.substring(0,2));
        Integer month = Integer.parseInt(date.substring(3,5));
        Integer year = Integer.parseInt(date.substring(6,8));
        if(day == null || month == null || year == null)
            return false;
        try {
			LocalDate convertedDate = LocalDate.of(day, month, year);
			return convertedDate != null;
		}catch(DateTimeException e){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.match_invalid_date), Toast.LENGTH_LONG).show();
        	return false;
		}
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CreateMatchFragmentInteraction) {
            mListener = (CreateMatchFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CreateMatchFragmentInteraction");
        }
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
        return minPlayers >= (2*num);
    }

    public void createMatch()
    {
        String title = mMatchTitle.getText().toString();
        String location = mMatchLocation.getText().toString();
        String date = mMatchDate.getText().toString();
        String maxPlayers = mMaxPlayers.getText().toString();
        String minPlayers = mMinPlayers.getText().toString();
        Match match = new Match(title, location, date, mNumPlayers.getSelectedItem().toString(), minPlayers, maxPlayers);

        Intent intent = new Intent(getActivity(), FirebaseIntentService.class);
        intent.putExtra(getActivity().getString(R.string.bndl_match), match);
        intent.setAction(getActivity().getString(R.string.action_create_match));
        getActivity().startService(intent);
    }


    public interface CreateMatchFragmentInteraction
    {
        void onCreateMatchFragmentStateChanged();
        void onMatchCreated();
    }

}
