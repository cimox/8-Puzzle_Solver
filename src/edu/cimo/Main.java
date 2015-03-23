package edu.cimo;

import com.sun.tools.javac.util.ArrayUtils;
import edu.cimo.api.Heuristic;
import edu.cimo.api.Node;
import edu.cimo.api.StatesGraph;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Vector;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        int tiles[][] = {{2, 6, 0}, {1, 4, 3}, {8, 7, 5}};
        int tilesGoal[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Node goal = new Node(tilesGoal, 3, 3, null, 0);
        PrintWriter writer;
        PrintWriter allTestsWriter = new PrintWriter("/tmp/manhattan/_allTests.txt", "UTF-8");


        for (int i = 0; i < 100; i++) { // 100 tests
            System.out.println("\nRunning test: " + (i+1));
            allTestsWriter.println("\nRunning test: " + (i+1));
            Node start = new Node(generateRandomTiles(3, 3), 3, 3, null, 0);
            start.printFancyTiles();
            writer = new PrintWriter("/tmp/manhattan/test" + (i+1) + ".txt", "UTF-8");



            if (start.isSolvable()) {
                StatesGraph graph = new StatesGraph(start, goal);
                Vector<Node> solution = graph.BFSGreedy();
                if (solution != null) {
                    Node lastNode = solution.lastElement();
                    lastNode.printFancyTilesToFile(writer);
                    int movesCnt = 0;
                    while (lastNode.getPredecessor() != null) {
                        lastNode = lastNode.getPredecessor();
                        lastNode.printFancyTilesToFile(writer);
                        movesCnt++;
                    }
                    writer.println("Total moves: " + movesCnt + ", iterations: " + graph.getIterationsCounter());
                    allTestsWriter.println("Total moves: " + movesCnt + ", iterations: " + graph.getIterationsCounter());
                    System.out.println("Total moves: " + movesCnt + ", iterations: " + graph.getIterationsCounter());
                }
                else {
                    writer.println("Solution does not exists!");
                    allTestsWriter.println("Solution does not exists!");
                    System.out.println("NULL NULL NULL NULL NULL NULL");
                }
            }
            else {
                writer.println("Solution does not exists!");
                allTestsWriter.println("Solution does not exists!");
                System.out.println("Solution does not exists!");
            }
            writer.close();
        }
        allTestsWriter.close();
    }

    private static int[][] generateRandomTiles(int N, int M) {
        Vector<Integer> tiles = new Vector<Integer>(9);
        int retTiles[][] = new int[N][M];
        Random rand = new Random();

        for (int i = 0; i < N*M; i++)
            tiles.add(i);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int get = randInt(1, tiles.size(), rand);
                retTiles[i][j] = tiles.get(get -1);
                tiles.remove(get -1);
            }
        }
        return retTiles;
    }

    private static int[][] getRandomTiles(int N, int M) {
        int tiles[][] = new int[N][M];
        String strTiles;
        boolean zeroAdded = false;
        Random rand = new Random();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                strTiles = getTilesAsString(N, M, tiles);
                Integer newTile = randInt(0, N*M-1, rand);

                if (newTile == 0 && !zeroAdded) {
                    tiles[i][j] = newTile;
                    zeroAdded = true;
                }
                else {
                    while (strTiles.contains(newTile.toString())) {
                        newTile = randInt(0, N*M-1, rand);
                    }
                    tiles[i][j] = newTile;
                }
            }
        }
        return tiles;
    }

    public static int randInt(int min, int max, Random rand) {
        int randNum = rand.nextInt((max - min) + 1) + min;

        return randNum;
    }

    public static String getTilesAsString(int N, int M, int tiles[][]) {
        StringBuilder strTiles = new StringBuilder();
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++) {
                strTiles.append(Integer.toString(tiles[i][j]));
            }
        return strTiles.toString();
    }
}
