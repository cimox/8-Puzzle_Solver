package edu.cimo.api;


/**
 * Created by cimo on 10/03/15.
 */
public class Puzzle {
    private int N, M; // NxM puzzle size
    private int tiles[][]; // ((1 2 3) (4 5 6) (7 8 0))
    private  Puzzle precedessor;
    private int lastUsedOperator; // 1 = Down, 2 = Up, 3 = Right, 4 = Left, 0 = none

    public Puzzle(int[][] tiles, int N, int M) {
        this.tiles = tiles;
        this.N = N;
        this.M = M;
    }
    public Puzzle(int[][] tiles, int N, int M, Puzzle precedessor, int lastUsedOperator) {
        this.tiles = tiles;
        this.precedessor = precedessor;
        this.lastUsedOperator = lastUsedOperator;
        this.N = N;
        this.M = M;
    }

    public int getN() {
        return N;
    }

    public int getM() {
        return M;
    }

    public int[][] getTiles() {
        return tiles;
    }
}
