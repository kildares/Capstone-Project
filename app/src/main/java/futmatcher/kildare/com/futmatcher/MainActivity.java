package futmatcher.kildare.com.futmatcher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import futmatcher.kildare.com.futmatcher.ui.MatchListFragment;

public class MainActivity extends AppCompatActivity implements MatchListFragment.CreateMatchButton {

    private boolean mTwoPane;
    MatchListFragment mMatchListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTwoPane = false;
        mMatchListFragment = (MatchListFragment) getSupportFragmentManager().findFragmentById(R.id.fr_match_list);
        mMatchListFragment.addCreateMatchButtonListener(this);
    }


    @Override
    public void onCreateMatchButtonPressed() {
        if(mTwoPane){
            loadDetail();
        }
        else{
            Intent intent = new Intent(MainActivity.this,CreateMatchActivity.class);
            startActivity(intent);
        }
    }


    private void loadDetail()
    {

    }

}
