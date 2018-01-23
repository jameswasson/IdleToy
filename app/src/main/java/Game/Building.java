package Game;

/**
 * Created by James on 12/20/2017.
 */

public class Building {
    public static final double INFLATIONRATE = 1.1;

    private int numberOwned;
    private int basePrice;
    private int baseRate;
    private String nameSingular;
    private String namePlural;

    public Building(int basePrice, int baseRate, String nameSingular, String namePlural) {
        numberOwned = 0;
        this.basePrice = basePrice;
        this.baseRate = baseRate;
        this.nameSingular = nameSingular;
        this.namePlural = namePlural;
    }

    public int getNumberOwned() {
        return numberOwned;
    }
    public int getBasePrice() {
        return basePrice;
    }
    public int getBaseRate() {
        return baseRate;
    }
    public String getNameSingular() {
        return nameSingular;
    }
    public String getNamePlural() {
        return namePlural;
    }
    public long getCurrentPrice(){
        return (long)(Math.pow(INFLATIONRATE,numberOwned)*(double)basePrice);
    }
    public void buy(){
        numberOwned++;
    }
    public void buyQuantity(int quantity){
        numberOwned += quantity;
    }

}