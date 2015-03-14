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

    public int getManhattanDistance(Node node, Node goal) {
        int manhattanDist = 0;

        for (int i = 0; i < node.getN(); i++) {
            for (int j = 0; j < node.getM(); j ++) {
                if (node.getTiles()[i][j] != 0)
                    manhattanDist += getDistToCoordinates(new int[] {i,j}, getTileCoordinates(goal, node.getTiles()[i][j]));
            }
        }

        return manhattanDist;
    }

    private int getDistToCoordinates(int[] coordinatesNode, int[] coordinateGoal) {
        int dist = 0;

        dist = Math.abs(coordinateGoal[0] - coordinatesNode[0]) + Math.abs(coordinateGoal[1] - coordinatesNode[1]);
        return dist;
    }

    private int[] getTileCoordinates(Node goal, int tile) {
        int coordinates[] = new int[2];

        for (int i = 0; i < goal.getN(); i++) {
            for (int j = 0; j < goal.getM(); j++) {
                if (goal.getTiles()[i][j] == tile) {
                    coordinates[0] = i; coordinates[1] = j;
                    return coordinates;
                }
            }
        }

        return coordinates;
    }

    private boolean isTileInHisPosition(Node node, int row, int column, int tileNumber) {

        if (tileNumber <= ((row+1) * node.getN())) {
            if ((tileNumber - (row * node.getM())) == column+1)
                return true;
        }

        return false;
    }
}
