package edu.cimo;

import edu.cimo.api.Heuristic;
import edu.cimo.api.Node;
import edu.cimo.api.StatesGraph;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {
        int tiles[][] = {{1, 2, 3}, {5, 4, 6}, {7, 0, 8}};
        Node start = new Node(tiles, 3, 3, null, 0);
        int tilesGoal[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Node goal = new Node(tiles, 3, 3, null, 0);

        Heuristic h = new Heuristic();
        int d = h.getHammingDistance(start, start);
        //System.out.println(d);

        for (int i : start.getPossibleMoves())
            System.out.print(i + " ");
        System.out.println("\n+++++");
        start.printFancyTiles();

        StatesGraph graph = new StatesGraph(start, goal);
        graph.BSTGreedy();
    }
}
