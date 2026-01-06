package com.calypso.general.datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Graph_Indian_Deactivated {

    private Map<Vertex_Indian_Deactivated, List<Vertex_Indian_Deactivated>> adjVertices;

    public Graph_Indian_Deactivated() {
        adjVertices = new HashMap<Vertex_Indian_Deactivated, List<Vertex_Indian_Deactivated>>();
    }


   void addVertex(String label) {
        adjVertices.putIfAbsent(new Vertex_Indian_Deactivated(label), new ArrayList<Vertex_Indian_Deactivated>());
    }

    void addVertex(Vertex_Indian_Deactivated vertex) {
        // check for duplicity as may are differnte instances of same object
        adjVertices.putIfAbsent(vertex, new ArrayList<Vertex_Indian_Deactivated>());
    }

    void removeVertex(String label) {
        Vertex_Indian_Deactivated v = new Vertex_Indian_Deactivated(label);
        adjVertices.values().stream().forEach(e -> e.remove(v));
        adjVertices.remove(new Vertex_Indian_Deactivated(label));
    }

    void addEdge(String label1, String label2) {
        Vertex_Indian_Deactivated v1 = new Vertex_Indian_Deactivated(label1);
        Vertex_Indian_Deactivated v2 = new Vertex_Indian_Deactivated(label2);
        adjVertices.get(v1).add(v2);
        adjVertices.get(v2).add(v1);

        // control the nodes as especified in the paper, 
        // check for duplicity
    }

    void addEdge(Vertex_Indian_Deactivated v1, Vertex_Indian_Deactivated v2) {

        adjVertices.get(v1).add(v2);
        adjVertices.get(v2).add(v1);

         // control the nodes as especified in the paper, 
        // check for duplicity

    }

    List<Vertex_Indian_Deactivated> getAdjVertices(String label) {
        return adjVertices.get(new Vertex_Indian_Deactivated(label));
    }

    public boolean hasCycle(Vertex_Indian_Deactivated sourceVertex) {
        sourceVertex.setBeingVisited(true);
    
        for (Vertex_Indian_Deactivated neighbor : sourceVertex.getAdjacencyList()) {
            if (neighbor.isBeingVisited()) {
                // backward edge exists
                return true;
            } else if (!neighbor.isVisited() && hasCycle(neighbor)) {
                return true;
            }
        }
    
        sourceVertex.setBeingVisited(false);
        sourceVertex.setVisited(true);
        return false;
    }

    Graph_Indian_Deactivated createGraph() {
        Graph_Indian_Deactivated graph = new Graph_Indian_Deactivated();
        graph.addVertex("Bob");
        graph.addVertex("Alice");
        graph.addVertex("Mark");
        graph.addVertex("Rob");
        graph.addVertex("Maria");
        graph.addEdge("Bob", "Alice");
        graph.addEdge("Bob", "Rob");
        graph.addEdge("Alice", "Mark");
        graph.addEdge("Rob", "Mark");
        graph.addEdge("Alice", "Maria");
        graph.addEdge("Rob", "Maria");
        return graph;
    }

    Set<String> depthFirstTraversal(Graph_Indian_Deactivated graph, String root) {
        Set<String> visited = new LinkedHashSet<String>();
        Stack<String> stack = new Stack<String>();
        stack.push(root);
        while (!stack.isEmpty()) {
            String vertex = stack.pop();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                for (Vertex_Indian_Deactivated v : graph.getAdjVertices(vertex)) {              
                    stack.push(v.label);
                }
            }
        }
        return visited;
    }

    public Set<String> breadthFirstTraversal(Graph_Indian_Deactivated graph, String root) {
        Set<String> visited = new LinkedHashSet<String>();
        Queue<String> queue = new LinkedList<String>();
        queue.add(root);
        visited.add(root);
        while (!queue.isEmpty()) {
            String vertex = queue.poll();
            for (Vertex_Indian_Deactivated v : graph.getAdjVertices(vertex)) {
                if (!visited.contains(v.label)) {
                    visited.add(v.label);
                    queue.add(v.label);
                }
            }
        }
    return visited;
}

    public static void main(String[] args) throws Exception {

        Graph_Indian_Deactivated graph = new Graph_Indian_Deactivated();


        /*if ("[Bob, Rob, Maria, Alice, Mark]".equals(graph.depthFirstTraversal(graph.createGraph(), "Bob").toString())) {
            System.out.println("Got it right");


        } else {
            System.out.println("Got it wrong");
        }

        
        if ("[Bob, Alice, Rob, Mark, Maria]".equals( graph.breadthFirstTraversal(graph.createGraph(), "Bob").toString())) {
            System.out.println("Got it right");


        } else {
            System.out.println("Got it wrong");
        } */

        Vertex_Indian_Deactivated vertexA = new Vertex_Indian_Deactivated("A");
        Vertex_Indian_Deactivated vertexB = new Vertex_Indian_Deactivated("B");
        Vertex_Indian_Deactivated vertexC = new Vertex_Indian_Deactivated("C");
        Vertex_Indian_Deactivated vertexD = new Vertex_Indian_Deactivated("D");
    
       // Graph graph = new Graph();
        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);
        graph.addVertex(vertexD);
    
        graph.addEdge(vertexA, vertexB);
        graph.addEdge(vertexB, vertexC);
        graph.addEdge(vertexC, vertexA);
        graph.addEdge(vertexD, vertexC);

        if (graph.hasCycle(vertexD)) {
            System.out.println("Got a cycle");


        } else {
            System.out.println("Doesnt have a cycle");
        }
    
    }

}
