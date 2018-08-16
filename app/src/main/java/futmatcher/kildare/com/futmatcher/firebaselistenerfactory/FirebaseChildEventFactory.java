package futmatcher.kildare.com.futmatcher.firebaselistenerfactory;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;

import java.util.Objects;

import futmatcher.kildare.com.futmatcher.model.Match;
import futmatcher.kildare.com.futmatcher.ui.MatchDetailsFragment;

/**
 * Created by kilda on 6/30/2018.
 */

public class FirebaseChildEventFactory {

	private static FirebaseChildEventFactory mEventListener;

	public enum ListenerType{
		MATCH, PLAYER, WIDGET;
	}

	private FirebaseChildEventFactory()
	{

	}

	public static FirebaseEventListener getListener(ListenerType listener, Object adapter, Match match)
	{
		if(Objects.equals(listener, ListenerType.MATCH)){
			return new FirebaseMatchEventListener(adapter, match);
		}
		else if(Objects.equals(listener, ListenerType.PLAYER)){
			return new FirebasePlayerEventListener(adapter, match);
		}
		else if(Objects.equals(listener, ListenerType.WIDGET)){
			return new FirebaseWidgetEventListener(adapter);
		}
		else
			throw new UnsupportedOperationException("No listener type found");
	}

	public static FirebaseEventListener getListener(ListenerType listener, Object adapter)
	{
		if(Objects.equals(listener, ListenerType.MATCH)){
			return new FirebaseMatchEventListener(adapter);
		}
		else if(Objects.equals(listener, ListenerType.PLAYER)){
			return new FirebasePlayerEventListener(adapter);
		}else if(Objects.equals(listener, ListenerType.WIDGET)){
			return new FirebaseWidgetEventListener(adapter);
		}
		else
			throw new UnsupportedOperationException("No listener type found");
	}

	public static FirebaseEventListener getFirebaseEventListener(ListenerType listener, Object adapter, Match match)
	{
		if(Objects.equals(listener, ListenerType.MATCH)){
			return new FirebaseMatchEventListener(adapter, match);
		}
		else if(Objects.equals(listener, ListenerType.PLAYER)){
			return new FirebasePlayerEventListener(adapter, match);
		}
		else
			throw new UnsupportedOperationException("No listener type found");
	}

}
