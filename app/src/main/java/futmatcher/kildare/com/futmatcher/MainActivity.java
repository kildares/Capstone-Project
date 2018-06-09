package futmatcher.kildare.com.futmatcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import futmatcher.kildare.com.futmatcher.model.Match;
import futmatcher.kildare.com.futmatcher.persistence.FutMatcherFirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Match match = new Match("Match Title","Location", "10/10/10", "10","10","20");

        FutMatcherFirebaseDatabase.addMatch(match);
    }
}
