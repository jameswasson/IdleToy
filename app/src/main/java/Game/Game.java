package Game;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;

/**
 * Created by James on 12/20/2017.
 */

public class Game {
    public static final String[] AMOUNT_TO_BUY_NAMES = {"x1","x10","x100","Max"};
    public static final int[] AMOUNT_TO_BUY_NUMBERS = {1,10,100,-1};//-1 is considered max
    public int indexOfAmountToBuyArray;

    public static final String SAVE_DATA_FILE_NAME = "Save.txt";
    public static final long MILLION = ((long)(Math.pow(10,6)));
    public static final long BILLION = ((long)(Math.pow(10,9)));
    public static final long TRILLION = ((long)(Math.pow(10,12)));
    public static final long QUADRILLION = ((long)(Math.pow(10,15)));
    public static final long QUINTILLION = ((long)(Math.pow(10,18)));
    public static final long SEXTILLION = ((long)(Math.pow(10,21)));
    public static final String[] LARGE_NUMBER_NAMES = {"Million","Billion","Trillion","Quadrillion",
            "Quintillion","Sextillion","Septillion","Octillion","Nonillion","Decillion","Undecillion"
            ,"Dodecillion","Tredecillion"};

    private static Game ourGame;
    public static Game getGame(){
        if (ourGame == null)
            ourGame = new Game();
        return ourGame;
    }
    public static void deleteSaveData(){
        ourGame = null;
        Boxe.delete();
        BuildingManager.delete();
    }
    private Game(){
        indexOfAmountToBuyArray = 0;
    }

    public String getSaveData(){
        SaveData save = new SaveData();
        save.boxe = Boxe.getBox();
        save.buildingManager = BuildingManager.getBuildingManager();
        save.game = this;
        return new Gson().toJson(save);
    }
    public void storeSaveData(String saveData){
        SaveData load = new Gson().fromJson(saveData,SaveData.class);
        if (load == null) return;
        Boxe.setOurBox(load.boxe);
        BuildingManager.setOurBuildingManager(load.buildingManager);
        ourGame = load.game;
    }
    public double numberOfToys(){
        return Boxe.getBox().getToys();
    }
    public double totalRate(){
        return Boxe.getBox().getRate();
    }
    public void toyButtonPressed(){
        Boxe.getBox().toyButtonPressed();
    }
    public boolean tryToBuyBuilding(int indexOfBuilding){

        int numToBuy = AMOUNT_TO_BUY_NUMBERS[indexOfAmountToBuyArray];

        if (numToBuy == -1){
            buyMax(indexOfBuilding);
            return true;
        }
        else{
            return tryToBuyQuantity(indexOfBuilding,numToBuy);
        }
    }
    public void buyMax(int indexOfBuilding){
        //buys as many of the available building as possible
        int maxToBuy = getMaxNumberAbleToPurchaseOf(indexOfBuilding);
        tryToBuyQuantity(indexOfBuilding,maxToBuy);
    }
    public boolean tryToBuyQuantity(int indexOfBuilding, int quantity){
        if (getPriceOfQuantity(indexOfBuilding,quantity) <= Boxe.getBox().getToys()){
            buyBuildingQuantity(indexOfBuilding,quantity);
            return true;
        }
        else
            return false;
    }
    public long getPriceOfQuantity(int indexOfBuilding, int quantity){
        return BuildingManager.getBuildingManager().getPriceOfQuantity(indexOfBuilding,quantity);
    }
    public int getMaxNumberAbleToPurchaseOf(int indexOfBuilding){
        return BuildingManager.getBuildingManager().maxAbleToPurchaseOf(indexOfBuilding,Boxe.getBox().getToys());
    }
    public long priceOf(int indexOfBuilding){
        return getPriceOfQuantity(indexOfBuilding,1);
    }
    public String nameOf(int indexOfBuilding){
        if (numberOwnedOf(indexOfBuilding) == 1)
            return nameOfSingular(indexOfBuilding);
        else
            return nameOfPlural(indexOfBuilding);
    }
    public int numberOwnedOf(int indexOfBuilding){
        return BuildingManager.getBuildingManager().numberOwnedOf(indexOfBuilding);
    }
    public int getRateOf(int indexOfBuilding){
        return BuildingManager.getBuildingManager().getBaseRateOf(indexOfBuilding);
    }
    public List<String> getListOfBuildingNames(){
        return BuildingManager.getBuildingManager().getListOfBuildingNames();
    }
    public String getInfoForBuilding(int indexOfBuilding){
        String toReturn = "";
        toReturn += nameOfSingular(indexOfBuilding);
        toReturn += "\nOwned: " + numberOwnedOf(indexOfBuilding);
        toReturn += " Rate: " + convertDoubleToChosenFormat(getRateOf(indexOfBuilding));
        toReturn += "\nPrice: " + convertDoubleToChosenFormat(priceOf(indexOfBuilding));
        return toReturn;
    }
    public String getPrestigeInfo(){
        String toReturn = "";
        toReturn += "You can reset your game to receive buffs based on your previous games\n";
        String maxBuildingName = BuildingManager.getBuildingManager().getNameSingularOf(getListOfBuildingNames().size()-1);
        toReturn += "You receive one Gold for every " + maxBuildingName + " you have when you reset (2% multiplier each)\n";
        String minBuildingName = BuildingManager.getBuildingManager().getNamePluralOf(0);
        toReturn += "You will also keep 1/20 of all your " + minBuildingName + " when you reset\n";
        toReturn += minBuildingName + " you will keep on reset: " + (BuildingManager.getBuildingManager().numberOwnedOf(0)/20) + "\n";
        toReturn += "Gold you will receive on reset: " + BuildingManager.getBuildingManager().numberOwnedOf(getListOfBuildingNames().size()-1) + "\n";
        toReturn += "Current Gold: " + Boxe.getBox().getPrestige();
        return toReturn;
    }
    public void prestige(){
        int numOfHighestBuildingPurchased = BuildingManager.getBuildingManager().numberOwnedOf(getListOfBuildingNames().size()-1);
        int numOfLowestBuildingsPurchased = BuildingManager.getBuildingManager().numberOwnedOf(0);
        BuildingManager.delete();
        Boxe.getBox().prestige(numOfHighestBuildingPurchased);

        Boxe.getBox().updateAfterPurchase(0,BuildingManager.getBuildingManager().getBaseRateOf(0) * (numOfLowestBuildingsPurchased/20));
        BuildingManager.getBuildingManager().buyQuantity(0, numOfLowestBuildingsPurchased/20);
    }
    public String nameOfSingular(int indexOfBuilding){
        return BuildingManager.getBuildingManager().getNameSingularOf(indexOfBuilding);
    }
    public String nameOfPlural(int indexOfBuilding){
        return BuildingManager.getBuildingManager().getNamePluralOf(indexOfBuilding);
    }

    public void amountToBuyButtonPressed(){
        indexOfAmountToBuyArray = (indexOfAmountToBuyArray + 1) % AMOUNT_TO_BUY_NAMES.length;
    }
    public String amountToBuyButtonString(){
        return AMOUNT_TO_BUY_NAMES[indexOfAmountToBuyArray];
    }


    private boolean canBuyBuilding(int indexOfBuilding){
        return getPriceOfQuantity(indexOfBuilding,1) <= numberOfToys();
    }
    private void buyBuilding(int indexOfBuilding){
        buyBuildingQuantity(indexOfBuilding,1);
    }
    private void buyBuildingQuantity(int indexOfBuilding,int quantity){
        long totalPrice = getPriceOfQuantity(indexOfBuilding,quantity);
        Boxe.getBox().updateAfterPurchase(totalPrice,BuildingManager.getBuildingManager().getBaseRateOf(indexOfBuilding) * quantity);
        BuildingManager.getBuildingManager().buyQuantity(indexOfBuilding,quantity);
    }

    public static String convertDoubleToChosenFormat(double inputNumber){
        if (inputNumber < MILLION)
            inputNumber = (double)((int)(inputNumber));
        return convertDoubleToNumWithNames(inputNumber);//for now this will be the default
    }
    public static String convertDoubleToNumWithCommas(double inputNumber){
        String toReturn = "";
        String inputAsString = "" + inputNumber;
        while (inputAsString.length() > 3){
            String firstThreeChars = inputAsString.substring(inputAsString.length() - 3);
            inputAsString = inputAsString.substring(0,inputAsString.length() - 3);
            toReturn = "," + firstThreeChars + toReturn;
        }
        toReturn = inputAsString + toReturn;
        return toReturn;
    }
    public static String convertDoubleToNumWithNames(double inputNumber){
        String toReturn = "";
        String number = "";
        if (inputNumber < MILLION) {
            toReturn = convertDoubleToNumWithCommas(inputNumber);
            return inputNumber + "";
        }
        else {
            double numberOfDigits =  Math.log(inputNumber)/Math.log(10) + 0.0000000002;
            numberOfDigits -= 6 ;//this way millon is reduced to zero
            numberOfDigits /= 3;//this way each -illion is right next to each other
            int numberOfDigitsAsInt = (int) numberOfDigits;
            if (numberOfDigitsAsInt < LARGE_NUMBER_NAMES.length){
                number = convertDoubleToStringWith3Decimals(((double)(inputNumber))/((double)(Math.pow(10,(numberOfDigitsAsInt+2)*3)))) + "";
                toReturn += number + " " + LARGE_NUMBER_NAMES[numberOfDigitsAsInt];
            }
            else
                toReturn += inputNumber + "TOO BIG";
        }
        return toReturn;
    }
    public static String convertDoubleToStringWith3Decimals(double inputNumber){
        String fullNumber = "" + inputNumber;
        if (!fullNumber.contains("."))
            return fullNumber;
        int indexOfDot = fullNumber.indexOf('.');

        boolean allZerosPastThePeriod = true;
        for (int i = indexOfDot + 1; i < fullNumber.length(); i++){
               if (fullNumber.charAt(i) != '0')
                   allZerosPastThePeriod = false;
        }
        if (allZerosPastThePeriod)
            return fullNumber.substring(0,indexOfDot);

        if (fullNumber.length() - indexOfDot < 5)
            return fullNumber;
        return fullNumber.substring(0,indexOfDot + 4);
    }
    public void writeToFile(String data, String fileName){
        System.out.println(fileName);
        //System.out.println(mainActivityContext.getFilesDir());
        try {
            FileWriter fileWriter =
                    new FileWriter(fileName);
            BufferedWriter bufferedWriter =
                    new BufferedWriter(fileWriter);
            bufferedWriter.write(data);
            bufferedWriter.close();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    public String readFromFile(String fileName){
        String toReturn = "";
        System.out.println(fileName);
        try {
            Scanner sc = new Scanner(new File(fileName));

            sc.useDelimiter("\\Z");
            toReturn = sc.next();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return toReturn;
    }
}