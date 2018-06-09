package futmatcher.kildare.com.futmatcher.model;

import java.nio.file.attribute.PosixFileAttributes;

/**
 * Created by kilda on 6/9/2018.
 */

public class Player {

    private String Name;
    private String Position;

    public Player(String name, String position)
    {
        name = Name;
        position = Position;
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
}
