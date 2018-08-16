package futmatcher.kildare.com.futmatcher.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import futmatcher.kildare.com.futmatcher.firebaselistenerfactory.FirebaseChildEventFactory;
import futmatcher.kildare.com.futmatcher.persistence.FutMatcherFirebaseDatabase;

/**
 * Created by kilda on 8/13/2018.
 */

public class MatchesWidgetService extends RemoteViewsService{
	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {

		return new MatchesViewFactory(getApplicationContext(), intent);
	}
}
