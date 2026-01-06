package com.calypso.general.datastructures.pi;

import java.util.ArrayList;
import java.util.List;

public class SparseGraph {
    private int vertices;
    private List<Integer>[] adjacencyList;

    public SparseGraph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new List[vertices];
        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
    }

    public void addEdge(int source, int destination) {
        adjacencyList[source].add(destination);
    }

    public List<Integer> getNeighbors(int vertex) {
        return adjacencyList[vertex];
    }

    public int getVertices() {
        return vertices;
    }
}
