package Game;

import com.google.gson.Gson;

//treat this Boxe like a cloud, I called it a boxe when I made it because I didn't know things like this were known as clouds
//anyways, I don't really understand some of these formulas anymore, I could derive them though.
public class Boxe
{
      private long prevMillis;
      private double rate;
      private double prestige;
      private double smallNum;

    private static Boxe ourBox;
    public static Boxe getBox(){
        if (ourBox == null)
            ourBox = new Boxe();
        return ourBox;
    }
    public static void delete(){
        ourBox = null;
    }
    public static void setOurBox(Boxe newBoxe){
        ourBox = newBoxe;
    }

    private Boxe() {
        prevMillis = System.currentTimeMillis();
        rate = 0L;
        prestige = 0L;
        smallNum = 0L;
    }

    public void toyButtonPressed(){
        subtractToys(-1-getToys());//adds one toy
    }
    public void updateAfterPurchase(long priceChange, long rateChange){
        if (priceChange != 0)
            subtractToys(priceChange);
        if (rateChange != 0)
        updateByRateChange(rateChange);
    }

    public void updateByRateChange(long rateChange)
    {
        if (rateChange == 0) return;
        updateTime(rateChange);
        updateRate(rateChange);
    }
      
    private void updateTime(long rateChange) {
        if (getRate() == 0L) 
        {
            prevMillis = (long)(System.currentTimeMillis() - smallNum / rateChange);
        }
        else
            prevMillis = ((System.currentTimeMillis() - (long)(getRate() * (System.currentTimeMillis() - prevMillis) / ((getBaseRate() + rateChange) * (1.0D + 0.02D * prestige)))));
    }
  
    private void updateRate(long rateChange) 
    {
        rate = (getBaseRate() + rateChange); 
    }
  
    public void subtractToys(double priceChange) {
        if (getRate() == 0L) {
          smallNum -= priceChange;
        }
        else {
            prevMillis += (long)(1000.0*priceChange / (getRate()));
        }
    }
      
    public double getToys() {
        if (getRate() == 0L)
            return smallNum;
        return getRate() * (System.currentTimeMillis() - prevMillis) / 1000L;
    }
      
    public double getRate()
    {
        return (rate * (1.0 + 0.02 * prestige));
    }

    public double getBaseRate() {
        return rate; 
    }
      
    public long getTime() {
        if (getRate() == 0L)
            return System.currentTimeMillis();
        return prevMillis;
      }
      
    public double getPrestige()
    {
        return prestige; 
    }
    
    private void setPrevMillis(long time)
    {
        prevMillis = time;
    }
      
    private void setBaseRate(long rat)
    {
        rate = rat; 
    }
      
    private void setPrestige(long a) {
        prestige = a;
    }

    public void prestige(int a){
        double newPrestigeNumber = prestige + a;
        ourBox = new Boxe();
        ourBox.prestige = newPrestigeNumber;
    }

    public void reCalculateToyRate(){
        int numberOfBuildings = BuildingManager.getBuildingManager().getListOfBuildingNames().size();
        BuildingManager ourBuildingManager = BuildingManager.getBuildingManager();
        int newRate = 0;
        for (int i = 0; i < numberOfBuildings; i++){
            newRate += ourBuildingManager.getTotalRateOf(i);
        }
        setBaseRate(newRate);
    }
}