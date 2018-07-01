package futmatcher.kildare.com.futmatcher.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import futmatcher.kildare.com.futmatcher.R;
import futmatcher.kildare.com.futmatcher.model.Match;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PickTeamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PickTeamFragment extends Fragment {

    private PickTeamFragmentInteraction mListener;
    Match mMatch;

    TextView mTextTeam1;
    TextView mTextTeam2;
    TextView mTextReserve1;
    TextView mTextReserve2;
    ListView mListTeam1;
    ListView mListTeam2;
    ListView mListReserve1;
    ListView mListReserve2;

    Boolean mTeamPicked;

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

        View view = inflater.inflate(R.layout.fragment_pick_team, container, false);

        mTextTeam1 = view.findViewById(R.id.tv_team1);
        mTextTeam2 = view.findViewById(R.id.tv_team2);
        mListTeam1 = view.findViewById(R.id.lv_reserve1);
        mListTeam2 = view.findViewById(R.id.lv_reserve2);
        mTextReserve1 = view.findViewById(R.id.tv_reserve1);
        mTextReserve2 = view.findViewById(R.id.tv_reserve2);
        mListReserve1 = view.findViewById(R.id.lv_reserve1);
        mListReserve2 = view.findViewById(R.id.lv_reserve2);

        return view;
    }

    public void emptyData()
    {
        mTeamPicked = false;
        mTextTeam1.setVisibility(View.INVISIBLE);
        mTextTeam2.setVisibility(View.INVISIBLE);
        mListTeam1.setVisibility(View.INVISIBLE);
        mListTeam2.setVisibility(View.INVISIBLE);
        mTextReserve1.setVisibility(View.INVISIBLE);
        mTextReserve2.setVisibility(View.INVISIBLE);
        mListReserve1.setVisibility(View.INVISIBLE);
        mListReserve2.setVisibility(View.INVISIBLE);

        FrameLayout frameLayout = new FrameLayout(getActivity());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
            .setTitle(getActivity().getString(R.string.text_pick_team_title))
            .setPositiveButton(getActivity().getString(R.string.alert_pick_positive), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            })
            .setNegativeButton(getActivity().getString(R.string.alert_add_negative), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            })
            .setView(frameLayout)
            .setCancelable(false);

        AlertDialog dialog = builder.create();

        LayoutInflater inflater = dialog.getLayoutInflater();
        inflater.inflate(R.layout.pick_team_button, frameLayout);
        dialog.show();

    }

    public void onButtonPressed() {
        if (mListener != null) {
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PickTeamFragmentInteraction) {
            mListener = (PickTeamFragmentInteraction) context;
            if(mMatch == null)
                mTeamPicked = false;
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
    }
}
