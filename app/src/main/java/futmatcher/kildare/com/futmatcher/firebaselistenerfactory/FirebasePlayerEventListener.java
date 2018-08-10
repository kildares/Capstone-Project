package futmatcher.kildare.com.futmatcher.firebaselistenerfactory;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.List;

import futmatcher.kildare.com.futmatcher.model.Match;
import futmatcher.kildare.com.futmatcher.model.Player;
import futmatcher.kildare.com.futmatcher.ui.MatchDetailsFragment;

/**
 * Created by kilda on 8/8/2018.
 */

public class FirebasePlayerEventListener extends  FirebaseEventListener{

	private Match mMatch;
	ArrayAdapter<String> mPlayerNameAdapter;


	private void reloadData(Match match)
	{
		if(mPlayerNameAdapter != null && mMatch != null && match.getTitle().equals(mMatch.getTitle())){
			mMatch = match;
			List<String> array = MatchDetailsFragment.getPlayersName(mMatch);
			mPlayerNameAdapter.clear();
			mPlayerNameAdapter.addAll(array);
			mPlayerNameAdapter.notifyDataSetChanged();
			System.out.println("Match adapter:" + Integer.toString(mPlayerNameAdapter.getCount()));
		}
	}


	@Override
	public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
		if(dataSnapshot.getValue() != null){
			Match match = dataSnapshot.getValue(Match.class);
			reloadData(match);
		}
	}

	public FirebasePlayerEventListener(Object adapter, Match match){
		mPlayerNameAdapter = (ArrayAdapter<String>) adapter;
		mMatch = match;
	}

	public FirebasePlayerEventListener(Object adapter){
		mPlayerNameAdapter = (ArrayAdapter<String>) adapter;
		mMatch = null;
	}

	@Override
	public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
		Match match = dataSnapshot.getValue(Match.class);
		reloadData(match);
	}

	@Override
	public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
		Match match = dataSnapshot.getValue(Match.class);
		reloadData(match);
	}

	@Override
	public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

	}

	@Override
	public void onCancelled(@NonNull DatabaseError databaseError) {

	}

	@Override
	public void setAdapter(Object adapter) {
		mPlayerNameAdapter = (ArrayAdapter<String>) adapter;
	}
}
