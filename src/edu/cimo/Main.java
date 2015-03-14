package edu.cimo;

import edu.cimo.api.Heuristic;
import edu.cimo.api.Node;
import edu.cimo.api.StatesGraph;

import java.util.Vector;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {
        int tiles[][] = {{3, 4, 5}, {2, 0, 8}, {7, 1, 6}};
//        int tiles[][] = {{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};
        Node start = new Node(tiles, 3, 3, null, 0);
        int tilesGoal[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Node goal = new Node(tilesGoal, 3, 3, null, 0);

        Heuristic h = new Heuristic();
        int d = h.getHammingDistance(start, start);
        //System.out.println(d);

        for (int i : start.getPossibleMoves())
            System.out.print(i + " ");
        System.out.println("\n+++++");
        start.printFancyTiles();

        StatesGraph graph = new StatesGraph(start, goal);
//        graph.BSTGreedy();
//        System.out.println(start.doMove(1));
//        start.printFancyTiles();
        System.out.println(h.getManhattanDistance(start, goal));

        Vector<Node> solution = graph.BFSGreedy();
        try {
            Node lastNode = solution.lastElement();
            lastNode.printFancyTiles();
            int movesCnt = 0;
            while (lastNode.getPredecessor() != null) {
                lastNode = lastNode.getPredecessor();
                lastNode.printFancyTiles();
                movesCnt++;
            }
            System.out.println("Total moves: " + movesCnt + ", iterations: " + graph.getIterationsCounter());
        } catch (NullPointerException e) {
            System.out.println("Solution does not exists!");
        }

    }

}
