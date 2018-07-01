package futmatcher.kildare.com.futmatcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import futmatcher.kildare.com.futmatcher.ui.PickTeamFragment;

public class PickTeamActivity extends AppCompatActivity implements PickTeamFragment.PickTeamFragmentInteraction {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pick_team);

		Bundle bundle = getIntent().getExtras();

		if(bundle != null){

		}

	}
}
