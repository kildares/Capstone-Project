package futmatcher.kildare.com.futmatcher.firebaselistenerfactory;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import futmatcher.kildare.com.futmatcher.model.Match;
import futmatcher.kildare.com.futmatcher.widget.MatchesViewFactory;

/**
 * Created by kilda on 8/13/2018.
 */

public class FirebaseWidgetEventListener extends FirebaseEventListener {

	private static String LOG_TAG = "LOG_WIDGET";

	List<String> mMatchesNames;
	MatchesViewFactory mAdapter;

	public FirebaseWidgetEventListener(Object context){
		mAdapter = (MatchesViewFactory) context;
	}

	@Override
	public void setAdapter(Object adapter) {
		mAdapter = (MatchesViewFactory) adapter;
	}

	@Override
	public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
		if(dataSnapshot.getValue(Match.class) != null){
			Match match = dataSnapshot.getValue(Match.class);
			addMatch(match.getTitle());
			mAdapter.updateWidget(mMatchesNames);
			Log.i(LOG_TAG, "added match: " + match.getTitle());
		}
	}

	@Override
	public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
		if(dataSnapshot.getValue(Match.class) != null){
			Match match = dataSnapshot.getValue(Match.class);
			mMatchesNames.remove(s);
			addMatch(match.getTitle());
			mAdapter.updateWidget(mMatchesNames);
			Log.i(LOG_TAG, "changed match: " + match.getTitle());
		}
	}

	@Override
	public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
		if(dataSnapshot.getValue(Match.class) != null){
			Match match = dataSnapshot.getValue(Match.class);
			mMatchesNames.remove(match.getTitle());
			mAdapter.updateWidget(mMatchesNames);
			Log.i(LOG_TAG, "removed match: " + match.getTitle());
		}
	}

	@Override
	public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

	}

	@Override
	public void onCancelled(@NonNull DatabaseError databaseError) {

	}

	private void addMatch(String title){
		if(mMatchesNames == null)
			mMatchesNames = new ArrayList<>();
		mMatchesNames.add(title);
	}

}
