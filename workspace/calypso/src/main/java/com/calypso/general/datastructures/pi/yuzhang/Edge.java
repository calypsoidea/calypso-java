package com.calypso.general.datastructures.pi.yuzhang;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.graph.EndpointPair;

public class Edge {
    private final EndpointPair<String> endpoints;
    private final double weight;

    public Edge(String source, String destination, double weight) {
        this.endpoints = EndpointPair.ordered(source, destination);
        this.weight = weight;
    }

    public EndpointPair<String> getEndpoints() {
        return endpoints;
    }
    
    /*
    public List<Edge> getOutgoingEdges(String vj) {
        List<Edge> outgoingEdges = new ArrayList<>();
        for (Map.Entry<EndpointPair<String>, Edge> entry : edges.entrySet()) {
            if (entry.getKey().source().equals(vj)) {
                outgoingEdges.add(entry.getValue());
            }
        }
        return outgoingEdges;
    }*/

    public double getWeight() {
        return weight;
    }

    public String getSource() {
        return endpoints.source();
    }

    public String getDestination() {
        return endpoints.target();
    }
    
    public String toString() {
    	return Double.toString(this.getWeight());
    }
}
