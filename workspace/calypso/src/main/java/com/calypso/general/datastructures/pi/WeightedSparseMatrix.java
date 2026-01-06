package com.calypso.general.datastructures.pi;

import java.util.LinkedList;
import java.util.List;

public class WeightedSparseMatrix {
    private int rows;
    private int columns;
    private List<List<Edge>> matrix;

    public WeightedSparseMatrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = new LinkedList<>();

        for (int i = 0; i < rows; i++) {
            matrix.add(new LinkedList<>());
        }
    }

    public void addEdge(int fromNode, int toNode, double weight) {
        Edge edge = new Edge(fromNode, toNode, weight);
        matrix.get(fromNode).add(edge);
    }

    private class Edge {
        int toNode;
        double weight;

        public Edge(int fromNode, int toNode, double weight) {
            this.toNode = toNode;
            this.weight = weight;
        }
    }
}
