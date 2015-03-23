package edu.cimo.test;


import edu.cimo.api.Node;
import edu.cimo.api.Solver;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Vector;


/**
 * Created by HP on 13. 3. 2015.
 */
public class NodeTest {
    @Test
    public void testAdd() throws FileNotFoundException, UnsupportedEncodingException {
        int expectedPos[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Node goal = new Node(expectedPos, 3, 3, null, 0);
        PrintWriter writer;

        Random rand = new Random();
        for (int i = 0; i < 100; i++) { // 100 tests
            Node start = new Node(getRandomTiles(3, 3, rand), 3, 3, null, 0);
            writer = new PrintWriter("/tmp/test" + i+1 + ".txt", "UTF-8");


            System.out.println("Running test: " + i+1);
            if (start.isSolvable()) {
                Solver graph = new Solver(start, goal, 1);
                Vector<Node> solution = graph.BFSGreedy();
                Node lastNode = solution.lastElement();
                lastNode.printFancyTilesToFile(writer);
                int movesCnt = 0;
                while (lastNode.getPredecessor() != null) {
                    lastNode = lastNode.getPredecessor();
                    lastNode.printFancyTilesToFile(writer);
                    movesCnt++;
                }
                writer.println("Total moves: " + movesCnt + ", iterations: " + graph.getIterationsCounter());
                System.out.println("Total moves: " + movesCnt + ", iterations: " + graph.getIterationsCounter());
            }
            else {
                writer.println("Solution does not exists!");
                System.out.println("Solution does not exists!");
            }
            writer.close();
        }
    }

    private int[][] getRandomTiles(int N, int M, Random rand) {
        int tiles[][] = new int[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                tiles[i][j] = randInt(0,9,rand);
            }
        }
        return tiles;
    }

    public static int randInt(int min, int max, Random rand) {
        int randNum = rand.nextInt((max - min) + 1) + min;

        return randNum;
    }
}
