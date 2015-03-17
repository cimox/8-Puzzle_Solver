package edu.cimo.api;

import java.util.Comparator;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Vector;

/**
 * Created by cimo on 10/03/15.
 */
public class StatesGraph {
    private Node start, goal;
    private Vector<Node> graph;
    private Integer iterationsCounter;
    private Hashtable<String, Node> visitedNodes;

    public StatesGraph(Node start, Node goal) {
        this.start = start;
        this.goal = goal;
        this.graph = new Vector<Node>();
        this.iterationsCounter = new Integer(0);
        this.visitedNodes = new Hashtable<String, Node>();
    }

    public Integer getIterationsCounter() {
        return this.iterationsCounter;
    }

    public Vector<Node> BFSGreedy() {
        if (start.compareNodes(goal)) {
            System.out.println("Start node is the goal node!");
        }
        else {
            Heuristic h = new Heuristic();
            Node node = null;
            PriorityQueue<Node> edgeQueue = new PriorityQueue<Node>(4, new Comparator<Node>() {
                @Override
                public int compare(Node x, Node y) {
                    if (x.getH() <= y.getH()) return -1;
                    if (x.getH() > y.getH()) return 1;
                    return 0;
                }
            });
            start.setH(h.getHammingDistance(start,goal));
            edgeQueue.add(start);

            while (!edgeQueue.isEmpty()) {
                node = edgeQueue.poll();
                // Visited nodes are handled in makeMoves method.
                graph.add(node);

                if (node.compareNodes(goal)) {
                    System.out.println("Solution found!");
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
        Heuristic h = new Heuristic();
        for (int move : moves) {
            Node tmp = new Node(currNode, currNode, move);
            int diff = tmp.doMove(move);
            if (currNode.getLastUsedOperator() != move) {
//                tmp.setH(h.getHammingDistance(tmp, goal) + diff);
                tmp.setH(h.getManhattanDistance(tmp, goal) + diff);
                if (!visitedNodes.containsKey(tmp.getTilesAsString())) {
                    visitedNodes.put(tmp.getTilesAsString(), tmp);
                    edgeNodes.add(tmp);
                }
            }
        }
    }
}
