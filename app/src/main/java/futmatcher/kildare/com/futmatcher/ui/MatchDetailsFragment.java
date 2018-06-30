package futmatcher.kildare.com.futmatcher.ui;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import futmatcher.kildare.com.futmatcher.FirebaseChildEventListener;
import futmatcher.kildare.com.futmatcher.R;
import futmatcher.kildare.com.futmatcher.model.Match;
import futmatcher.kildare.com.futmatcher.model.Player;
import futmatcher.kildare.com.futmatcher.persistence.FutMatcherFirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MatchDetailsFragment.MatchDetailsFragmentInteraction} interface
 * to handle interaction events.
 * Use the {@link MatchDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchDetailsFragment extends Fragment implements View.OnClickListener{

    private MatchDetailsFragmentInteraction mListener;
    private Match mMatch;
    private ListView PlayersList;
    private TextView Location;
    private TextView Title;
    private TextView Date;
    private TextView PlayersListTitle;
    private Button mAddPlayer;
    private Button mPickTeam;
    private ArrayAdapter<String> mPlayerListAdapter;

    public MatchDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MatchDetailsFragment.
     */
    public static MatchDetailsFragment newInstance() {
        MatchDetailsFragment fragment = new MatchDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_match_details, container, false);

        Location = view.findViewById(R.id.tv_detail_location);
        Title = view.findViewById(R.id.tv_detail_title);
        Date = view.findViewById(R.id.tv_detail_date);
        PlayersList = view.findViewById(R.id.lv_players);
        mPickTeam = view.findViewById(R.id.bt_pick);

        mPickTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mAddPlayer = view.findViewById(R.id.bt_add_player);

        mAddPlayer.setOnClickListener(this);

        PlayersListTitle = view.findViewById(R.id.tv_detail_players);

        PlayersList = view.findViewById(R.id.lv_players);

        loadPlayersList();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MatchDetailsFragmentInteraction) {
            mListener = (MatchDetailsFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MatchDetailsFragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void loadMatchData(Match match)
    {
        mMatch = match;

        Title.setText(mMatch.getTitle());
        Location.setText(mMatch.getLocation());
        Date.setText(mMatch.getDate());
        PlayersListTitle.setText(getActivity().getString(R.string.text_title_detail_player));

        if(!(mMatch.getPlayers() == null || mMatch.getPlayers().size() == 0)){
            loadPlayersList();
        }
    }

    public void loadPlayersList()
    {
        if(PlayersList != null && mMatch != null){
            mPlayerListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, getPlayersName(mMatch));
            PlayersList.setAdapter(mPlayerListAdapter);
            FirebaseChildEventListener.setPlayerAdapter(mPlayerListAdapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPlayersList();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == mAddPlayer.getId())
        {
            PlayerAlertDialogListener listener = new PlayerAlertDialogListener();
            final FrameLayout frameView = new FrameLayout(getActivity());

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setTitle(getActivity().getString(R.string.alert_add_title))
                    .setView(frameView)
                    .setPositiveButton(getString(R.string.alert_add_positive), listener )
                    .setNegativeButton(getString(R.string.alert_add_negative), listener);
            final AlertDialog alertDialog = builder.create();

            LayoutInflater inflater = alertDialog.getLayoutInflater();

            View dialoglayout = inflater.inflate(R.layout.add_player_alert_dialog, frameView);
            listener.setAddPlayerName((EditText) dialoglayout.findViewById(R.id.et_add_player_name));
            listener.setPlayerPosition((Spinner) dialoglayout.findViewById(R.id.sp_add_player_position));
            alertDialog.show();

        }
    }

    public static List<String> getPlayersName(Match match)
    {
        if(match != null){
            List<Player> players = match.getPlayers();
            if(players == null)
                return new ArrayList<>();
            List<String> playerNames = new ArrayList<>();
            for(Player p : players){
                playerNames.add(p.getName());
            }
            return playerNames;
        }
        return new ArrayList<>();
    }


    public void addPlayerToMatch(String Name, String Position)
    {
        Player player = new Player(Name, Position);
        mMatch.addPlayer(player);
        FutMatcherFirebaseDatabase.getInstance().updateMatch(mMatch);

    }


/**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface MatchDetailsFragmentInteraction{
    }

    class PlayerAlertDialogListener implements DialogInterface.OnClickListener{

        private EditText mAddPlayerName;
        private Spinner mPlayerPosition;

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            switch(i){
                case DialogInterface.BUTTON_POSITIVE:{
                    if (mAddPlayerName != null && mPlayerPosition != null && !mAddPlayerName.getText().toString().isEmpty()) {
                        String name = mAddPlayerName.getText().toString();
                        String position = mPlayerPosition.getSelectedItem().toString();
                        addPlayerToMatch(name, position);
                    }
                    break;
                }
                case DialogInterface.BUTTON_NEGATIVE:{

                    dialogInterface.dismiss();
                    break;
                }

            }

        }
        public void setAddPlayerName(EditText playerName){
            mAddPlayerName = playerName;
        }

        public void setPlayerPosition(Spinner playerPosition){
            mPlayerPosition = playerPosition;
        }

    }

}
