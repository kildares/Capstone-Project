package futmatcher.kildare.com.futmatcher.persistence;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import futmatcher.kildare.com.futmatcher.model.Match;

/**
 * Created by kilda on 6/9/2018.
 */

public class FutMatcherFirebaseDatabase implements ChildEventListener {

    private static FutMatcherFirebaseDatabase futMatcherFirebaseDatabase;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;

    private static final String ROOT_REFERENCE = "matches";

    private FutMatcherFirebaseDatabase()
    {
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference(ROOT_REFERENCE);

        mDatabaseReference.addChildEventListener(this);

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

     public void addMatch(Match match)
    {
        mDatabaseReference.child(match.getTitle()).setValue(match);
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        //Match match = dataSnapshot.getValue();
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
