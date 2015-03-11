package edu.cimo.api;

/**
 * Created by cimo on 10/03/15.
 */
public class Heuristic {
    private int cost;

    public Heuristic() {

    }

    public int getHammingDistance(Puzzle puzzle, Puzzle goal) {
        int cnt = 0;

        for (int i = 0; i < puzzle.getN(); i++) {
            for (int j = 0; j < puzzle.getM(); j++) {
                if (!isTileInHisPosition(puzzle, i, j, puzzle.getTiles()[i][j])) {
                  cnt++;
                }
            }
        }

        return cnt;
    }

    private boolean isTileInHisPosition(Puzzle puzzle, int row, int column, int tileNumber) {

        if (tileNumber <= ((row+1) * puzzle.getN())) {
            if ((tileNumber - (row * puzzle.getM())) == column+1)
                return true;
        }

        return false;
    }
}
