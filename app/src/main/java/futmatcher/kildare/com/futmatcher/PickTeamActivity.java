package futmatcher.kildare.com.futmatcher;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import futmatcher.kildare.com.futmatcher.model.Match;
import futmatcher.kildare.com.futmatcher.ui.PickTeamFragment;

public class PickTeamActivity extends AppCompatActivity implements PickTeamFragment.PickTeamFragmentInteraction {

	PickTeamFragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pick_team);

		Bundle bundle = getIntent().getExtras();

		mFragment = (PickTeamFragment) getSupportFragmentManager().findFragmentById(R.id.fr_pick_team);

		if(bundle != null){
			Match match = bundle.getParcelable(getString(R.string.bndl_match));
			mFragment.setMatch(match);
		}

	}

	@Override
	public void onPickTeamCancelled() {
		Toast.makeText(this,"Could not pick teams",Toast.LENGTH_LONG).show();
		onBackPressed();
	}

	@Override
	public void onPickTeamFragmentStateChanged(Fragment fragment) {

	}


}
