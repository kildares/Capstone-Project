package futmatcher.kildare.com.futmatcher.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import paperparcel.PaperParcel;

/**
 * Created by kilda on 6/9/2018.
 */
@PaperParcel
public class Match implements Parcelable, PickTeam {

    public static final Creator<Match> CREATOR = PaperParcelMatch.CREATOR;

    private String Title;
    private String Location;
    private String Date;
    private String NumPlayers;
    private String MinPlayers;
    private String MaxPlayers;
    private List<Player> Players;
    private Team Team1;
    private Team Team2;
    public Match()
    {

    }

    public Match(String title,String location,String date,String numPlayers,String minPlayers,String maxPlayers)
    {
        this.Title=title;
        this.Location=location;
        this.Date=date;
        this.NumPlayers=numPlayers;
        this.MinPlayers=minPlayers;
        this.MaxPlayers=maxPlayers;
        Players = new ArrayList<>();

    }

    public Match(String title,String location,String date,String numPlayers,String minPlayers,String maxPlayers,List<Player> players)
    {
        this.Title=title;
        this.Location=location;
        this.Date=date;
        this.NumPlayers=numPlayers;
        this.MinPlayers=minPlayers;
        this.MaxPlayers=maxPlayers;
        Players = players;
    }

    protected Match(Parcel in) {
        Title = in.readString();
        Location = in.readString();
        Date = in.readString();
        NumPlayers = in.readString();
        MinPlayers = in.readString();
        MaxPlayers = in.readString();
    }

    public void addPlayerToMatch(Player player)
    {
        if(this.Players == null)
            this.Players = new ArrayList<>();
        this.Players.add(player);
    }

    public boolean removePlayerFromMatch(Player player)
    {
        if(this.Players == null)
            this.Players = new ArrayList<>();
        return this.Players.remove(player);
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getNumPlayers() {
        return NumPlayers;
    }

    public void setNumPlayers(String numPlayers) {
        NumPlayers = numPlayers;
    }

    public String getMinPlayers() {
        return MinPlayers;
    }

    public void setMinPlayers(String minPlayers) {
        MinPlayers = minPlayers;
    }

    public String getMaxPlayers() {
        return MaxPlayers;
    }

    public void setMaxPlayers(String maxPlayers) {
        MaxPlayers = maxPlayers;
    }

    public List<Player> getPlayers() {
        return Players;
    }

    public void setPlayers(List<Player> players) {
        Players = players;
    }

	public Team getTeam1() {
		return Team1;
	}

	public void setTeam1(Team team1) {
		Team1 = team1;
	}

	public Team getTeam2() {
		return Team2;
	}

	public void setTeam2(Team team2) {
		Team2 = team2;
	}

	@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        PaperParcelMatch.writeToParcel(this, parcel, i);
    }

    @Override
    public boolean pickTeamsRandomly(){

        boolean[] posPlayer = new boolean[Players.size()];
        for(int i = 0 ; i < posPlayer.length ; i++)
            posPlayer[i] = false;

        posPlayer = getTeamRandom(posPlayer, true);
        posPlayer = getTeamRandom(posPlayer, false);
        posPlayer = getTeamReserves(posPlayer);

        return true;
    }

    public boolean[] getTeamRandom(boolean[] players, boolean isFirstTeam){

        List<Player> team = new ArrayList<>();
        Random random = new Random();
        boolean[] playersUpd = players;

        while(team.size() < Integer.parseInt(NumPlayers)){
            int pos = random.nextInt(players.length);
            if(playersUpd[pos] != true){
                team.add(getPlayers().get(pos));
                playersUpd[pos] = true;
            }
        }
        if(isFirstTeam)
            Team1 = new Team(team);
        else
            Team2 = new Team(team);
        return playersUpd;
    }

    private boolean[] getTeamReserves(boolean[] players){

        boolean isTeam1 = true;

        Team1.setTeamSubstitutes(new ArrayList<Player>());
        Team2.setTeamSubstitutes(new ArrayList<Player>());

        for(int i = 0 ; i < players.length ; i++){
            if(!players[i]){
                if(isTeam1) {
                    Team1.addSubstitute(getPlayers().get(i));
                    players[i] = true;
                    isTeam1 = false;
                }
                else{
                    Team2.addSubstitute(getPlayers().get(i));
                    players[i] = true;
                    isTeam1 = true;
                }
            }
        }
        return players;
    }

    @Override
    public boolean pickTeamsByPosition()
    {

        Team1 = new Team(new ArrayList<Player>());
        Team2 = new Team(new ArrayList<Player>());

        List<Player> keepers = sortPlayersByPosition(Player.KEEPER);
        List<Player> defense = sortPlayersByPosition(Player.DEFENSE);
        List<Player> midfield = sortPlayersByPosition(Player.MIDFIELD);
        List<Player> attack = sortPlayersByPosition(Player.ATTACK);

        if(Integer.parseInt(this.NumPlayers) != 11 || keepers.size() < 2 || defense.size() < 8 || midfield.size() < 8){
			throw new RuntimeException("Not enough players");
		}


		addPlayersToTeamsByPosition(keepers, 1);
		addPlayersToTeamsByPosition(defense,4);
		addPlayersToTeamsByPosition(midfield,4);
		addPlayersToTeamsByPosition(attack,2);

		return true;
    }

    public List<Player> sortPlayersByPosition(String position){
        List<Player> player = new ArrayList<>();
        for(Player p : Players)
            if(position.equals(p.getPosition()))
                player.add(p);
        return player;
    }

    public boolean addPlayersToTeamsByPosition(List<Player> players, int limitOfPosition)
    {
     	   if(players.size() == 0)
            return false;
        boolean[] keeperSelected = new boolean[players.size()];
		for(int i = 0 ; i < keeperSelected.length ; i++)
			keeperSelected[i] = false;

		Random random = new Random();

		int addedTeam1=0;
		int addedTeam2=0;
		int addedTotal=0;

		while(addedTotal < players.size()){
			int pos = random.nextInt(players.size());
			if(!keeperSelected[pos]){
				if(addedTeam1 < limitOfPosition){
					Team1.addPlayer(players.get(pos));
					addedTeam1++;
				}
				else if(addedTeam2 < limitOfPosition){
					Team2.addPlayer(players.get(pos));
					addedTeam2++;
				}
				else if(Team1.getTeamSubstitutes().size() < Team2.getTeamSubstitutes().size())
					Team1.addSubstitute(players.get(pos));
				else
					Team2.addSubstitute(players.get(pos));

				addedTotal++;
				keeperSelected[pos] = true;
			}
		}
		return true;
    }
}
