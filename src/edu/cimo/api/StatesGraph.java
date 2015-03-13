package edu.cimo.api;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Vector;

/**
 * Created by cimo on 10/03/15.
 */
public class StatesGraph {
    private Node start, goal;
    private Vector<Node> graph;
    private Integer movesCounter;

    public StatesGraph(Node start, Node goal) {
        this.start = start;
        this.goal = goal;
        this.graph = new Vector<Node>();
        this.movesCounter = new Integer(0);
    }

    public void BSTGreedy() throws CloneNotSupportedException {
        Heuristic h = new Heuristic();
        Node node = null;
        PriorityQueue<Node> currNodes = new PriorityQueue<Node>(4, new Comparator<Node>() {
            @Override
            public int compare(Node x, Node y) {
                if (x.getH() < y.getH()) return -1;
                if (x.getH() > y.getH()) return 1;
                return 0;
            }
        });

        if (currNodes.isEmpty()) { // Starting alg. generate first possible moves.
            graph.add(start);
            makeMoves(start, currNodes);
            node = currNodes.poll(); // Get the head of queue and remove object.
            currNodes.clear(); // Empty priority queue
            graph.add(node);
            movesCounter++;
        }

        while (!node.compareNodes(goal)) {
            makeMoves(node, currNodes);

            node = currNodes.poll();
            currNodes.clear();
            graph.add(node);
            movesCounter++;
        }

        for (Node e : graph)
            e.printFancyTiles();
    }

    /**
     * Generates possible moves from current position.
     * @param currNode current node
     * @param currNodes priority queue where generated nodes will be inserted
     */
    private void makeMoves(Node currNode, PriorityQueue<Node> currNodes) {
        int moves[] = currNode.getPossibleMoves();
        Heuristic h = new Heuristic();
        for (int move : moves) {
            Node tmp = new Node(currNode, currNode, move);
            tmp.doMove(move);
            tmp.setH(h.getHammingDistance(tmp, goal));
            currNodes.add(tmp);
        }
    }

}
