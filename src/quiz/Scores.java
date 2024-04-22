/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quiz;

/**
 *
 * @author Tayla Rechichi
 */
class Scores {
    public static final int NUMBER_OF_SCORES_ATTRIBUTES = 4;
    public static final int INDEX_OF_NAME = 0;
    public static final int INDEX_OF_SCORE = 1;
    public static final int INDEX_OF_DATE = 2;
    public static final int INDEX_OF_TIMER = 3;
    private String name;
    private String score;
    private String date;
    private String timer;

    public Scores(String name, String score, String date, String timer) {
        this.name = name;
        this.score = score;
        this.date = date;
        this.timer = timer;
    }

    // getters
    public String getName() { return name; }
    public String getScore() { return score; }
    public String getDate() { return date; }
    public String getTimer() { return timer; }
}

