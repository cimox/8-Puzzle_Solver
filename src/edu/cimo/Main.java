package edu.cimo;

import edu.cimo.api.Heuristic;
import edu.cimo.api.Puzzle;

public class Main {

    public static void main(String[] args) {
        int tiles[][] = {{1, 2, 3}, {5, 4, 6}, {7, 0, 8}};
        Puzzle start = new Puzzle(tiles, 3, 3, null, 0);

        Heuristic h = new Heuristic();
        h.getHammingDistance(start, start);
    }
}
