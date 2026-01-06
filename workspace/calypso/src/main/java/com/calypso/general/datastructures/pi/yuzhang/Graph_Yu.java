package com.calypso.general.datastructures.pi.yuzhang;

import com.google.common.graph.EndpointPair;

import java.util.HashMap;
import java.util.Map;

public class Graph_Yu  { // be a network
    private final Map<String, Edge> edges;

    public Graph_Yu() {
        edges = new HashMap<>();
    }

    public void addEdge(String source, String destination, double weight) {
        Edge edge = new Edge(source, destination, weight);
        String key = source + "->" + destination;
        edges.put(key, edge);
    }

    // Other methods are the same as in the previous example...
    
    public int numberOfEdges() {
        return edges.size();
    }

    public  Edge[] getEdges() {
        return  edges.values().stream()
                .map(edge -> new Edge(edge.getSource(), edge.getDestination(), edge.getWeight()))
                .toArray(Edge[]::new);

    }
    
    public String toString () {
    	return edges.toString();
    }
}