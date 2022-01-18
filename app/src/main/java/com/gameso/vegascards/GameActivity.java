package com.gameso.vegascards;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class GameActivity extends AppCompatActivity {
    private ImageView imageViewRandom;
    private ImageView[] suits;
    private String selectedSuit;
    private boolean gameRunning = false;
    private final static int DEFAULT_START_POINTS = 1000;
    private final static int MINIMAL_BET = 25;
    private int points;
    private int bet = MINIMAL_BET;
    private TextView textViewPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        points = getSharedPreferences("save", 0).getInt("points",  DEFAULT_START_POINTS);

        ImageView imageViewSpade = findViewById(R.id.imageViewSpade);
        ImageView imageViewHeart = findViewById(R.id.imageViewHeart);
        ImageView imageViewDiamond = findViewById(R.id.imageViewDiamond);
        ImageView imageViewClub = findViewById(R.id.imageViewClub);
        imageViewRandom = findViewById(R.id.imageViewRandom);
        suits = new ImageView[] {imageViewSpade, imageViewDiamond, imageViewClub, imageViewHeart};
        for(ImageView suit : suits) {
            suit.setOnClickListener(onClickGameListener);
            String resourceName = getResources().getResourceEntryName(suit.getId());
            suit.setTag(resourceName);
        }

        Button buttonExitGame = findViewById(R.id.buttonExitGame);
        Button buttonBet25 = findViewById(R.id.buttonBet25);
        Button buttonBet50 = findViewById(R.id.buttonBet50);
        Button buttonBet100 = findViewById(R.id.buttonBet100);
        buttonExitGame.setOnClickListener(onClickExitGame);
        Button[] betButtons = new Button[]{buttonBet25, buttonBet50, buttonBet100};
        for(Button betButton : betButtons) {
            betButton.setOnClickListener(onClickChangeBet);

        }

        textViewPoints = findViewById(R.id.textViewPoints);
        textViewPoints.setText(String.format(Locale.getDefault(), "%d", points));
    }

    View.OnClickListener onClickExitGame = view -> finishAffinity();

    View.OnClickListener onClickChangeBet = view -> {
        bet = Integer.parseInt(view.getTag().toString());
        Toast.makeText(getApplicationContext(), "Bet set to" + bet, Toast.LENGTH_SHORT).show();
    };

    View.OnClickListener onClickGameListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!gameRunning){
                selectedSuit = view.getTag().toString();
                startAnimation();
            } else {
                Toast.makeText(getApplicationContext(), "You already did your spin, dumbass!", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void changePoints(int value){
        points += value;
        textViewPoints.setText(String.format(Locale.getDefault(), "%d", points));
    }

    public void startAnimation(){
        if(!gameRunning){
            gameRunning = true;
            CountDownTimer countDownTimer = new CountDownTimer(4000, 125) {
                @Override
                public void onTick(long l) {
                    int random = (int) (Math.random() * suits.length);
                    imageViewRandom.setImageDrawable(suits[random].getDrawable());
                    imageViewRandom.setTag(suits[random].getTag().toString());
                }

                @Override
                public void onFinish() {
                gameRunning = false;
                int value;
                if (imageViewRandom.getTag().equals(selectedSuit)){
                    Toast.makeText(GameActivity.this, "Great, you win!", Toast.LENGTH_SHORT).show();
                    value = bet;
                } else {
                    Toast.makeText(GameActivity.this, "Fool, you loose!", Toast.LENGTH_SHORT).show();
                    value = -bet;
                }
                changePoints(value);
                }
            }.start();
        }
    }
}