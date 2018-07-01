package futmatcher.kildare.com.futmatcher;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import futmatcher.kildare.com.futmatcher.model.Match;
import futmatcher.kildare.com.futmatcher.ui.MatchDetailsFragment;
import futmatcher.kildare.com.futmatcher.ui.MatchListFragment;

public class MatchDetailActivity extends AppCompatActivity implements MatchDetailsFragment.MatchDetailsFragmentInteraction {

	MatchDetailsFragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_detail);

		Bundle bundle = getIntent().getExtras();
		Match match = bundle.getParcelable(getString(R.string.bndl_match));

		mFragment = (MatchDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fr_match_detail);

		mFragment.loadMatchData(match);
	}

	@Override
	public void onPickTeamButtonPressed() {
		Intent intent = new Intent(MatchDetailActivity.this, PickTeamActivity.class);
		startActivity(intent);
	}
}
