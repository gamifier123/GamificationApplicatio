package com.example.games;

/*
 * @Author Nicholas Leong        EDUV4551823
 * @Author Aarya Manowah         be.2023.q4t9k6
 * @Author Nyasha Masket        BE.2023.R3M0Y0
 * @Author Sakhile Lesedi Mnisi  BE.2022.j9f3j4
 * @Author Dominic Newton       EDUV4818782
 * @Author Kimberly Sean Sibanda EDUV4818746
 *
 * Supervisor: Stacey Byrne      Stacey.byrne@eduvos.com
 * */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Crossword extends AppCompatActivity {
    private GridLayout gridLayout;
    private RecyclerView recyclerViewAcrossClues;
    private RecyclerView recyclerViewDownClues;

    private final int numTsAndDs = 7;
    private List<TermsAndDefinitions> TsAndDs = new ArrayList<>();

    private List<String> terms = new ArrayList<>();
    private List<String> definitions = new ArrayList<>();

    private final EditText[][] cells = new EditText[10][10];
//    private TextView[][] numberLabels = new TextView[10][10];

    private final List<PlacedWord> placedWords = new ArrayList<>();
    private final int GRID_SIZE = 10;
    private final char[][] gridLogic = new char[GRID_SIZE][GRID_SIZE];
    private final int[][] clueNumbers = new int[GRID_SIZE][GRID_SIZE];

    private final List<Word> acrossWords = new ArrayList<>();
    private final List<Word> downWords = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crossword);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gridLayout = findViewById(R.id.gridLayoutCrossword);
        recyclerViewAcrossClues = findViewById(R.id.recyclerViewAcrossClues);
        recyclerViewDownClues = findViewById(R.id.recyclerViewDownClues);
        Button buttonCheck = findViewById(R.id.checkAnswerButton);

        buttonCheck.setOnClickListener(v -> checkAnswers());

        TermsAndDefinitions.loadDummyTsAndDs();
        setupCrosswordGrid();
        newLevel();

        ImageButton cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Crossword.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupCrosswordGrid() {                             //Found in themes.xml
        Context context = new ContextThemeWrapper(this, R.style.CrosswordCell);

        for (int i = 0; i < GRID_SIZE * GRID_SIZE; i++) {
            EditText cell = new EditText(context);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.width = 0;
            params.height = 0;
            cell.setLayoutParams(params);

            // Configure EditText for single uppercase letter
            cell.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
            cell.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1), new InputFilter.AllCaps()});
            cell.setGravity(Gravity.CENTER);

            gridLayout.addView(cell);

            int row = i / GRID_SIZE;
            int col = i % GRID_SIZE;
            cells[row][col] = cell;
        }
    }

    private void setHints() {
        recyclerViewAcrossClues.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDownClues.setLayoutManager(new LinearLayoutManager(this));

        List<String> acrossClues = new ArrayList<>();
        List<String> downClues = new ArrayList<>();

        for (Word word : acrossWords) {
            acrossClues.add(word.id + ". " + word.definition);
        }

        for (Word word : downWords) {
            downClues.add(word.id + ". " + word.definition);
        }

        recyclerViewAcrossClues.setAdapter(new ClueAdapter(acrossClues));
        recyclerViewDownClues.setAdapter(new ClueAdapter(downClues));
    }

    private void determineDownAndAcross() {
        placedWords.clear();
        acrossWords.clear();
        downWords.clear();

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                gridLogic[i][j] = '\0';
                clueNumbers[i][j] = 0;
            }
        }

        List<String> sortedTerms = new ArrayList<>(terms);
        sortedTerms.sort((s1, s2) -> s2.length() - s1.length());

        String firstWord = sortedTerms.get(0);
        int startRow = 1;
        int startCol = (GRID_SIZE - firstWord.length()) / 2;
        placeWord(firstWord, startRow, startCol, true);
        sortedTerms.remove(0);

        for (String wordToPlace : sortedTerms) {
            tryPlaceWithIntersection(wordToPlace);
        }

        assignClueNumbers();

        // Clear the visual grid (don't show answers)
        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                cells[r][c].setText("");
                cells[r][c].setEnabled(gridLogic[r][c] != '\0');
            }
        }
    }

    private void assignClueNumbers() {
        int clueNumber = 1;
        Map<String, Integer> wordToNumber = new HashMap<>();

        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                boolean isAcrossStart = false;
                boolean isDownStart = false;

                // Check if this is the start of an across word
                if (gridLogic[r][c] != '\0' && (c == 0 || gridLogic[r][c - 1] == '\0')) {
                    if (c + 1 < GRID_SIZE && gridLogic[r][c + 1] != '\0') {
                        isAcrossStart = true;
                    }
                }

                // Check if this is the start of a down word
                if (gridLogic[r][c] != '\0' && (r == 0 || gridLogic[r - 1][c] == '\0')) {
                    if (r + 1 < GRID_SIZE && gridLogic[r + 1][c] != '\0') {
                        isDownStart = true;
                    }
                }

                if (isAcrossStart || isDownStart) {
                    clueNumbers[r][c] = clueNumber;

                    // Find the word(s) starting at this position
                    for (PlacedWord pw : placedWords) {
                        if (pw.row == r && pw.col == c) {
                            wordToNumber.put(pw.word + "_" + pw.isAcross, clueNumber);
                        }
                    }

                    clueNumber++;
                }
            }
        }

        // Create Word objects with proper IDs
        for (PlacedWord pw : placedWords) {
            String key = pw.word + "_" + pw.isAcross;
            Integer number = wordToNumber.get(key);
            if (number != null) {
                int originalIndex = terms.indexOf(pw.word);
                if (originalIndex != -1) {
                    String definition = definitions.get(originalIndex);
                    Direction dir = pw.isAcross ? Direction.ACROSS : Direction.DOWN;
                    String id = number + (pw.isAcross ? "A" : "D");

                    Word word = new Word(id, pw.word, definition, pw.row, pw.col, dir);

                    if (pw.isAcross) {
                        acrossWords.add(word);
                    } else {
                        downWords.add(word);
                    }
                }
            }
        }

        // Sort words by number
        acrossWords.sort((w1, w2) -> {
            int num1 = Integer.parseInt(w1.id.substring(0, w1.id.length() - 1));
            int num2 = Integer.parseInt(w2.id.substring(0, w2.id.length() - 1));
            return num1 - num2;
        });

        downWords.sort((w1, w2) -> {
            int num1 = Integer.parseInt(w1.id.substring(0, w1.id.length() - 1));
            int num2 = Integer.parseInt(w2.id.substring(0, w2.id.length() - 1));
            return num1 - num2;
        });
    }

    private boolean tryPlaceWithIntersection(String word) {
        for (int i = 0; i < word.length(); i++) {
            char letterInWord = word.charAt(i);

            for (PlacedWord existingWord : placedWords) {
                for (int j = 0; j < existingWord.word.length(); j++) {
                    char letterOnGrid = existingWord.word.charAt(j);

                    if (letterInWord == letterOnGrid) {
                        int newRow, newCol;
                        boolean newIsAcross = !existingWord.isAcross;

                        if (existingWord.isAcross) {
                            newRow = existingWord.row - i;
                            newCol = existingWord.col + j;
                        } else {
                            newRow = existingWord.row + j;
                            newCol = existingWord.col - i;
                        }

                        if (newRow < 0 || newCol < 0){
                            continue; // Skip to the next potential placement
                        }

                        if (canPlaceWord(word, newRow, newCol, newIsAcross)) {
                            placeWord(word, newRow, newCol, newIsAcross);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean canPlaceWord(String word, int row, int col, boolean isAcross) {
        if (row < 0 || col < 0) {
            return false;
        }

        if ((isAcross && col + word.length() > GRID_SIZE) ||
                (!isAcross && row + word.length() > GRID_SIZE)) {
            return false;
        }

        // Check space before word (should be empty)
        if (isAcross && col > 0 && gridLogic[row][col - 1] != '\0') {
            return false;
        }
        if (!isAcross && row > 0 && gridLogic[row - 1][col] != '\0') {
            return false;
        }

        // Check space after word (should be empty)
        if (isAcross && col + word.length() < GRID_SIZE && gridLogic[row][col + word.length()] != '\0') {
            return false;
        }
        if (!isAcross && row + word.length() < GRID_SIZE && gridLogic[row + word.length()][col] != '\0') {
            return false;
        }

        for (int i = 0; i < word.length(); i++) {
            int r = isAcross ? row : row + i;
            int c = isAcross ? col + i : col;

            if (r >= GRID_SIZE || c >= GRID_SIZE) {
                return false;
            }

            char letterOnGrid = gridLogic[r][c];
            char newLetter = word.charAt(i);

            if (letterOnGrid != '\0' && letterOnGrid != newLetter) {
                return false;
            }

            // Check perpendicular neighbors to avoid creating unintended words
            if (letterOnGrid == '\0') {
                if (isAcross) {
                    // Check cells above and below
                    if ((r > 0 && gridLogic[r - 1][c] != '\0') ||
                            (r < GRID_SIZE - 1 && gridLogic[r + 1][c] != '\0')) {
                        return false;
                    }
                } else {
                    // Check cells left and right
                    if ((c > 0 && gridLogic[r][c - 1] != '\0') ||
                            (c < GRID_SIZE - 1 && gridLogic[r][c + 1] != '\0')) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void placeWord(String word, int row, int col, boolean isAcross) {
        for (int i = 0; i < word.length(); i++) {
            if (isAcross) {
                gridLogic[row][col + i] = word.charAt(i);
            } else {
                gridLogic[row + i][col] = word.charAt(i);
            }
        }
        placedWords.add(new PlacedWord(word, row, col, isAcross));
    }

    public void blackoutEmptySquares() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (gridLogic[row][col] == '\0') {
                    cells[row][col].setBackgroundColor(Color.BLACK);
                    cells[row][col].setEnabled(false);
                } else {
                    cells[row][col].setBackgroundColor(Color.WHITE);
                    // Add clue number if present
                    if (clueNumbers[row][col] > 0) {
                        cells[row][col].setHint(String.valueOf(clueNumbers[row][col]));
                    }
                }
            }
        }
    }

    private void checkAnswers() {
        boolean allCorrect = true;
        int correctCount = 0;
        int totalCells = 0;

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (gridLogic[row][col] != '\0') {
                    totalCells++;
                    String userInput = cells[row][col].getText().toString().toUpperCase();
                    String correctAnswer = String.valueOf(gridLogic[row][col]).toUpperCase();

                    if (userInput.equals(correctAnswer)) {
                        cells[row][col].setTextColor(Color.GREEN);
                        correctCount++;
                    } else {
                        cells[row][col].setTextColor(Color.RED);
                        allCorrect = false;
                    }
                }
            }
        }

        if (allCorrect) {
            Toast.makeText(this, "Congratulations! All answers are correct!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Score: " + correctCount + "/" + totalCells + " correct", Toast.LENGTH_LONG).show();
        }
    }

    private void getTsAndDs() {
        if (TsAndDs == null) {
            TsAndDs = new ArrayList<>();
        }
        TsAndDs.clear();
        TermsAndDefinitions termsAndDefinitionsTemp;
        while (TsAndDs.size() < numTsAndDs) {
            termsAndDefinitionsTemp = TermsAndDefinitions.getTermAndDefinition(
                    TermsAndDefinitions.generateRandomIndex());
            if (!TsAndDs.contains(termsAndDefinitionsTemp)) {
                TsAndDs.add(termsAndDefinitionsTemp);
            }
        }
    }

    private void setTsAndDs() {
        if (terms == null) {
            terms = new ArrayList<>();
        }
        if (definitions == null) {
            definitions = new ArrayList<>();
        }
        terms.clear();
        definitions.clear();
        for (TermsAndDefinitions td : TsAndDs) {
            if (td != null) {
                terms.add(td.getTerm().toUpperCase());
                definitions.add(td.getDefinition());
            }
        }
    }

    private void newLevel() {
        if (TsAndDs == null) {
            TsAndDs = new ArrayList<>();
        }
        if (terms == null) {
            terms = new ArrayList<>();
        }
        if (definitions == null) {
            definitions = new ArrayList<>();
        }

        TsAndDs.clear();
        terms.clear();
        definitions.clear();
        getTsAndDs();
        setTsAndDs();
        determineDownAndAcross();
        setHints();
        blackoutEmptySquares();
    }
}

class PlacedWord {
    String word;
    int row;
    int col;
    boolean isAcross;

    PlacedWord(String word, int row, int col, boolean isAcross) {
        this.word = word;
        this.row = row;
        this.col = col;
        this.isAcross = isAcross;
    }
}