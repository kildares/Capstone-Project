package futmatcher.kildare.com.futmatcher.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import paperparcel.PaperParcel;

/**
 * Created by kilda on 7/29/2018.
 */
@PaperParcel
public class Team implements Parcelable{

	public static final Parcelable.Creator<Team> CREATOR = PaperParcelTeam.CREATOR;


	protected List<Player> TeamPlayers;
	protected List<Player> TeamSubstitutes;

	public Team(){}

	public Team(List<Player> teamPlayers){
		this.TeamPlayers = teamPlayers;
		TeamSubstitutes = new ArrayList<>();
	}

	public Team(List<Player> teamPlayers, List<Player> teamSubstitutes){
		this.TeamPlayers = teamPlayers;
		TeamSubstitutes = teamSubstitutes;
	}

	public List<Player> getTeamPlayers() {
		return TeamPlayers;
	}

	public void setTeamPlayers(List<Player> teamPlayers) {
		TeamPlayers = teamPlayers;
	}

	public List<Player> getTeamSubstitutes() {
		return TeamSubstitutes;
	}

	public void setTeamSubstitutes(List<Player> teamSubstitutes) {
		TeamSubstitutes = teamSubstitutes;
	}

	public void addSubstitute(Player player){
		this.TeamSubstitutes.add(player);
	}

	public void addPlayer(Player p){
		TeamPlayers.add(p);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		PaperParcelTeam.writeToParcel(this, parcel, i);
	}
}
