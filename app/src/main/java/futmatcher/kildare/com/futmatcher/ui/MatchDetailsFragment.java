package futmatcher.kildare.com.futmatcher.ui;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import futmatcher.kildare.com.futmatcher.dialoginterface.EditPlayerInterface;
import futmatcher.kildare.com.futmatcher.dialoginterface.EditPlayerOnClickListener;
import futmatcher.kildare.com.futmatcher.firebaselistenerfactory.FirebaseChildEventFactory;
import futmatcher.kildare.com.futmatcher.R;
import futmatcher.kildare.com.futmatcher.firebaselistenerfactory.FirebaseEventListener;
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
public class MatchDetailsFragment extends Fragment implements View.OnClickListener, EditPlayerInterface{

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

    private static final String LOG_TAG = "MatchDetailsFragment";

    private static final String KEY_MATCH = "key_match";

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

        if(savedInstanceState != null){
            mMatch = savedInstanceState.getParcelable(KEY_MATCH);
            mListener.onMatchDetailsFragmentStateChanged(this);
        }

        View view = inflater.inflate(R.layout.fragment_match_details, container, false);

        Location = view.findViewById(R.id.tv_detail_location);
        Title = view.findViewById(R.id.tv_detail_title);
        Date = view.findViewById(R.id.tv_detail_date);
        PlayersList = view.findViewById(R.id.lv_players);
        mPickTeam = view.findViewById(R.id.bt_pick);

        PlayersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Player player = mMatch.getPlayers().get(position);

                if(player == null)
                    throw new RuntimeException();

                Log.i(LOG_TAG,"Player selected: " + mMatch.getPlayers().get(position).getName());

                FrameLayout frameLayout = new FrameLayout(getActivity());
                EditPlayerOnClickListener listener = new EditPlayerOnClickListener(MatchDetailsFragment.this, player);

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                builder
                        .setTitle(getActivity().getString(R.string.alert_edit_player))
                        .setPositiveButton(getActivity().getString(R.string.alert_positive_button), listener)
                        .setNegativeButton(getActivity().getString(R.string.alert_negative_button), listener)
                        .setView(frameLayout)
                        .setCancelable(true);

                android.app.AlertDialog dialog = builder.create();
                LayoutInflater inflater = dialog.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.add_player_alert_dialog, frameLayout);
                EditText playerName = dialogView.findViewById(R.id.et_add_player_name);
                playerName.setText(mPlayerListAdapter.getItem(position));
                listener.setPlayerNameView(playerName);
                dialog.show();

            }
        });

        mPickTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int numPlayers = mPlayerListAdapter.getCount();
                int minPlayers = Integer.parseInt(mMatch.getMinPlayers());
                if(numPlayers == 0){
                    Toast.makeText(getActivity(), getActivity().getString(R.string.toast_no_players),Toast.LENGTH_LONG ).show();
                }
                else if(numPlayers < minPlayers){
                    Toast.makeText(getActivity(), getActivity().getString(R.string.toast_not_enough_players),Toast.LENGTH_LONG ).show();
                }
                else{
                    mListener.onPickTeamButtonPressed(mMatch);
                }
            }
        });

        mAddPlayer = view.findViewById(R.id.bt_add_player);
        mAddPlayer.setOnClickListener(this);
        PlayersListTitle = view.findViewById(R.id.tv_detail_players);
        PlayersList = view.findViewById(R.id.lv_players);

        if(mMatch != null){
            reloadMatchData(mMatch);
        }

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

    public void reloadMatchData(Match match)
    {
        mMatch = match;
        Title.setText(mMatch.getTitle());
        Location.setText(mMatch.getLocation());
        Date.setText(mMatch.getDate());
        PlayersListTitle.setText(getActivity().getString(R.string.text_title_detail_player));
        loadPlayersList();
    }

    public Match getMatch() {
        return mMatch;
    }

    public void setMatch(Match mMatch) {
        this.mMatch = mMatch;
    }

    public void loadPlayersList()
    {
        if(PlayersList != null && mMatch != null){
            if(mPlayerListAdapter == null){
                mPlayerListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, getPlayersName(mMatch));
                PlayersList.setAdapter(mPlayerListAdapter);
                FirebaseEventListener listener = FirebaseChildEventFactory.getListener(
                                        FirebaseChildEventFactory.ListenerType.PLAYER,
                                        mPlayerListAdapter,
                                        mMatch);
                //TODO fix error
                FutMatcherFirebaseDatabase.getInstance().addChildEventListenerToReference(listener);
            }
            else{
                mPlayerListAdapter.clear();
                mPlayerListAdapter.addAll(getPlayersName(mMatch));
                mPlayerListAdapter.notifyDataSetChanged();
            }

            Log.d("LOG","configuring new adapter:" + Integer.toString(mPlayerListAdapter.getCount()));
        }
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

    @Override
    public void addPlayer(String Name, String Position)
    {
        Player player = new Player(Name, Position);
        mMatch.addPlayerToMatch(player);
        FutMatcherFirebaseDatabase.getInstance().updateMatch(mMatch);
    }

    @Override
    public void editPlayer(Player player) {
        FutMatcherFirebaseDatabase.getInstance().updateMatch(mMatch);
    }

    @Override
    public void removePlayer(Player player) {
        mMatch.removePlayerFromMatch(player);
        FutMatcherFirebaseDatabase
                .getInstance().updateMatch(mMatch);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(KEY_MATCH, mMatch);
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
        void onPickTeamButtonPressed(Match match);
        void onMatchDetailsFragmentStateChanged(Fragment fragment);
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
                        addPlayer(name, position);
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
