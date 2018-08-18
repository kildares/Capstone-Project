package futmatcher.kildare.com.futmatcher.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import futmatcher.kildare.com.futmatcher.LoginActivity;
import futmatcher.kildare.com.futmatcher.MainActivity;

public class FirebaseLogout {

	public static void logout(Context context)
	{
		AuthUI.getInstance().signOut(context).addOnCompleteListener(new OnCompleteListener<Void>() {
			@Override
			public void onComplete(@NonNull Task<Void> task) {
				Toast.makeText(context, "Signed out successfully", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(context, LoginActivity.class);
				context.startActivity(intent);
			}
		});
	}

}
