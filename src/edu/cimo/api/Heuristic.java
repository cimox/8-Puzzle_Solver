package edu.cimo.api;

/**
 * Created by cimo on 10/03/15.
 */
public class Heuristic {
    public Heuristic() {

    }

    public int getHammingDistance(Node node, Node goal) {
        int cnt = 0;

        for (int i = 0; i < node.getN(); i++) {
            for (int j = 0; j < node.getM(); j++) {
                if (!isTileInHisPosition(node, i, j, node.getTiles()[i][j])) {
                    cnt++;
                }
            }
        }

        return cnt;
    }

    public int getManhattanDistance(Node node) {



        return 0;
    }

    private boolean isTileInHisPosition(Node node, int row, int column, int tileNumber) {

        if (tileNumber <= ((row+1) * node.getN())) {
            if ((tileNumber - (row * node.getM())) == column+1)
                return true;
        }

        return false;
    }
}
