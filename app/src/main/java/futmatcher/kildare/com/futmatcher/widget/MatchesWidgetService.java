package futmatcher.kildare.com.futmatcher.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by kilda on 8/13/2018.
 */

public class MatchesWidgetService extends RemoteViewsService{
	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {

		return new MatchesViewFactory(getApplicationContext(), intent);
	}
}
