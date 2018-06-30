package futmatcher.kildare.com.futmatcher;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import futmatcher.kildare.com.futmatcher.model.Match;
import futmatcher.kildare.com.futmatcher.recyclerview.MatchAdapter;
import futmatcher.kildare.com.futmatcher.ui.MatchDetailsFragment;

/**
 * Created by kilda on 6/30/2018.
 */

public class FirebaseChildEventListener implements ChildEventListener{

	private MatchAdapter mMatchAdapter;
	private ArrayAdapter<String> mPlayerNameAdapter;

	private static FirebaseChildEventListener mEventListener;

	private FirebaseChildEventListener()
	{

	}

	public static FirebaseChildEventListener getInstance()
	{
		if(mEventListener == null){
			mEventListener = new FirebaseChildEventListener();
		}
		return mEventListener;
	}

	public static void setMatchAdapter(MatchAdapter matchAdapter)
	{
		FirebaseChildEventListener eventListener = FirebaseChildEventListener.getInstance();
		eventListener.mMatchAdapter = matchAdapter;
	}


	public static void setPlayerAdapter(ArrayAdapter<String> arrayAdapter)
	{
		FirebaseChildEventListener eventListener = FirebaseChildEventListener.getInstance();
		eventListener.mPlayerNameAdapter = arrayAdapter;
	}


	@Override
	public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

		Match match = dataSnapshot.getValue(Match.class);

		if(mMatchAdapter != null){

			mMatchAdapter.addMatch(match);
		}

		if(mPlayerNameAdapter != null){
			mPlayerNameAdapter.clear();
			mPlayerNameAdapter.addAll(MatchDetailsFragment.getPlayersName(match));
			mPlayerNameAdapter.notifyDataSetChanged();
		}

	}

	@Override
	public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

		Match match = dataSnapshot.getValue(Match.class);
		if(mMatchAdapter != null){
			mMatchAdapter.updateMatch(match);
		}

		if(mPlayerNameAdapter != null){
			mPlayerNameAdapter.clear();
			mPlayerNameAdapter.addAll(MatchDetailsFragment.getPlayersName(match));
			mPlayerNameAdapter.notifyDataSetChanged();
		}

	}

	@Override
	public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

		Match match = dataSnapshot.getValue(Match.class);
		if(mMatchAdapter != null){
			mMatchAdapter.removeMatch(match);
		}

		if(mPlayerNameAdapter != null){
			mPlayerNameAdapter.clear();
			mPlayerNameAdapter.addAll(MatchDetailsFragment.getPlayersName(match));
			mPlayerNameAdapter.notifyDataSetChanged();
		}
		if(mPlayerNameAdapter != null){
			mPlayerNameAdapter.clear();
			mPlayerNameAdapter.addAll(MatchDetailsFragment.getPlayersName(match));
			mPlayerNameAdapter.notifyDataSetChanged();
		}

	}

	@Override
	public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
	}

	@Override
	public void onCancelled(@NonNull DatabaseError databaseError) {
	}

}
