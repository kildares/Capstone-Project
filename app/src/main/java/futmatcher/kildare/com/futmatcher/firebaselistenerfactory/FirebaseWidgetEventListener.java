package futmatcher.kildare.com.futmatcher.firebaselistenerfactory;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;


import futmatcher.kildare.com.futmatcher.R;
import futmatcher.kildare.com.futmatcher.model.Match;
import futmatcher.kildare.com.futmatcher.widget.MatchesAppWidget;
import futmatcher.kildare.com.futmatcher.widget.MatchesViewFactory;

/**
 * Created by kilda on 8/13/2018.
 */

public class FirebaseWidgetEventListener extends FirebaseEventListener {

	MatchesViewFactory mAdapter;

	public FirebaseWidgetEventListener(Object adapter){
		mAdapter = (MatchesViewFactory) adapter;
	}

	@Override
	public void setAdapter(Object adapter) {
	}

	@Override
	public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
		if(dataSnapshot.getValue(Match.class) != null){
			Match match = dataSnapshot.getValue(Match.class);
			mAdapter.addMatch(match.getTitle());
		}
	}

	@Override
	public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
		if(dataSnapshot.getValue(Match.class) != null){
			Match match = dataSnapshot.getValue(Match.class);
			mAdapter.replaceMatch(s, match.getTitle());
		}
	}

	@Override
	public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
		if(dataSnapshot.getValue(Match.class) != null){
			Match match = dataSnapshot.getValue(Match.class);
			mAdapter.removeMatch(match.getTitle());
		}
	}

	@Override
	public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

	}

	@Override
	public void onCancelled(@NonNull DatabaseError databaseError) {

	}

}
