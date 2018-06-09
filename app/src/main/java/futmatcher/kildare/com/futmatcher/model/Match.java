package futmatcher.kildare.com.futmatcher.model;

/**
 * Created by kilda on 6/9/2018.
 */

public class Match {

    private String Title;
    private String Location;
    private String Date;
    private String NumPlayers;
    private String MinPlayers;
    private String MaxPlayers;


    public Match(String title,String location,String date,String numPlayers,String minPlayers,String maxPlayers)
    {
        this.Title=title;
        this.Location=location;
        this.Date=date;
        this.NumPlayers=numPlayers;
        this.MinPlayers=minPlayers;
        this.MaxPlayers=maxPlayers;
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
}
