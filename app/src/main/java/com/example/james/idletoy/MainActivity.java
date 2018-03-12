package com.example.james.idletoy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import Game.Game;

public class MainActivity extends Activity {
    private Button amountToBuyButton;
    private Button prestigeButton;
    private Button statsButton;
    private Button toyButton;
    private AdView adView;
    private TextView mainText;
    private TextView rateText;
    private RecyclerView myRecyclerView;
    private LinearLayoutManager myLinearLayoutManager;
    private BuildingAdapter myBuildingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();
        setMenuButtons();
        setUpperScreenViews();
        setRecyclerView();
        setAdvertisement();
        refreshToyRate();
    }
    @Override
    protected void onResume(){
        super.onResume();

        setRecyclerView();
        refreshToyRate();
    }
    private void setRecyclerView(){
        myRecyclerView = (RecyclerView) findViewById(R.id.building_main_activity);
        myLinearLayoutManager = new LinearLayoutManager(this);
        myLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecyclerView.setLayoutManager(myLinearLayoutManager);

        myBuildingAdapter = new BuildingAdapter(this);
        myRecyclerView.setAdapter(myBuildingAdapter);

    }
    private void setMenuButtons(){
        amountToBuyButton = findViewById(R.id.amount_to_buy_button);
        prestigeButton = findViewById(R.id.prestige_menu_button);
        statsButton = findViewById(R.id.stats_button);

        prestigeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prestigeButtonPressed();
            }
        });

        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statsButtonPressed();
            }
        });

        amountToBuyButton.setText(Game.getGame().amountToBuyButtonString());
        amountToBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {amountToBuyButtonPressed();
            }
        });
    }
    private void setUpperScreenViews(){
        mainText = (TextView)findViewById(R.id.main_text_view);
        rateText = (TextView)findViewById(R.id.toy_rate);

        toyButton = (Button) findViewById(R.id.Toy_Button1);
        toyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game.getGame().toyButtonPressed();
                saveData();
            }
        });

        Thread t = new Thread() {

            @Override
            public void run() {

                //this is it's own thread

                try {
                    while (!isInterrupted()) {//this is a loop
                        Thread.sleep(15);//15 ms
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // this is the UI thread

                                refreshToysNumber();

                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }
    private void setAdvertisement(){
        MobileAds.initialize(this,"ca-app-pub-2940609218659931~4962793124");
        adView = (AdView)findViewById(R.id.ad_view);
        AdRequest myAdRequest = new AdRequest.Builder().build();
        adView.loadAd(myAdRequest);
    }
    public void changeMainText(String text){
        mainText.setText(text);
    }
    public void refreshToysNumber(){
        changeMainText(Game.convertDoubleToChosenFormat(Game.getGame().numberOfToys()));
    }
    public void refreshToyRate(){
        rateText.setText(Game.getGame().convertDoubleToChosenFormat(Game.getGame().totalRate()));
        saveData();
    }

    private void amountToBuyButtonPressed(){
        Game.getGame().amountToBuyButtonPressed();
        amountToBuyButton.setText(Game.getGame().amountToBuyButtonString());
    }
    private void prestigeButtonPressed(){
        startActivity(new Intent(this, PrestigeActivity.class));
    }
    private void statsButtonPressed(){}

    public void saveData(){
        String path = Game.SAVE_DATA_FILE_NAME;
        String dataToSave = Game.getGame().getSaveData();
        writeToFile(dataToSave,path);
    }
    public void loadData(){
        String path = Game.SAVE_DATA_FILE_NAME;
        String loadedData = readFromFile(path);
        Game.getGame().storeSaveData(loadedData);
    }
    public void writeToFile(String data, String path) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(path, MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    public String readFromFile(String path) {

        String ret = "";

        try {
            InputStream inputStream = openFileInput(path);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}