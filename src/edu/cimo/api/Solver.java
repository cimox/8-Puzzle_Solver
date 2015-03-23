package edu.cimo.api;

import java.util.Comparator;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Vector;

/**
 * Created by cimo on 10/03/15.
 */
public class Solver {
    private Node start, goal;
    private Vector<Node> graph;
    private Integer iterationsCounter;
    private Hashtable<String, Node> visitedNodes;
    private int h; // 1 = hamming, 2 = manhattan

    public Solver(Node start, Node goal, int h) {
        this.start = start;
        this.goal = goal;
        this.graph = new Vector<Node>();
        this.iterationsCounter = new Integer(0);
        this.visitedNodes = new Hashtable<String, Node>();
        this.h = h;
    }

    public Integer getIterationsCounter() {
        return this.iterationsCounter;
    }

    public Vector<Node> BFSGreedy() {
        if (start.compareNodes(goal)) {
            System.out.println("Start node is the goal node!");
        }
        else {
            Node node = null;
            PriorityQueue<Node> edgeQueue = new PriorityQueue<Node>(4, new Comparator<Node>() {
                @Override
                public int compare(Node x, Node y) {
                    if (x.getH() < y.getH()) return -1;
                    if (x.getH() > y.getH()) return 1;
                    return 0;
                }
            });
            if (this.h == 1) start.setH(Heuristic.getHammingDistance(start, goal));
            else start.setH(Heuristic.getManhattanDistance(start, goal));
            edgeQueue.add(start);

            while (!edgeQueue.isEmpty()) {
                node = edgeQueue.poll();

                graph.add(node);
                iterationsCounter++;
                if (node.compareNodes(goal)) {
                    return graph;
                }

                makeMoves(node, edgeQueue);
            }
        }
        return null;
    }

    /**
     * Generates possible moves from current position.
     * @param currNode current node
     * @param edgeNodes priority queue where generated nodes will be inserted
     */
    private void makeMoves(Node currNode, PriorityQueue<Node> edgeNodes) {
        int moves[] = currNode.getPossibleMoves();
        for (int move : moves) {
            Node tmp = new Node(currNode, currNode, move);
            int diff = tmp.doMove(move);
//            if (currNode.getLastUsedOperator() != move) {
                if (this.h == 1) tmp.setH(Heuristic.getHammingDistance(tmp, goal));
                else tmp.setH(Heuristic.getManhattanDistance(tmp, goal));

                if (!visitedNodes.containsKey(tmp.getTilesAsString())) {
                    visitedNodes.put(tmp.getTilesAsString(), tmp);
                    edgeNodes.add(tmp);
                }
//            }
        }
    }
}
