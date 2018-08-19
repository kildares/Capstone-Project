package futmatcher.kildare.com.futmatcher.persistence;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import futmatcher.kildare.com.futmatcher.model.Match;

/**
 * Created by kilda on 6/9/2018.
 */

public class FutMatcherFirebaseDatabase {

    private static FutMatcherFirebaseDatabase futMatcherFirebaseDatabase;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private static final String ROOT_REFERENCE = "matches";

    private FutMatcherFirebaseDatabase(){
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference(ROOT_REFERENCE);

    }

    public void addChildEventListenerToReference(ChildEventListener listener){
        mDatabaseReference.addChildEventListener(listener);
    }

    public static FutMatcherFirebaseDatabase getInstance()
    {
        if(futMatcherFirebaseDatabase == null){
            futMatcherFirebaseDatabase = new FutMatcherFirebaseDatabase();
            return futMatcherFirebaseDatabase;
        }
        else
            return futMatcherFirebaseDatabase;
    }

    public void addMatch(Match match){
        mDatabaseReference.child(match.getTitle()).setValue(match);
    }

    public void updateMatch(Match match){
        mDatabaseReference.child(match.getTitle()).setValue(match);
    }

    public void removeMatch(Match match){
        mDatabaseReference.child(match.getTitle()).removeValue();
    }

}
