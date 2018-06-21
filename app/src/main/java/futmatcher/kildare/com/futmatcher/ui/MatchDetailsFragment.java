package futmatcher.kildare.com.futmatcher.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import futmatcher.kildare.com.futmatcher.R;
import futmatcher.kildare.com.futmatcher.model.Match;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MatchDetailsFragment.MatchDetailsFragmentInteraction} interface
 * to handle interaction events.
 * Use the {@link MatchDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchDetailsFragment extends Fragment {

    private MatchDetailsFragmentInteraction mListener;
    private Match mMatch;
    private ListView PlayersList;
    private TextView Location;
    private TextView Title;
    private TextView Date;
    private TextView PlayersListTitle;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_match_details, container, false);

        Location = view.findViewById(R.id.tv_detail_location);
        Title = view.findViewById(R.id.tv_detail_title);
        Date = view.findViewById(R.id.tv_detail_date);
        PlayersList = view.findViewById(R.id.lv_players);

        PlayersListTitle = view.findViewById(R.id.tv_detail_players);
        // Inflate the layout for this fragment
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
}
