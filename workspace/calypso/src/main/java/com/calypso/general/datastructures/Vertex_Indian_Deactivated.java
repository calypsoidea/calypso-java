package com.calypso.general.datastructures;

import java.util.*;

import java.io.*;
import java.net.*;
import java.util.*;

class Vertex_Indian_Deactivated {
    String label;
    private boolean beingVisited;
    private boolean visited;
    private List<Vertex_Indian_Deactivated> adjacencyList;

    Vertex_Indian_Deactivated(String label) {
        this.label = label;
        this.adjacencyList = new ArrayList<Vertex_Indian_Deactivated>();

    }

    public void setBeingVisited(boolean beinVisited)
    {
         this.beingVisited = beinVisited;
    }    

    public boolean isBeingVisited() {
        
        return this.beingVisited;
    }

    public void setVisited(boolean visited)
    {
         this.visited = visited;
    }    

    public boolean isVisited() {
        return this.visited;
    }

    public List<Vertex_Indian_Deactivated> getAdjacencyList() {
        return this.adjacencyList;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; 
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false; 
        }

        Vertex_Indian_Deactivated other = (Vertex_Indian_Deactivated) obj; 

        return this.label.equals(other.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label); // Generates a hash code based on name and age
    }

    public void addNeighbor(Vertex_Indian_Deactivated adjacent) {
        this.adjacencyList.add(adjacent);
    }

} // Class Vertex
