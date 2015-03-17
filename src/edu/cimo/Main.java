package edu.cimo;

import edu.cimo.api.Heuristic;
import edu.cimo.api.Node;
import edu.cimo.api.StatesGraph;

import java.util.Vector;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {
        int tiles[][] = {{8, 1, 2}, {0, 4, 3}, {7, 6, 5}};
//        int tiles[][] = {{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};
        Node start = new Node(tiles, 3, 3, null, 0);
        int tilesGoal[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Node goal = new Node(tilesGoal, 3, 3, null, 0);


        if (start.isSolvable()) {
            StatesGraph graph = new StatesGraph(start, goal);
            Vector<Node> solution = graph.BFSGreedy();
            Node lastNode = solution.lastElement();
            lastNode.printFancyTiles();
            int movesCnt = 0;
            while (lastNode.getPredecessor() != null) {
                lastNode = lastNode.getPredecessor();
                lastNode.printFancyTiles();
                movesCnt++;
            }
            System.out.println("Total moves: " + movesCnt + ", iterations: " + graph.getIterationsCounter());
        }
        else {
            System.out.println("Solution does not exists!");
        }

    }

}
