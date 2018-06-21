package futmatcher.kildare.com.futmatcher.model;

import android.os.Parcel;
import android.os.Parcelable;

import paperparcel.PaperParcel;

/**
 * Created by kilda on 6/9/2018.
 */
@PaperParcel
public class Player implements Parcelable{

    public static final Creator<Player> CREATOR = PaperParcelPlayer.CREATOR;

    private String Name;
    private String Position;

    public Player(String Name, String Position)
    {
        this.Name = Name;
        this.Position = Position;
    }

    public Player(String name)
    {
        Name = name;
        Position = "NA";
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        PaperParcelPlayer.writeToParcel(this, parcel, i);
    }
}
