package futmatcher.kildare.com.futmatcher.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import futmatcher.kildare.com.futmatcher.R;
import futmatcher.kildare.com.futmatcher.model.Match;
import futmatcher.kildare.com.futmatcher.model.Player;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PickTeamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PickTeamFragment extends Fragment {

    private PickTeamFragmentInteraction mListener;
    private Match mMatch;

    private TextView mTextTeam1;
    private TextView mTextTeam2;
    private TextView mTextReserve1;
    private TextView mTextReserve2;
    private ListView mListTeam1;
    private ListView mListTeam2;
    private ListView mListReserve1;
    private ListView mListReserve2;
    private Button mPickButton;
    private int mTeamPicked;

    private Button mPickTeam;
    private RadioGroup mRadioGroup;

    private static final int TEAM_NOT_PICKED_CODE = 0;
    private static final int TEAM_PICKED_RANDOM = 1;
    private static final int TEAM_PICKED_POSITION = 2;


    public PickTeamFragment() {
        // Required empty public constructor
    }

    public void setMatch(Match match){ mMatch = match;}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PickTeamFragment.
     */
    public static PickTeamFragment newInstance() {
        PickTeamFragment fragment = new PickTeamFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(savedInstanceState != null){
            mTeamPicked = savedInstanceState.getInt(getActivity().getString(R.string.key_picked_team));
            mMatch = savedInstanceState.getParcelable(getActivity().getString(R.string.key_id_match));
            mListener.onPickTeamFragmentStateChanged(this);
        }

        View view = inflater.inflate(R.layout.fragment_pick_team, container, false);

        mTextTeam1 = view.findViewById(R.id.tv_team1);
        mTextTeam2 = view.findViewById(R.id.tv_team2);
        mListTeam1 = view.findViewById(R.id.lv_team1);
        mListTeam2 = view.findViewById(R.id.lv_team2);
        mTextReserve1 = view.findViewById(R.id.tv_reserve1);
        mTextReserve2 = view.findViewById(R.id.tv_reserve2);
        mListReserve1 = view.findViewById(R.id.lv_reserve1);
        mListReserve2 = view.findViewById(R.id.lv_reserve2);
        mPickButton = view.findViewById(R.id.bt_pick_team);
        mRadioGroup = view.findViewById(R.id.in_pick_team);

        mPickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTeam();
            }
        });

        if(mTeamPicked == TEAM_NOT_PICKED_CODE)
            emptyData();
        else
            updateTeamViews();

        return view;
    }

    public void pickTeam() {

        int selectedId = mRadioGroup.getCheckedRadioButtonId();
        RadioButton by_position = getActivity().findViewById(R.id.rb_position);
        try{
            if(selectedId == by_position.getId()){
                try{
                    mMatch.pickTeamsByPosition();
                    mTeamPicked = TEAM_PICKED_POSITION;
                }catch(RuntimeException e){
                    String message = getActivity().getString(R.string.error_pick_team_unavailable);
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    Log.w("Pick Team", message);
                }
            }
            else{
                mMatch.pickTeamsRandomly();
                mTeamPicked = TEAM_PICKED_RANDOM;
            }
            updateTeamViews();
        }catch(RuntimeException e){
            e.printStackTrace();
            if(mTeamPicked == TEAM_NOT_PICKED_CODE)
                mListener.onPickTeamCancelled();
            else
                Toast.makeText(getActivity(), getActivity().getString(R.string.toast_not_enough_players), Toast.LENGTH_LONG).show();
        }
    }


    public void emptyData()
    {
        mTeamPicked = TEAM_NOT_PICKED_CODE;
        mTextTeam1.setVisibility(View.INVISIBLE);
        mTextTeam2.setVisibility(View.INVISIBLE);
        mListTeam1.setVisibility(View.INVISIBLE);
        mListTeam2.setVisibility(View.INVISIBLE);
        mTextReserve1.setVisibility(View.INVISIBLE);
        mTextReserve2.setVisibility(View.INVISIBLE);
        mListReserve1.setVisibility(View.INVISIBLE);
        mListReserve2.setVisibility(View.INVISIBLE);
        mPickButton.setVisibility(View.INVISIBLE);
        mRadioGroup.setVisibility(View.INVISIBLE);

        FrameLayout frameLayout = new FrameLayout(getActivity());
        PickTeamOnClickListener listener = new PickTeamOnClickListener();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
            .setTitle(getActivity().getString(R.string.text_pick_team_title))
            .setPositiveButton(getActivity().getString(R.string.alert_pick_positive), listener)
            .setNegativeButton(getActivity().getString(R.string.alert_add_negative), listener)
            .setView(frameLayout)
            .setCancelable(false);

        AlertDialog dialog = builder.create();
        LayoutInflater inflater = dialog.getLayoutInflater();
        View view = inflater.inflate(R.layout.pick_team_radio, frameLayout);
        //listener
        listener.setView(view);
        dialog.show();

    }

    public void showData(){
        mTextTeam1.setVisibility(View.VISIBLE);
        mTextTeam2.setVisibility(View.VISIBLE);
        mListTeam1.setVisibility(View.VISIBLE);
        mListTeam2.setVisibility(View.VISIBLE);
        mTextReserve1.setVisibility(View.VISIBLE);
        mTextReserve2.setVisibility(View.VISIBLE);
        mListReserve1.setVisibility(View.VISIBLE);
        mListReserve2.setVisibility(View.VISIBLE);
        mPickButton.setVisibility(View.VISIBLE);

        //mRadioGroup = getActivity().findViewById(R.id.in_pick_team);
        //mRadioGroup.setVisibility(View.VISIBLE);
    }

    public void onButtonPressed() {
        if (mListener != null) {
        }
    }

    public void updateTeamViews()
    {

        ArrayAdapter<String> arrayTeam1 = loadListViewPlayers(mMatch.getTeam1().getTeamPlayers());
        mListTeam1.setAdapter(arrayTeam1);
        arrayTeam1.notifyDataSetChanged();

        ArrayAdapter<String> arrayTeam2 = loadListViewPlayers(mMatch.getTeam2().getTeamPlayers());
        mListTeam2.setAdapter(arrayTeam2);
        arrayTeam2.notifyDataSetChanged();

        ArrayAdapter<String> arrayRes1 = loadListViewPlayers(mMatch.getTeam1().getTeamSubstitutes());
        mListReserve1.setAdapter(arrayRes1);
        arrayRes1.notifyDataSetChanged();

        ArrayAdapter<String> arrayRes2 = loadListViewPlayers(mMatch.getTeam2().getTeamSubstitutes());
        mListReserve2.setAdapter(arrayRes2);
        arrayRes2.notifyDataSetChanged();

        showData();
    }

    public ArrayAdapter<String> loadListViewPlayers(List<Player> players)
    {
        if(players != null){
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.pick_team_item, Player.getPlayerNameList(players));
            return adapter;
        }
        return new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<String>());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PickTeamFragmentInteraction) {
            mListener = (PickTeamFragmentInteraction) context;
            mTeamPicked = TEAM_NOT_PICKED_CODE;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(getActivity().getString(R.string.key_picked_team), mTeamPicked);
        outState.putParcelable(getActivity().getString(R.string.key_id_match), mMatch);
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
    public interface PickTeamFragmentInteraction {
        void onPickTeamCancelled();
        void onPickTeamFragmentStateChanged(Fragment fragment);
    }

    private class PickTeamOnClickListener implements DialogInterface.OnClickListener{

        private View mView;
        PickTeamOnClickListener(){
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            mRadioGroup.setVisibility(View.VISIBLE); //TODO acertar isso depois
            mRadioGroup = mView.findViewById(R.id.rg_pick_order);
            switch(i){
                case DialogInterface.BUTTON_NEGATIVE:{
                    mListener.onPickTeamCancelled();
                    break;
                }
                case DialogInterface.BUTTON_POSITIVE:{
                    pickTeam();
                    break;
                }
            }
        }

        public void setView(View view){
            this.mView = view;
        }

        public void setRadioGroup(RadioGroup radioGroup){
            mRadioGroup = radioGroup;
        }
    }
}
