package futmatcher.kildare.com.futmatcher.firebaselistenerfactory;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import futmatcher.kildare.com.futmatcher.model.Match;
import futmatcher.kildare.com.futmatcher.model.Player;
import futmatcher.kildare.com.futmatcher.ui.MatchDetailsFragment;

/**
 * Created by kilda on 8/8/2018.
 */

public class FirebasePlayerEventListener extends  FirebaseEventListener{

	private Match mMatch;
	ArrayAdapter<String> mPlayerNameAdapter;
	@Override
	public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
		if(dataSnapshot.getValue() != null){
			mMatch= dataSnapshot.getValue(Match.class);
			if(mPlayerNameAdapter != null){
				Player player = dataSnapshot.getValue(Player.class);
				mPlayerNameAdapter.add(player.getName());
				mPlayerNameAdapter.notifyDataSetChanged();
				System.out.println("Match adapter:" + Integer.toString(mPlayerNameAdapter.getCount()));
			}
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

		if(mPlayerNameAdapter != null){
			mPlayerNameAdapter.clear();
			mPlayerNameAdapter.addAll(MatchDetailsFragment.getPlayersName(mMatch));
			mPlayerNameAdapter.notifyDataSetChanged();
		}

	}

	@Override
	public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
		if(mPlayerNameAdapter != null){
			mPlayerNameAdapter.clear();
			mPlayerNameAdapter.addAll(MatchDetailsFragment.getPlayersName(mMatch));
			mPlayerNameAdapter.notifyDataSetChanged();
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
		mPlayerNameAdapter = (ArrayAdapter<String>) adapter;
	}
}
