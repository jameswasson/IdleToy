package com.example.james.idletoy;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Game.Game;

public class PrestigeActivity extends Activity {

    Button backButton;
    Button prestigeButton;
    TextView mainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestige);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mainText = findViewById(R.id.prestige_main_text);
        mainText.setText(textForMainText());

        prestigeButton = findViewById(R.id.prestige);
        prestigeButton.setText("Prestige Now");
        prestigeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game.getGame().prestige();
                mainText.setText(textForMainText());
                finish();
            }
        });
    }
    private String textForMainText(){
        return "James Wasson 2018" + "\n" + Game.getGame().getPrestigeInfo();
    }
}