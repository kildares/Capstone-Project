package futmatcher.kildare.com.futmatcher;

import java.util.ArrayList;
import java.util.List;

import futmatcher.kildare.com.futmatcher.model.Player;

/**
 * Created by kilda on 7/29/2018.
 */

public class MatchTestHelper {


	public static List<Player> getPlayerList()
	{
		List<Player> players = new ArrayList<>();

		players.add(new Player("Goleiro 1",Player.KEEPER));
		players.add(new Player("Goleiro 2",Player.KEEPER));
		players.add(new Player("Goleiro 3",Player.KEEPER));
		players.add(new Player("Goleiro 4",Player.KEEPER));
		players.add(new Player("Defensor 1",Player.DEFENSE));
		players.add(new Player("Defensor 2",Player.DEFENSE));
		players.add(new Player("Defensor 3",Player.DEFENSE));
		players.add(new Player("Defensor 4",Player.DEFENSE));
		players.add(new Player("Defensor 5",Player.DEFENSE));
		players.add(new Player("Defensor 6",Player.DEFENSE));
		players.add(new Player("Defensor 7",Player.DEFENSE));
		players.add(new Player("Defensor 8",Player.DEFENSE));
		players.add(new Player("Defensor 9",Player.DEFENSE));
		players.add(new Player("Defensor 10",Player.DEFENSE));
		players.add(new Player("Defensor 11",Player.DEFENSE));
		players.add(new Player("Meio 1",Player.MIDFIELD));
		players.add(new Player("Meio 2",Player.MIDFIELD));
		players.add(new Player("Meio 3",Player.MIDFIELD));
		players.add(new Player("Meio 4",Player.MIDFIELD));
		players.add(new Player("Meio 5",Player.MIDFIELD));
		players.add(new Player("Meio 6",Player.MIDFIELD));
		players.add(new Player("Meio 7",Player.MIDFIELD));
		players.add(new Player("Meio 8",Player.MIDFIELD));
		players.add(new Player("Meio 9",Player.MIDFIELD));
		players.add(new Player("Meio 10",Player.MIDFIELD));
		players.add(new Player("Meio 11",Player.MIDFIELD));
		players.add(new Player("Meio 12",Player.MIDFIELD));
		players.add(new Player("Meio 13",Player.MIDFIELD));
		players.add(new Player("Ataque 1",Player.ATTACK));
		players.add(new Player("Ataque 2",Player.ATTACK));
		players.add(new Player("Ataque 3",Player.ATTACK));
		players.add(new Player("Ataque 4",Player.ATTACK));
		players.add(new Player("Ataque 5",Player.ATTACK));
		players.add(new Player("Ataque 6",Player.ATTACK));

		return players;

	}

	public static int getNumPlayersByPosition(List<Player> players, String position) {
		int playerPositionTotal = 0;
		for (Player p : players) {
			playerPositionTotal += (p.getPosition().equals(position)) ? 1 : 0;
		}
		return playerPositionTotal;
	}

}
