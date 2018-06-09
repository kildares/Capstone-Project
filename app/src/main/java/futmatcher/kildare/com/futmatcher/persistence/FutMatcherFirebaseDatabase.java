package futmatcher.kildare.com.futmatcher.persistence;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import futmatcher.kildare.com.futmatcher.model.Match;

/**
 * Created by kilda on 6/9/2018.
 */

public class FutMatcherFirebaseDatabase {

    private static FutMatcherFirebaseDatabase futMatcherFirebaseDatabase;

    private FirebaseDatabase Database;
    private DatabaseReference DatabaseReference;

    private static final String ROOT_REFERENCE = "matches/";

    private FutMatcherFirebaseDatabase()
    {
        Database = FirebaseDatabase.getInstance();
        DatabaseReference = Database.getReference(ROOT_REFERENCE);
        DatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
        DatabaseReference reference = Database.getReference(ROOT_REFERENCE + match.getTitle());
        reference.setValue(match);
    }




}
