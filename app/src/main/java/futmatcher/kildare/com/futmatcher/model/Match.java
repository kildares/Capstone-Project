package futmatcher.kildare.com.futmatcher.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import paperparcel.PaperParcel;

/**
 * Created by kilda on 6/9/2018.
 */
@PaperParcel
public class Match implements Parcelable {

    public static final Creator<Match> CREATOR = PaperParcelMatch.CREATOR;

    private String Title;
    private String Location;
    private String Date;
    private String NumPlayers;
    private String MinPlayers;
    private String MaxPlayers;
    private List<Player> Players;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        PaperParcelMatch.writeToParcel(this, parcel, i);
    }
}
