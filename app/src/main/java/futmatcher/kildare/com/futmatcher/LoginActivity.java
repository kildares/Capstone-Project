package futmatcher.kildare.com.futmatcher;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

	Button mLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mLogin = findViewById(R.id.bt_login);

		mLogin.setOnClickListener(this);
	}

	public static final int RC_SIGN_IN = 123;

	@Override
	public void onClick(View view) {
		if(view.getId() == mLogin.getId()){

			List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build());
			startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(), RC_SIGN_IN);

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode == RC_SIGN_IN){
			IdpResponse response = IdpResponse.fromResultIntent(data);

			if(resultCode == RESULT_OK){
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
			}
			else{
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				AlertDialog dialog = builder.setTitle("Sorry, we could not login. Please try again.\n").create();
				dialog.show();
			}
		}
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}
