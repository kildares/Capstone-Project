package futmatcher.kildare.com.futmatcher.dialoginterface;

import android.content.DialogInterface;
import android.widget.EditText;

import futmatcher.kildare.com.futmatcher.dialoginterface.EditPlayerInterface;
import futmatcher.kildare.com.futmatcher.model.Player;

/**
 * Created by kilda on 8/5/2018.
 */

public class EditPlayerOnClickListener implements DialogInterface.OnClickListener {

	private Player mPlayer;
	EditPlayerInterface mListener;
	EditText mPlayerNameView;

	public EditPlayerOnClickListener(EditPlayerInterface listener, Player player){
		mPlayer = player;
		mListener = listener;
	}

	@Override
	public void onClick(DialogInterface dialogInterface, int i) {
		switch(i){
			case DialogInterface.BUTTON_POSITIVE:{
				mPlayer.setName(mPlayerNameView.getText().toString());
				mListener.editPlayer(mPlayer);
				dialogInterface.dismiss();
				break;
			}
			case DialogInterface.BUTTON_NEGATIVE:{
				mListener.removePlayer(mPlayer);
				dialogInterface.dismiss();
				break;
			}

		}
	}

	public void setPlayerNameView(EditText view){
		mPlayerNameView = view;
	}

}
