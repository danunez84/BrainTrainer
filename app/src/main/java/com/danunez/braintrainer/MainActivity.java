package com.danunez.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    LinearLayout myLinearLayout;

    TextView textViewTime;
    TextView textViewQuestion;
    TextView textViewScore;
    TextView textViewDisplay;

    Button buttonGoPlay;

    Button button00;
    Button button01;
    Button button10;
    Button button11;

    Button buttonPlayAgain;

    int num1;
    int num2;
    int myResult;
    int score;
    int plays;

    Random myRandom;

    public String[] generateNumbers(){

        // {textDisplay , pos1, pos2, pos3, pos4}
        myRandom = new Random();
        num1 = myRandom.nextInt(50);
        num2 = myRandom.nextInt(50);
        myResult = num1 + num2;

        textViewQuestion.setText(num1 + " + " + num2);
        button00.setText("" + myResult);

        String textDisplay = num1 + " + " + num2;
        String[] arrayContent = {textDisplay,"","","",""};

        int posAnswer = myRandom.nextInt(3) + 1;
        arrayContent [posAnswer] = String.valueOf(myResult);

        for (int i = 1 ; i < 5 ; i++ ){
            if (arrayContent[i].equals("")){
                int numToFillMatrix = myRandom.nextInt(50)+50;
                arrayContent[i] = String.valueOf(numToFillMatrix);
            }
        }
        return arrayContent;
    }

    public void generatePlay(){
        //{textDisplay , pos1, pos2, pos3, pos4}
        String[] numbers = generateNumbers();
        textViewQuestion.setText(numbers[0]);
        button00.setText(numbers[1]);
        button01.setText(numbers[2]);
        button10.setText(numbers[3]);
        button11.setText(numbers[4]);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLinearLayout = findViewById(R.id.myLinearLayout);

        textViewTime = findViewById(R.id.textViewTime);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewScore = findViewById(R.id.textViewScore);
        textViewDisplay = findViewById(R.id.textViewDisplay);

        buttonGoPlay = findViewById(R.id.buttonGo);
        button00 = findViewById(R.id.button00);
        button01 = findViewById(R.id.button01);
        button10 = findViewById(R.id.button10);
        button11 = findViewById(R.id.button11);
        buttonPlayAgain = findViewById(R.id.buttonPlayAgain);

    }

    // method used to pass from first big Button "GO!"
    public void goToPlay(View view){
        buttonGoPlay.setVisibility(View.INVISIBLE);
        myLinearLayout.setVisibility(View.VISIBLE);
        button00.setVisibility(View.VISIBLE);
        button01.setVisibility(View.VISIBLE);
        button10.setVisibility(View.VISIBLE);
        button11.setVisibility(View.VISIBLE);
        startGame();
    }


    public void startGame(){
        score = 0;
        plays = 0;
        startCountDown();
        generatePlay();
    }

    public void startCountDown(){
        CountDownTimer myCountDown = new CountDownTimer(31000 , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String textToDisplay = millisUntilFinished / 1000 + "s";
                textViewTime.setText(textToDisplay);
                Log.i("info" , "textToDisplay     :   " + textToDisplay);
            }
            @Override
            public void onFinish() {
                textViewDisplay.setVisibility(View.VISIBLE);
                buttonPlayAgain.setVisibility(View.VISIBLE);
                textViewDisplay.setText( "Your score: " + score+"/"+plays );
                //myGridLayout.setEnabled(false);
                button00.setEnabled(false);
                button01.setEnabled(false);
                button10.setEnabled(false);
                button11.setEnabled(false);
            }
        }.start()  ;
    }

    public void getAnswer(View view){

        textViewDisplay.setText("Won??");
        textViewDisplay.setVisibility(View.VISIBLE);

        String theTag = (String)view.getTag();
        String theTextOnButton = "";
        switch (theTag){
            case "1" : theTextOnButton = (String)button00.getText();
            break;
            case "2" : theTextOnButton = (String)button01.getText();
            break;
            case "3" : theTextOnButton = (String)button10.getText();
            break;
            case "4" : theTextOnButton = (String)button11.getText();
            break;
        }

        if (theTextOnButton.equals(String.valueOf(myResult))){
            score++;
            textViewDisplay.setText("Correct");
        }else {
            textViewDisplay.setText("Wrong");
        }
        plays++;
        String newScoreDisplay = score+"/"+plays;
        textViewScore.setText(newScoreDisplay);
        generatePlay();

    }

    public void playAgain(View view){
        textViewDisplay.setVisibility(View.INVISIBLE);
        buttonPlayAgain.setVisibility(View.INVISIBLE);
        button00.setEnabled(true);
        button01.setEnabled(true);
        button10.setEnabled(true);
        button11.setEnabled(true);
        startGame();
    }

}
