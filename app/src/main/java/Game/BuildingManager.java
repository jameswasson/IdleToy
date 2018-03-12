package Game;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 12/20/2017.
 */

public class BuildingManager {
    private BuildingManager(){
        List<BuildingInfo> ourInfo = BaseGameInfo.getOurBuildings();
        ourBuildings = new ArrayList<>();
        ourBuildingNames = new ArrayList<>();
        for (BuildingInfo i: ourInfo){
            ourBuildings.add(new Building(i.getBasePrice(),i.getBaseRate(),i.getSingularName(),i.getPluralName()));
            ourBuildingNames.add(i.getSingularName());
        }
    }
    private static BuildingManager ourBuildingManager;
    public static BuildingManager getBuildingManager(){
        if (ourBuildingManager == null)
            ourBuildingManager = new BuildingManager();
        return ourBuildingManager;
    }
    public static void setOurBuildingManager(BuildingManager newBuildingManager){
        ourBuildingManager = newBuildingManager;
    }
    public static void delete(){
        ourBuildingManager = null;
    }

    private List<Building> ourBuildings;
    private List<String> ourBuildingNames;//singular

    public double getPriceOfQuantity(int indexOfBuilding,int quantity){
        double r = Building.INFLATIONRATE;
        double start = getPriceOf(indexOfBuilding);
        return (1.0-Math.pow(r,quantity))*(start)/(1.0-r);
    }
    public int maxAbleToPurchaseOf(int indexOfBuilding,double currentNumberOfToys){
        double r = Building.INFLATIONRATE;
        double start = getPriceOf(indexOfBuilding);
        return (int) (Math.log(1-(currentNumberOfToys)*(1-r)/(start))/Math.log(r));
    }
    public void buyQuantity(int indexOfBuilding, int quantity){
        ourBuildings.get(indexOfBuilding).buyQuantity(quantity);
    }
    public int numberOwnedOf(int indexOfBuilding){
        return ourBuildings.get(indexOfBuilding).getNumberOwned();
    }
    public int getBaseRateOf(int indexOfBuilding){
        return ourBuildings.get(indexOfBuilding).getBaseRate();
    }
    public int getTotalRateOf(int indexOfBuilding){
        return ourBuildings.get(indexOfBuilding).getBaseRate() * ourBuildings.get(indexOfBuilding).getNumberOwned();
    }
    public String getNameSingularOf(int indexOfBuilding){
        return ourBuildings.get(indexOfBuilding).getNameSingular();
    }
    public String getNamePluralOf(int indexOfBuilding){
        return ourBuildings.get(indexOfBuilding).getNamePlural();
    }
    public double getPriceOf(int indexOfBuilding){
        return ourBuildings.get(indexOfBuilding).getCurrentPrice();
    }
    public void buy(int indexOfBuilding){
        ourBuildings.get(indexOfBuilding).buy();
    }
    public List<String> getListOfBuildingNames(){
        return ourBuildingNames;
    }
}