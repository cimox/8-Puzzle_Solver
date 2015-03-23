package edu.cimo;

import edu.cimo.api.Node;
import edu.cimo.api.Solver;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Vector;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        int tiles[][] = {{3, 8, 5}, {2, 4, 1}, {5, 0, 6}};
        int tilesGoal[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Node goal = new Node(tilesGoal, 3, 3, null, 0);
        PrintWriter writer;
        Vector<Node> nodes = new Vector<Node>(100);

        for (int i = 0; i < 100; i++) { // Generates 100 random nodes
            nodes.add(new Node(generateRandomTiles(3,3), 3,3, null, 0));
        }

        for (int h = 1; h <= 2; h++) {
            PrintWriter allTestsWriter = new PrintWriter("/tmp/"+ h +"/_allTests.txt", "UTF-8");
            for (int i = 0; i < 100; i++) { // 100 tests
                System.out.println("\nRunning test: " + (i + 1));
                allTestsWriter.println("\nRunning test: " + (i + 1));
                Node start = nodes.get(i);

                writer = new PrintWriter("/tmp/"+ h +"/test" + (i + 1) + ".txt", "UTF-8");


                if (start.isSolvable()) {
                    Solver solver = new Solver(start, goal, h);
                    Vector<Node> path = solver.BFSGreedy();
                    if (path != null) {
                        Node lastNode = path.lastElement();
                        lastNode.printFancyTilesToFile(writer);
                        int movesCnt = 0;
                        while (lastNode.getPredecessor() != null) {
                            lastNode = lastNode.getPredecessor();
                            lastNode.printFancyTilesToFile(writer);
                            movesCnt++;
                        }
                        writer.println("Total moves: " + movesCnt + ", iterations: " + solver.getIterationsCounter());
                        allTestsWriter.println("Total moves: " + movesCnt + ", iterations: " + solver.getIterationsCounter());
                        System.out.println("Total moves: " + movesCnt + ", iterations: " + solver.getIterationsCounter());
                    } else {
                        writer.println("Solution does not exists!");
                        allTestsWriter.println("Solution does not exists! NULL " + solver.getIterationsCounter());
                        System.out.println("Solution does not exists! NULL");
                    }
                } else {
                    writer.println("Solution does not exists!");
                    allTestsWriter.println("Solution does not exists!");
                    System.out.println("Solution does not exists!");
                }
                writer.close();
            }
            allTestsWriter.close();

            // Reversed
            allTestsWriter = new PrintWriter("/tmp/"+ h +"/rev_allTests.txt", "UTF-8");
            for (int i = 0; i < 100; i++) { // 100 tests
                allTestsWriter.println("\nRunning test: " + (i + 1));
                Node start = nodes.get(i);

                writer = new PrintWriter("/tmp/"+ h +"/rev_test" + (i + 1) + ".txt", "UTF-8");

                if (start.isSolvable()) {
                    Solver solver = new Solver(goal, start, h);
                    Vector<Node> path = solver.BFSGreedy();
                    if (path != null) {
                        Node lastNode = path.lastElement();
                        lastNode.printFancyTilesToFile(writer);
                        int movesCnt = 0;
                        while (lastNode.getPredecessor() != null) { // Postupnost krokov do suboru.
                            lastNode = lastNode.getPredecessor();
                            lastNode.printFancyTilesToFile(writer);
                            movesCnt++;
                        }
                        writer.println("Total moves: " + movesCnt + ", iterations: " + solver.getIterationsCounter());
                        allTestsWriter.println("Total moves: " + movesCnt + ", iterations: " + solver.getIterationsCounter());
                    } else {
                        writer.println("Solution does not exists!");
                        allTestsWriter.println("Solution does not exists! NULL " + solver.getIterationsCounter());
                    }
                } else {
                    writer.println("Solution does not exists!");
                    allTestsWriter.println("Solution does not exists!");
                }
                writer.close();
            }
            allTestsWriter.close();
        }
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

    public static int randInt(int min, int max, Random rand) {
        int randNum = rand.nextInt((max - min) + 1) + min;

        return randNum;
    }
}
