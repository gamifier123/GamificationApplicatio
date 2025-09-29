package com.example.games;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/*The goal of this game is for the user to match a given term with a given definition. This game has five rounds.*/

public class MatchingGame extends AppCompatActivity {
    private final List<TermsAndDefinitions> TsAndDs = new ArrayList<>();//Will store TermsAndDefinitions objects
    private final Map<String, String> tdPair = new HashMap<>();//Will map terms to definitions

    /*
     * The variable arrays of 1 are the only way to call values in button clicks
     * */
    private final boolean[] defIsSelected = {false};//stores if a definition button has been selected
    private final boolean[] termIsSelected = {false};//stores if a definition button has been selected

    final Button[] selectedDefinitionButton = {null};//Stores which definition button is currently selected
    final Button[] selectedTermButton = {null};//Stores which term button is currently selected

    private Button def1;
    private Button def2;
    private Button def3;
    private Button def4;
    private Button def5;

    private Button term1;
    private Button term2;
    private Button term3;
    private Button term4;
    private Button term5;

    private int numCorrectAnswers = 0;
    private int numLevelsSofar = 0;

    private String selectedDefinitionText = "";
    private String selectedTermText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_matching_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        def1 = findViewById(R.id.Def1);
        def2 = findViewById(R.id.Def2);
        def3 = findViewById(R.id.Def3);
        def4 = findViewById(R.id.Def4);
        def5 = findViewById(R.id.Def5);

        term1 = findViewById(R.id.Term1);
        term2 = findViewById(R.id.Term2);
        term3 = findViewById(R.id.Term3);
        term4 = findViewById(R.id.Term4);
        term5 = findViewById(R.id.Term5);

        newLevel();//sets new game for user

        /*
        * For each button's setOnClickListener:
        * 1) If the button is not currently selected, mark the button as selected by:
        *       changing the background of the button,
        *       assigning definition[0] or term[0] the value of the clicked button,
        *       marking defIsSelected[0] or termIsSelected[0] to true (i.e. A term and/or definition is selected),
        *       and selectedDefinitionButton[0] of selectedTermButton[0] is given the value of the respective clicked button.
        *
        * 2) When a button is clicked, the system checks if both, a term and definition, is selected and stored (using the method "termAndDefSelected()")
        *   2.1) If both have been selected then the system checks of the answer is correct using checkPair(term[0], definition[0])
        *   2.2.1) If the pair match each other, then the user is given 10 points and an animation plays to show that the term and definition match.
        *   2.2.2) If they do not match then the user loses 5 points and  an animation plays to show that the term and definition do not match.
        *
        * 3) If the same button is clicked again it is deselected by:
        *       changing the background of the button back to the default,
        *       resetting the value of definition[0] or term[0],
        *       resetting the value of defIsSelected[0] or termIsSelected[0],
        *       resetting the value of selectedDefinitionButton[0] or selectedTermButton[0]
        * */
        def1.setOnClickListener(v -> onDefinitionButtonClick(def1, def1.getText().toString().trim()));
        def2.setOnClickListener(v -> onDefinitionButtonClick(def2, def2.getText().toString().trim()));
        def3.setOnClickListener(v -> onDefinitionButtonClick(def3, def3.getText().toString().trim()));
        def4.setOnClickListener(v -> onDefinitionButtonClick(def4, def4.getText().toString().trim()));
        def5.setOnClickListener(v -> onDefinitionButtonClick(def5, def5.getText().toString().trim()));

        term1.setOnClickListener(v -> onTermButtonClick(term1, term1.getText().toString().trim()));
        term2.setOnClickListener(v -> onTermButtonClick(term2, term2.getText().toString().trim()));
        term3.setOnClickListener(v -> onTermButtonClick(term3, term3.getText().toString().trim()));
        term4.setOnClickListener(v -> onTermButtonClick(term4, term4.getText().toString().trim()));
        term5.setOnClickListener(v -> onTermButtonClick(term5, term5.getText().toString().trim()));

        ImageButton cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchingGame.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
    * A single, reusable method to handle clicks on any definition button
    * 1) The user clicks the same button again to deselect it
    * 2) The user clicked a different definition button while one was already selected
    * 3) No definition button was selected, so select this one is selected
    *
    * 4) Check for a match after any selection change
    * */
    private void onDefinitionButtonClick(Button clickedButton, String definitionText) {

        if (clickedButton == selectedDefinitionButton[0]) {
            //1)
            clickedButton.setBackgroundResource(R.drawable.mg_definition_button_format);
            selectedDefinitionText = "";
            defIsSelected[0] = false;
            selectedDefinitionButton[0] = null;
        } else if (defIsSelected[0]) {
            //2)
            // Deselect the old button first
            selectedDefinitionButton[0].setBackgroundResource(R.drawable.mg_definition_button_format);

            // Now, select the new button
            clickedButton.setBackgroundResource(R.drawable.mg_definition_selected_format);
            selectedDefinitionText = definitionText;
            selectedDefinitionButton[0] = clickedButton;
        } else {
            //3)
            clickedButton.setBackgroundResource(R.drawable.mg_definition_selected_format);
            selectedDefinitionText = definitionText;
            defIsSelected[0] = true;
            selectedDefinitionButton[0] = clickedButton;
        }

        //4)
        if (termAndDefSelected()) {
            if (checkPair(selectedTermText, selectedDefinitionText)) {
                PointsTracker.addPoints();
                // Pass the actual button objects directly
                animateCorrectAnswerColour(selectedDefinitionButton[0], selectedTermButton[0]);
            } else {
                PointsTracker.removePoints();
                // Pass the actual button objects directly
                animateWrongAnswerColour(selectedDefinitionButton[0], selectedTermButton[0]);

                selectedDefinitionText = "";
                selectedTermText = "";
                defIsSelected[0] = false;
                termIsSelected[0] = false;
                selectedDefinitionButton[0] = null;
                selectedTermButton[0] = null;
            }
        }
    }

    /*
    * A single, reusable method to handle clicks on any term button
    * 1) The user clicks the same button again to deselect it
    * 2) The user clicked a different term button while one was already selected
    * 3) No term button was selected, so select this one is selected
    *
    * 4) Check for a match after any selection change
    * */
    private void onTermButtonClick(Button clickedButton, String termText) {
        if (clickedButton == selectedTermButton[0]) {
            //1)
            clickedButton.setBackgroundResource(R.drawable.mg_term_button_format);
            selectedTermText = "";
            termIsSelected[0] = false;
            selectedTermButton[0] = null;
        } else if (termIsSelected[0]) {
            //2)
            selectedTermButton[0].setBackgroundResource(R.drawable.mg_term_button_format);
            clickedButton.setBackgroundResource(R.drawable.mg_term_selected_format);
            selectedTermText = termText;
            selectedTermButton[0] = clickedButton;
        } else {
            //3)
            clickedButton.setBackgroundResource(R.drawable.mg_term_selected_format);
            selectedTermText = termText;
            termIsSelected[0] = true;
            selectedTermButton[0] = clickedButton;
        }

        //4)
        if (termAndDefSelected()) {
            if (checkPair(selectedTermText, selectedDefinitionText)) {
                PointsTracker.addPoints();
                animateCorrectAnswerColour(selectedDefinitionButton[0], selectedTermButton[0]);
            } else {
                PointsTracker.removePoints();
                animateWrongAnswerColour(selectedDefinitionButton[0], selectedTermButton[0]);

                selectedDefinitionText = "";
                selectedTermText = "";
                defIsSelected[0] = false;
                termIsSelected[0] = false;
                selectedDefinitionButton[0] = null;
                selectedTermButton[0] = null;
            }
        }
    }

    /*
     * Gets five unique, random terms and definitions objects from the TermsAndDefinitions class.
     * It uses a while loop to continue adding items until the list has 5 unique entries.
     * */
    private void getTermsAndDefinitions(){
        TsAndDs.clear();

        TermsAndDefinitions termsAndDefinitionsTemp;
        while (TsAndDs.size() < 5){
            termsAndDefinitionsTemp = TermsAndDefinitions.getTermAndDefinition(TermsAndDefinitions.generateRandomIndex());
            if (!TsAndDs.contains(termsAndDefinitionsTemp)){
                TsAndDs.add(termsAndDefinitionsTemp);
            }
        }

    }

    /*
    * Takes the terms and conditions from the TermsAndConditions list and puts them into a HashMap,
    *   where the term is the key, and the definition is the value
    * */
    private void putToMap(){
        for (int i = 0; i < TsAndDs.size(); i++){
            tdPair.put(TsAndDs.get(i).getTerm(), TsAndDs.get(i).getDefinition());
        }
    }

    /*
    * Sets the text of the buttons to the definitions and terms
    * Collections.shuffle() randomises the order of the terms and definitions
    * */
    private void setButtonText(){
        Integer[] randomisedDefinitionArray = {0, 1, 2, 3, 4};
        Integer[] randomisedTermArray = {0, 1, 2, 3, 4};

        List<Integer> randomisedDefinitionList = Arrays.asList(randomisedDefinitionArray);
        List<Integer> randomisedTermList = Arrays.asList(randomisedTermArray);

        Collections.shuffle(randomisedDefinitionList);
        Collections.shuffle(randomisedTermList);

        def1.setText(TsAndDs.get(randomisedDefinitionList.get(0)).definition);
        def2.setText(TsAndDs.get(randomisedDefinitionList.get(1)).definition);
        def3.setText(TsAndDs.get(randomisedDefinitionList.get(2)).definition);
        def4.setText(TsAndDs.get(randomisedDefinitionList.get(3)).definition);
        def5.setText(TsAndDs.get(randomisedDefinitionList.get(4)).definition);

        term1.setText(TsAndDs.get(randomisedTermList.get(0)).term);
        term2.setText(TsAndDs.get(randomisedTermList.get(1)).term);
        term3.setText(TsAndDs.get(randomisedTermList.get(2)).term);
        term4.setText(TsAndDs.get(randomisedTermList.get(3)).term);
        term5.setText(TsAndDs.get(randomisedTermList.get(4)).term);
    }

    /*
    * Checks if a term and a definition is selected. This will be used to check if they match.
    * */
    private boolean termAndDefSelected(){
        return termIsSelected[0] && defIsSelected[0];
    }

    /*
    * Checks if the selected term and definition is correct
    * */
    private boolean checkPair(String term, String definition){
        if (tdPair.containsKey(term)){
            if (Objects.equals(tdPair.get(term), definition)){
                numCorrectAnswers++;
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /*
    * Creates a new level for the user:
    * 1) Checks the number of levels played so far:
    *   1.1) If the number of levels equals 4 (i.e. 5 levels have been played) then the game ends
    *   1.2) If not:
    *       1.2.1) The number of correct answers is set to 0
    *       1.2.2) The number of levels so far is incremented by 1
    *       1.2.3) New terms and definitions are retrieved and set
    *       1.2.4) Boolean variables defIsSelected[0] and termIsSelected[0] are set to false
    * */
    private void newLevel(){
        numCorrectAnswers = 0;//1.2.1)
        numLevelsSofar++;//1.2.2)

        getTermsAndDefinitions();
        putToMap();
        setButtonText();//1.2.3)
        resetButtonsForNewGame();

        defIsSelected[0] = false;
        termIsSelected[0] = false;//1.2.4)
        selectedDefinitionButton[0] = null;
        selectedTermButton[0] = null;
        selectedDefinitionText = "";
        selectedTermText = "";

        Toast.makeText(this, "New Level! Current level is " + (numLevelsSofar), Toast.LENGTH_SHORT).show();

        if (numLevelsSofar > 5){//1)
            endGame();//1.1)
            Toast.makeText(this, "End of game!", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * The selected buttons will show red before going back to their original colour
     * This is shown when the user's answer is wrong
     * */
    private void animateWrongAnswerColour(Button definitionButton, Button termButton) {
        definitionButton.setBackgroundResource(R.drawable.mg_definition_button_format);
        termButton.setBackgroundResource(R.drawable.mg_term_button_format);

        //Get the drawable objects from buttons
        GradientDrawable defDrawable = (GradientDrawable) definitionButton.getBackground();
        GradientDrawable termDrawable = (GradientDrawable) termButton.getBackground();

        //Define the colors we will animate between
        // Get the "unselected" colors from colors.xml file.
        int originalDefColor = getResources().getColor(R.color.definition_button_colour, getTheme());
        int originalTermColor = getResources().getColor(R.color.term_button_colour, getTheme());
        int redColor = Color.parseColor("#ee020b");

        //Animate from the current color to red
        ValueAnimator defToRed = ValueAnimator.ofObject(new ArgbEvaluator(), originalDefColor, redColor);
        defToRed.setDuration(500);
        defToRed.addUpdateListener(animation -> defDrawable.setColor((int) animation.getAnimatedValue()));

        ValueAnimator termToRed = ValueAnimator.ofObject(new ArgbEvaluator(), originalTermColor, redColor);
        termToRed.setDuration(500);
        termToRed.addUpdateListener(animation -> termDrawable.setColor((int) animation.getAnimatedValue()));

        //After a delay, animate from red back to the original colors
        int fadeBackDelay = 250;

        definitionButton.postDelayed(() -> {
            ValueAnimator reverseDefAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), redColor, originalDefColor);
            reverseDefAnimation.setDuration(500);
            reverseDefAnimation.addUpdateListener(animation -> defDrawable.setColor((int) animation.getAnimatedValue()));
            reverseDefAnimation.start();
        }, fadeBackDelay);

        termButton.postDelayed(() -> {
            ValueAnimator reverseTermAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), redColor, originalTermColor);
            reverseTermAnimation.setDuration(500);
            reverseTermAnimation.addUpdateListener(animation -> termDrawable.setColor((int) animation.getAnimatedValue()));
            reverseTermAnimation.start();
        }, fadeBackDelay);

        //Start the animations to red immediately
        defToRed.start();
        termToRed.start();

        definitionButton.setSelected(false);
        termButton.setSelected(false);
        selectedDefinitionText = "";
        selectedTermText = "";
        defIsSelected[0] = false;
        termIsSelected[0] = false;
        selectedDefinitionButton[0] = null;
        selectedTermButton[0] = null;
    }


    /*
     * The selected buttons will show green before fading to grey, and being set to disabled
     * This is shown when the user's answer is correct
     * */
    private void animateCorrectAnswerColour(Button definitionButton, Button termButton) {
        //Get the drawable objects and define colors
        GradientDrawable defDrawable = (GradientDrawable) definitionButton.getBackground();
        GradientDrawable termDrawable = (GradientDrawable) termButton.getBackground();

        int greenColor = Color.parseColor("#71e73a");
        int greyColor = Color.parseColor("#808080");

        // Get the "selected" colors from your colors.xml file to start the animation from.
        int selectedDefColor = getResources().getColor(R.color.definition_button_colour, getTheme());
        int selectedTermColor = getResources().getColor(R.color.term_button_colour, getTheme());

        //Animate from selected color to green
        ValueAnimator defToGreen = ValueAnimator.ofObject(new ArgbEvaluator(), selectedDefColor, greenColor);
        defToGreen.setDuration(500);
        defToGreen.addUpdateListener(animation -> defDrawable.setColor((int) animation.getAnimatedValue()));

        ValueAnimator termToGreen = ValueAnimator.ofObject(new ArgbEvaluator(), selectedTermColor, greenColor);
        termToGreen.setDuration(500);
        termToGreen.addUpdateListener(animation -> termDrawable.setColor((int) animation.getAnimatedValue()));

        //Turn buttons to grey after a delay
        int fadeToGreyDelay = 250;

        definitionButton.postDelayed(() -> {
            ValueAnimator defToGrey = ValueAnimator.ofObject(new ArgbEvaluator(), greenColor, greyColor);
            defToGrey.setDuration(500);
            defToGrey.addUpdateListener(animation -> defDrawable.setColor((int) animation.getAnimatedValue()));

            defToGrey.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    definitionButton.setEnabled(false);

                    if (isEndOfLevel()){
                        newLevel();
                    }else{
                        definitionButton.setEnabled(false);
                        defIsSelected[0] = false;
                        selectedDefinitionButton[0] = null;
                    }
                }
            });
            defToGrey.start();
        }, fadeToGreyDelay);

        termButton.postDelayed(() -> {
            ValueAnimator termToGrey = ValueAnimator.ofObject(new ArgbEvaluator(), greenColor, greyColor);
            termToGrey.setDuration(500);
            termToGrey.addUpdateListener(animation -> termDrawable.setColor((int) animation.getAnimatedValue()));

            termToGrey.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    termButton.setEnabled(false);

                    if (isEndOfLevel()){
                        newLevel();
                    }else{
                        termButton.setEnabled(false);
                        termIsSelected[0] = false;
                        selectedTermButton[0] = null;
                    }
                }
            });
            termToGrey.start();
        }, fadeToGreyDelay);

        //Start the animations to green immediately
        defToGreen.start();
        termToGreen.start();

        definitionButton.setSelected(false);
        termButton.setSelected(false);
        selectedDefinitionText = "";
        selectedTermText = "";
        defIsSelected[0] = false;
        termIsSelected[0] = false;
        selectedDefinitionButton[0] = null;
        selectedTermButton[0] = null;
    }

    /*
    * Resets buttons so that they can be used again
    * */
    private void resetButtonsForNewGame(){
        def1.setBackgroundResource(R.drawable.mg_definition_button_format);
        def2.setBackgroundResource(R.drawable.mg_definition_button_format);
        def3.setBackgroundResource(R.drawable.mg_definition_button_format);
        def4.setBackgroundResource(R.drawable.mg_definition_button_format);
        def5.setBackgroundResource(R.drawable.mg_definition_button_format);

        term1.setBackgroundResource(R.drawable.mg_term_button_format);
        term2.setBackgroundResource(R.drawable.mg_term_button_format);
        term3.setBackgroundResource(R.drawable.mg_term_button_format);
        term4.setBackgroundResource(R.drawable.mg_term_button_format);
        term5.setBackgroundResource(R.drawable.mg_term_button_format);

        def1.setEnabled(true);
        def2.setEnabled(true);
        def3.setEnabled(true);
        def4.setEnabled(true);
        def5.setEnabled(true);

        term1.setEnabled(true);
        term2.setEnabled(true);
        term3.setEnabled(true);
        term4.setEnabled(true);
        term5.setEnabled(true);
    }

    /*
    * If 5 levels have been played, then the game ends.
    * The points are tallied up and the user is taken to the post-game screen
    * */
    private void endGame(){

    }

    /*
    * Checks if 5 correct answers have been given. If so, it will return true
    * */
    private boolean isEndOfLevel(){
        return numCorrectAnswers == 5;
    }


}