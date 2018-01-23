package Game;

import java.util.ArrayList;
import java.util.List;


public class BaseGameInfo {
    private static List<BuildingInfo> ourBuildings;
    public static List<BuildingInfo> getOurBuildings(){
        if (ourBuildings != null)
            return  ourBuildings;
        ourBuildings = new ArrayList<>();
        ourBuildings.add(new BuildingInfo("Hammers","Hammer",60,1));
        ourBuildings.add(new BuildingInfo("Machines","Machine",400,4));
        ourBuildings.add(new BuildingInfo("Assembly Lines","Assembly Line",2000,20));
        ourBuildings.add(new BuildingInfo("Factories","Factory",8000,50));
        ourBuildings.add(new BuildingInfo("Cities","City",28000,100));
        ourBuildings.add(new BuildingInfo("Countries","Country",200000,500));
        ourBuildings.add(new BuildingInfo("Planets","Planet",4000000,6666));
        ourBuildings.add(new BuildingInfo("Galaxies","Galaxy",1234567890,246810));
        return ourBuildings;
    }
}