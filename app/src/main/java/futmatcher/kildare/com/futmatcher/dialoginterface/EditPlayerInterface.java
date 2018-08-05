package futmatcher.kildare.com.futmatcher.dialoginterface;

import futmatcher.kildare.com.futmatcher.model.Player;

/**
 * Created by kilda on 8/5/2018.
 */

public interface EditPlayerInterface {

	void addPlayer(String Name, String Position);
	void editPlayer(Player player);
	void removePlayer(Player player);
}
