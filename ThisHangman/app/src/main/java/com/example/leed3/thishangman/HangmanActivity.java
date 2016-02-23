package com.example.leed3.thishangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import java.util.*;

public class HangmanActivity extends AppCompatActivity {

    private Integer totalGuesses = 0;
    private Integer wrongGuesses = 0;
    private String rightWord;
    private boolean won;
    private boolean gameOver;
    private String currentWord = "";

    public void clickButton(View view) {

        Button b = (Button) view;
        String val = b.getText().toString();
        b.setVisibility(View.INVISIBLE);
        clicked(val);
    }

    public void clicked(String val) {

        if (val.equals("RESET")) {
            displayWord();
        }

        else if (won) {
            return;
        }

        else if (gameOver) {
            return;
        }

        else {
            guessedLetter(val);
        }
        //first choose a word from a random list
    }

    public String generateWord(){
        String [] words = {"handler","against","horizon","chops","junkyard","amoeba","academy","roast",
                "countryside","children","strange","best","drumbeat","amnesiac","chant","amphibian","smuggler","fetish"};
        Random r = new Random();
        int index = r.nextInt(words.length);
        return words[index];
    }

    private void displayWord() {

        setContentView(R.layout.activity_hangman);
        rightWord = generateWord();
        currentWord = "";
        won = false;
        gameOver = false;
        TextView screen = (TextView) findViewById(R.id.hangmanWord);
        totalGuesses = 0;
        wrongGuesses = 0;
        Integer length = 0;

        for (int i = 0; i < rightWord.length(); i++) {
            currentWord = currentWord + "-";
            length++;
        }

        screen.setText(currentWord + " consists of "+ length.toString() + " letters");
    }

    private void guessedLetter(String val) {

        val = val.toLowerCase();
        String scrn = "";
        TextView screen = (TextView) findViewById(R.id.hangmanWord);
        boolean letter = false;
        totalGuesses += 1;

        if (checkGuessedLetter(val) && !won) {

            letter = true;

            for (int i = 0; i < rightWord.length(); i++) {

                if (rightWord.substring(i,i+1).equals(val))
                    scrn = scrn + val;

                else if (letter && rightWord.substring(i,i+1).equals(currentWord.substring(i,i+1)))
                    scrn = scrn + currentWord.substring(i,i+1);

                else
                    scrn = scrn + "-";
            }

            currentWord = scrn;
        }

        if (!checkGuessedLetter(val)) {
            scrn = currentWord;
            wrongGuesses += 1;
        }

        if (currentWord.equals(rightWord)) {
            won = true;
            gameOver = true;
        }

        if (won) {
            screen.setText("you WIN, the word was: " + rightWord + " you used " + totalGuesses.toString()
                    + " guesses");
        }

        else if (wrongGuesses == 11 && !won) {
            screen.setText("you lost, the word was: " + rightWord + " you used " + totalGuesses.toString()
                + " guesses");

            gameOver = true;
        }

        else {
            screen.setText(scrn + " used " + wrongGuesses.toString() + " of 11 guesses");
        }
    }

    private boolean checkGuessedLetter(String c) {
        if (rightWord.contains(c)) {
            return true;
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        displayWord();
    }
}