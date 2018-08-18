package futmatcher.kildare.com.futmatcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import futmatcher.kildare.com.futmatcher.ui.CreateMatchFragment;

public class CreateMatchActivity extends AppCompatActivity implements CreateMatchFragment.CreateMatchFragmentInteraction{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match);


    }

    @Override
    public void onCreateMatchFragmentStateChanged() {

    }

    @Override
    public void onMatchCreated() {
        onBackPressed();
    }
}
