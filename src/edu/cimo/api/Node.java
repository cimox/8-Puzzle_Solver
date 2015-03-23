package edu.cimo.api;


import java.awt.*;
import java.io.PrintWriter;

/**
 * Created by cimo on 10/03/15.
 */
public class Node {
    private int N, M; // NxM puzzle size
    private int tiles[][]; // ((1 2 3) (4 5 6) (7 8 0))
    private Node predecessor;
    private int lastUsedOperator; // 1 = Down, 2 = Up, 3 = Right, 4 = Left, 0 = none
    private int h;

    public Node(int[][] tiles, int N, int M) {
        this.tiles = tiles;
        this.N = N;
        this.M = M;
    }
    public Node(int[][] tiles, int N, int M, Node predecessor, int lastUsedOperator) {
        this.tiles = tiles;
        this.predecessor = predecessor;
        this.lastUsedOperator = lastUsedOperator;
        this.N = N;
        this.M = M;
    }
    /**
     * Copy constructor
     * @attribute object to copy.
     * @return copy of parameter object.
     */
    public Node(Node copy, Node predecessor, int lastUsedOperator) {
        this.N = new Integer(copy.getN());
        this.M = new Integer(copy.getM());
        int tiles[][] = new int[this.N][this.M];
        for (int i = 0; i < this.N; i++)
            for (int j = 0; j < this.M; j++)
                tiles[i][j] = copy.getTiles()[i][j];
        this.tiles = tiles;
        this.predecessor = predecessor;
        this.lastUsedOperator = lastUsedOperator;

    }

    public Node getPredecessor() {
        return this.predecessor;
    }

    public int getLastUsedOperator() {
        return this.lastUsedOperator;
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

    public String getTilesAsString() {
        StringBuilder tiles = new StringBuilder();
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++) {
                tiles.append(Integer.toString(this.tiles[i][j]));
            }
        return tiles.toString();
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void printFancyTiles() {
        if (lastUsedOperator == 1) System.out.println("DOWN");
        else if (lastUsedOperator == 2) System.out.println("UP");
        else if (lastUsedOperator == 3) System.out.println("RIGHT");
        else if (lastUsedOperator == 4) System.out.println("LEFT");
        else System.out.println("none");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(tiles[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("-----");
    }

    public void printFancyTilesToFile(PrintWriter writer) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                writer.print(tiles[i][j] + " ");
            }
            writer.println();
        }
        if (lastUsedOperator == 1) writer.println("DOWN");
        else if (lastUsedOperator == 2) writer.println("UP");
        else if (lastUsedOperator == 3) writer.println("RIGHT");
        else if (lastUsedOperator == 4) writer.println("LEFT");
        else writer.println("none");
        writer.println("-----");
    }

    private int[] getEmptyTilePosition() {
        int ret[] = new int[2];
        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < this.M; j++) {
                if (tiles[i][j] == 0) {
                    ret[0] = i; ret[1] = j;
                    return ret;
                }
            }
        }
        return ret;
    }

    public int[] getPossibleMoves() {
        /**
         * 1 = Down, 2 = Up, 3 = Right, 4 = Left, 0 = none
         * NODE - example
         * {  0  1  2
         * 0 {1, 2, 3},
         * 1 {5, 4, 6},
         * 2 {7, 0, 8}
         * }
         */
        int moves[] = {0};
        int pos[] = getEmptyTilePosition();

        if (pos[0] == 1 && pos[1] == 1) { // Empty tile is in the middle, all moves possible.
            moves = new int[4];
            for (int i = 0; i < 4; i++)
                moves[i] = i+1;
            return moves;
        }
        // First row
        else if (pos[0] == 0 && pos[1] == 0) { // Top-left corner, 1. row and 1.column. RIGHT, DOWN
            moves = new int[2];
            moves[0] = 1; moves[1] = 3;
            return moves;
        }
        else if (pos[0] == 0 && pos[1] == 1) { // 1. row and 2. column, LEFT, DOWN, RIGHT
            moves = new int[3];
            moves[0] = 4; moves[1] = 1; moves[2] = 3;
            return moves;
        }
        else if (pos[0] == 0 && pos[1] == 2) { // Top-right corner, 1. row and 3.column. LEFT, DOWN
            moves = new int[2];
            moves[0] = 4; moves[1] = 1;
            return moves;
        }
        // Second row
        else if (pos[0] == 1 && pos[1] == 0) { // 2. row and 1.column. TOP, DOWN, RIGHT
            moves = new int[3];
            moves[0] = 2; moves[1] = 1; moves[2] = 3;
            return moves;
        }
        else if (pos[0] == 1 && pos[1] == 2) { // 2. row and 3.column. TOP, DOWN, LEFT
            moves = new int[3];
            moves[0] = 2; moves[1] = 1; moves[2] = 4;
            return moves;
        }
        // Third row
        else if (pos[0] == 2 && pos[1] == 0) { // Bottom-left corner, 1. row and 1.column. RIGHT, TOP
            moves = new int[2];
            moves[0] = 2; moves[1] = 3;
            return moves;
        }
        else if (pos[0] == 2 && pos[1] == 1) { // 1. row and 2. column, LEFT, TOP, RIGHT
            moves = new int[3];
            moves[0] = 4; moves[1] = 2; moves[2] = 3;
            return moves;
        }
        else if (pos[0] == 2 && pos[1] == 2) { // Bottom-right corner, 1. row and 3.column. LEFT, TOP
            moves = new int[2];
            moves[0] = 4; moves[1] = 2;
            return moves;
        }

        return moves;
    }

    public int doMove(int moveID) {
        // 1 = Down, 2 = Up, 3 = Right, 4 = Left, 0 = none
        int pos[] = getEmptyTilePosition();
        if (moveID == 1) {
            moveDown();
            return tiles[pos[0]][pos[1]];
        }
        else if (moveID == 2) {
            moveUp();
            return tiles[pos[0]][pos[1]];
        }
        else if (moveID == 3) {
            moveRight();
            return tiles[pos[0]][pos[1]];
        }
        else if (moveID == 4) {
            moveLeft();
            return tiles[pos[0]][pos[1]];
        }
        else return 0; // none
    }

    private void moveDown() {
        int pos[] = getEmptyTilePosition();
        tiles[pos[0]][pos[1]] = tiles[pos[0]+1][pos[1]];
        tiles[pos[0]+1][pos[1]] = 0;
    }

    private void moveUp() {
        int pos[] = getEmptyTilePosition();
        tiles[pos[0]][pos[1]] = tiles[pos[0]-1][pos[1]];
        tiles[pos[0]-1][pos[1]] = 0;
    }

    private void moveRight() {
        int pos[] = getEmptyTilePosition();
        tiles[pos[0]][pos[1]] = tiles[pos[0]][pos[1]+1];
        tiles[pos[0]][pos[1]+1] = 0;
    }

    private void moveLeft() {
        int pos[] = getEmptyTilePosition();
        tiles[pos[0]][pos[1]] = tiles[pos[0]][pos[1]-1];
        tiles[pos[0]][pos[1]-1] = 0;
    }

    public boolean compareNodes(Node goal) {
        int goalTiles[][] = goal.getTiles();
        int cnt = N*M;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (tiles[i][j] == goalTiles[i][j]) {
                    cnt--;
                    if (cnt == 0) return true;
                }
                else return false;
            }
        }

        return false;
    }

    public boolean isSolvable() {
        int invCount = 0;
        int[] tiles1D = new int[tiles.length * N];

        for (int i = 0; i < N; i++)
            System.arraycopy(tiles[i], 0, tiles1D, N*i, tiles.length);

        for (int i = 0; i < N*M; i++) {
            for (int j = i+1; j < N*M; j++) {
                if (tiles1D[i] > tiles1D[j] && tiles1D[i] != 0) {
                    invCount++;
                }
            }
        }
        if (invCount % 2 == 0) return true;

        return false;
    }
}
