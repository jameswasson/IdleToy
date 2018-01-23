package Game;

/**
 * Created by James on 12/20/2017.
 */

public class BuildingInfo {
    public String getPluralName() {
        return pluralName;
    }
    public String getSingularName() {
        return singularName;
    }
    public int getBaseRate() {
        return baseRate;
    }
    public int getBasePrice() {
        return basePrice;
    }

    String pluralName;
    String singularName;
    int baseRate;
    int basePrice;

    public BuildingInfo(String pluralName, String singularName, int basePrice, int baseRate) {
        this.pluralName = pluralName;
        this.singularName = singularName;
        this.baseRate = baseRate;
        this.basePrice = basePrice;
    }
}