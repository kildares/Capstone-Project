package futmatcher.kildare.com.futmatcher;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

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
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(mSecondFragment != null)
                transaction = transaction.remove(mSecondFragment);
            Fragment createMatchFragment = CreateMatchFragment.newInstance();
            transaction.add(R.id.fl_main_fragments, createMatchFragment).commit();
            mSecondFragment = createMatchFragment;
        }
        else{
            Intent intent = new Intent(MainActivity.this,CreateMatchActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClickMatchItem(Match match) {
        if(mTwoPane){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(mSecondFragment != null)
                transaction = transaction.remove(mSecondFragment);
            MatchDetailsFragment matchDetailsFragment = MatchDetailsFragment.newInstance();
            matchDetailsFragment.setMatch(match);
            transaction.add(R.id.fl_main_fragments, matchDetailsFragment).commit();
            mSecondFragment = matchDetailsFragment;
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
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(mSecondFragment != null)
                transaction = transaction.remove(mSecondFragment);
            PickTeamFragment pickTeamFragment = PickTeamFragment.newInstance();
            //TODO fazer o alertdialog aparecer quando o fragment de pickteam surgir
            transaction.add(R.id.fl_main_fragments, pickTeamFragment).commit();
            mSecondFragment = pickTeamFragment;
        }
    }

    @Override
    public void onPickTeamCancelled() {

    }
}
