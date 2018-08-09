package futmatcher.kildare.com.futmatcher.firebaselistenerfactory;

import com.google.firebase.database.ChildEventListener;

/**
 * Created by kilda on 8/8/2018.
 */

public abstract class FirebaseEventListener implements ChildEventListener {

	public abstract void setAdapter(Object adapter);

}
