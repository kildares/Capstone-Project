package futmatcher.kildare.com.futmatcher.persistence;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import futmatcher.kildare.com.futmatcher.R;
import futmatcher.kildare.com.futmatcher.model.Match;

public class FirebaseIntentService extends IntentService{

	public FirebaseIntentService()
	{
		super("FirebaseIntentService");
	}

	/**
	 * Creates an IntentService.  Invoked by your subclass's constructor.
	 *
	 * @param name Used to name the worker thread, important only for debugging.
	 */
	public FirebaseIntentService(String name) {
		super(name);
	}

	@Override
	protected void onHandleIntent(@Nullable Intent intent) {
		String action = intent.getAction();
		Bundle extras = intent.getExtras();

		Match match = extras.getParcelable(getString(R.string.bndl_match));
		if(action.equals(getString(R.string.action_create_match))){
			createMatch(match);
		}
		else if(action.equals(getString(R.string.action_add_player)) || action.equals(getString(R.string.action_edit_player))
				|| action.equals(getString(R.string.action_remove_player))){
			FutMatcherFirebaseDatabase.getInstance().updateMatch(match);
		}
	}

	public void createMatch(Match match)
	{
		FutMatcherFirebaseDatabase firebaseDatabase = FutMatcherFirebaseDatabase.getInstance();
		firebaseDatabase.addMatch(match);
		Log.i(getString(R.string.log_tag_firebase_service),getString(R.string.creating_match));
	}
}
