package futmatcher.kildare.com.futmatcher;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import futmatcher.kildare.com.futmatcher.model.Match;
import futmatcher.kildare.com.futmatcher.ui.CreateMatchFragment;
import futmatcher.kildare.com.futmatcher.ui.MatchDetailsFragment;
import futmatcher.kildare.com.futmatcher.ui.MatchListFragment;
import futmatcher.kildare.com.futmatcher.ui.PickTeamFragment;

public class MainActivity extends AppCompatActivity implements MatchListFragment.MatchListFragmentInteraction, MatchDetailsFragment.MatchDetailsFragmentInteraction, PickTeamFragment.PickTeamFragmentInteraction {

    private boolean mTwoPane;
    MatchListFragment mMatchListFragment;
    FrameLayout mFragmentView;
    Fragment mSecondFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentView = findViewById(R.id.fl_main_fragments);
        mMatchListFragment = (MatchListFragment) getSupportFragmentManager().findFragmentById(R.id.fr_match_list);
        mTwoPane = (mFragmentView != null);
    }


    @Override
    public void onCreateMatchButtonPressed() {
        if(mTwoPane){
            Fragment createMatchFragment = CreateMatchFragment.newInstance();
            updateSecondFragment(createMatchFragment);
        }
        else{
            Intent intent = new Intent(MainActivity.this,CreateMatchActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClickMatchItem(Match match) {
        if(mTwoPane){
            MatchDetailsFragment matchDetailsFragment = MatchDetailsFragment.newInstance();
            matchDetailsFragment.setMatch(match);
            updateSecondFragment(matchDetailsFragment);
        }
        else{
            Intent intent = new Intent(MainActivity.this,MatchDetailActivity.class);
            intent.putExtra(getString(R.string.bndl_match),match);
            startActivity(intent);
        }
    }


    @Override
    public void onPickTeamButtonPressed(Match match) {
        if(mTwoPane){
            PickTeamFragment pickTeamFragment = PickTeamFragment.newInstance();
            pickTeamFragment.setMatch(match);
            updateSecondFragment(pickTeamFragment);
        }
    }

    @Override
    public void onMatchDetailsFragmentStateChanged(Fragment fragment) {
        mSecondFragment = fragment;
    }

    private void updateSecondFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(mSecondFragment != null)
            transaction = transaction.remove(mSecondFragment);
        transaction.add(R.id.fl_main_fragments, fragment).commit();
        mSecondFragment = fragment;
    }

    @Override
    public void onPickTeamCancelled() {
        Toast.makeText(this,getString(R.string.pick_cancelled),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPickTeamFragmentStateChanged(Fragment fragment){
        mSecondFragment = fragment;
    }

}
