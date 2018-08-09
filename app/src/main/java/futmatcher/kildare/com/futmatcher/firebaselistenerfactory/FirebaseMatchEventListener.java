package futmatcher.kildare.com.futmatcher.firebaselistenerfactory;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import futmatcher.kildare.com.futmatcher.model.Match;
import futmatcher.kildare.com.futmatcher.recyclerview.MatchAdapter;

/**
 * Created by kilda on 8/8/2018.
 */

public class FirebaseMatchEventListener extends FirebaseEventListener{

	private MatchAdapter mMatchAdapter;
	private Match mMatch;

	@Override
	public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
		if(dataSnapshot.getValue() != null){
			mMatch = dataSnapshot.getValue(Match.class);
			mMatchAdapter.addMatch(mMatch);
		}
	}

	public FirebaseMatchEventListener(Object adapter, Match match){
		mMatchAdapter = (MatchAdapter) adapter;
		this.mMatch = match;
	}

	public FirebaseMatchEventListener(Object adapter){
		mMatchAdapter = (MatchAdapter) adapter;
		this.mMatch = null;
	}

	@Override
	public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
		Match match = dataSnapshot.getValue(Match.class);
		if(mMatchAdapter != null){
			mMatchAdapter.updateMatch(match);
		}

	}

	@Override
	public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
		Match match = dataSnapshot.getValue(Match.class);
		if(mMatchAdapter != null){
			mMatchAdapter.removeMatch(match);
		}
	}

	@Override
	public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

	}

	@Override
	public void onCancelled(@NonNull DatabaseError databaseError) {

	}

	@Override
	public void setAdapter(Object adapter) {
		mMatchAdapter = (MatchAdapter) adapter;
	}
}
