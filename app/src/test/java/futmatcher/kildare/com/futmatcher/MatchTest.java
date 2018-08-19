package futmatcher.kildare.com.futmatcher;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import futmatcher.kildare.com.futmatcher.model.Match;
import futmatcher.kildare.com.futmatcher.model.Player;
import futmatcher.kildare.com.futmatcher.model.Team;

import static junit.framework.Assert.assertEquals;

/**
 * Created by kilda on 7/29/2018.
 */

public class MatchTest {

	private Match testMatch;

	@Before
	public void initializeMatch()
	{
		List<Player> players = MatchTestHelper.getPlayerList();
		testMatch = new Match("Partida Teste 1", "Bazileu", "12-11-89", "11", "22","300", players);
	}


	@Test
	public void testPickTeamRandomly()
	{
		testMatch.pickTeamsRandomly();
		Team team1 = testMatch.getTeam1();
		Team team2 = testMatch.getTeam2();

		int totalSize = team1.getTeamPlayers().size() +
						team1.getTeamSubstitutes().size() +
						team2.getTeamPlayers().size() +
						team2.getTeamSubstitutes().size();

		assertEquals(34,totalSize);
		assertEquals(Integer.parseInt(testMatch.getNumPlayers()),team1.getTeamPlayers().size());
		assertEquals(Integer.parseInt(testMatch.getNumPlayers()),team2.getTeamPlayers().size());
		assertEquals(team1.getTeamSubstitutes().size() + team2.getTeamSubstitutes().size(), 12);
	}

	@Test
	public void testPickTeamsByPosition()
	{
		testMatch.pickTeamsByPosition();
		Team team1 = testMatch.getTeam1();
		Team team2 = testMatch.getTeam2();

		int totalSize = team1.getTeamPlayers().size() +
				team1.getTeamSubstitutes().size() +
				team2.getTeamPlayers().size() +
				team2.getTeamSubstitutes().size();

		assertEquals(34,totalSize);
		assertEquals(Integer.parseInt(testMatch.getNumPlayers()),team1.getTeamPlayers().size());
		assertEquals(Integer.parseInt(testMatch.getNumPlayers()),team2.getTeamPlayers().size());
		assertEquals(team1.getTeamSubstitutes().size() + team2.getTeamSubstitutes().size(), 12);
	}

	@Test
	public void testPickedTeamsPositions(){
		testMatch.pickTeamsByPosition();
		Team team1 = testMatch.getTeam1();
		Team team2 = testMatch.getTeam2();

		List<Player> team1Players = team1.getTeamPlayers();
		List<Player> team2Players = team2.getTeamPlayers();

		int team1Keepers = MatchTestHelper.getNumPlayersByPosition(team1Players, Player.KEEPER);
		int team1Defense = MatchTestHelper.getNumPlayersByPosition(team1Players, Player.DEFENSE);
		int team1Midfield = MatchTestHelper.getNumPlayersByPosition(team1Players, Player.MIDFIELD);
		int team1Attack = MatchTestHelper.getNumPlayersByPosition(team1Players, Player.ATTACK);

		int team2Keepers = MatchTestHelper.getNumPlayersByPosition(team2Players, Player.KEEPER);
		int team2Defense = MatchTestHelper.getNumPlayersByPosition(team2Players, Player.DEFENSE);
		int team2Midfield = MatchTestHelper.getNumPlayersByPosition(team2Players, Player.MIDFIELD);
		int team2Attack = MatchTestHelper.getNumPlayersByPosition(team2Players, Player.ATTACK);

		assertEquals(1,team1Keepers);
		assertEquals(4,team1Defense);
		assertEquals(4,team1Midfield);
		assertEquals(2,team1Attack);
		assertEquals(6, team1.getTeamSubstitutes().size());
		assertEquals(1,team2Keepers);
		assertEquals(4,team2Defense);
		assertEquals(4,team2Midfield);
		assertEquals(2,team2Attack);
		assertEquals(6, team2.getTeamSubstitutes().size());

	}

}
