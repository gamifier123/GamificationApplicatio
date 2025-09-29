package com.example.games;

public class PointsTracker {
    public static final int POINTS_FOR_CORRECT_ANSWER = 10;
    public static final int POINTS_FOR_INCORRECT_ANSWER = -5;
    public static int pointsForCurrentGame = 0;

    public static void addPoints(){
        pointsForCurrentGame += POINTS_FOR_CORRECT_ANSWER;
    }

    public static void removePoints(){
        pointsForCurrentGame += POINTS_FOR_INCORRECT_ANSWER;
    }
}
