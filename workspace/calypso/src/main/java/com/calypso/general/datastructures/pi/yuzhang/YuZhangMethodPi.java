package com.calypso.general.datastructures.pi.yuzhang;

import java.util.*;

import com.google.common.graph.*;

public class YuZhangMethodPi {

	public YuZhangMethodPi() {
		// TODO Auto-generated constructor stub
	}
	
	public static void dijkstra(Graph_Yu L, int v0) {
	    int M = L.numberOfEdges();

	    Map<String, Double> Dis = new HashMap<>();
	    Map<String, List<String>> Path = new HashMap<>();

	    // initialize distances and paths for all vertices in L(G)
	for (int m = 0; m < M; m++) {
	        String vertex = String.valueOf(m);
	        Dis.put(vertex, Double.POSITIVE_INFINITY);
	        Path.put(vertex, new ArrayList<>());
	    }

	    Dis.put("(0, " + v0 + ")", 0.0);

	    for (int m = 1; m <= M; m++) {
	        for (Edge e : L.getEdges()) {
	            String vi = e.getSource();
	            String vj = e.getDestination();
	            String vl = String.valueOf(m);
	            String key = vi + ", " + vj;
	            double dist_via_key = Dis.get(key) + e.getWeight();

	            if (dist_via_key < Dis.get(vj + ", " + vl) &&
	                    (!Path.get(vj + ", " + vl).contains(vl) || m == 0)) {
	                List<String> path = Path.get(key);
	                path.add(vl);
	                Path.put(vj + ", " + vl, path);
	                Dis.put(vj + ", " + vl, dist_via_key);
	            }
	        }
	    }

	    // Transfer distances and paths from L(G) to G
	Map<String, Double> Dtoken = new HashMap<>();
	    Map<String, List<String>> Ptoken = new HashMap<>();

	    for (Map.Entry<String, Double> entry : Dis.entrySet()) {
	        String t = entry.getKey().split(", ")[1];
	        double dist = entry.getValue();

	        if (dist < Dtoken.getOrDefault(t, Double.POSITIVE_INFINITY)) {
	            Dtoken.put(t, dist);
	            
	            // get all nodes from V0 to t
	            Ptoken.put(t, null);
	        }
	    }

	    System.out.println(Dtoken);
	    System.out.println(Ptoken);
	}


}

/*
 * public static void dijkstra(Graph G, int v0) {
    int M = G.length();

    Map<String, Integer> Dis = new HashMap<>();
    Map<String, List<String>> Path = new HashMap<>();

    // initialize distances and paths for all vertices in L(G)
for (int m = 0; m < M; m++) {
        String vertex = String.valueOf(m);
        Dis.put(vertex, Integer.MAX_VALUE);
        Path.put(vertex, new ArrayList<>());
    }

    Dis.put("(0, " + v0 + ")", 0);

    for (int m = 1; m <= M; m++) {
        for (Edge e : G.getEdges()) {
            String vi = e.getSource();
            String vj = e.getDestination();
            String vl = String.valueOf(m);
            String key = vi + ", " + vj;
            int dist_via_key = Dis.get(key) + e.getWeight();

            if (dist_via_key < Dis.get(vj + ", " + vl) &&
                    (!Path.get(vj + ", " + vl).contains(vl) || m == 0)) {
                List<String> path = Path.get(key);
                path.add(vl);
                Path.put(vj + ", " + vl, path);
                Dis.put(vj + ", " + vl, dist_via_key);
            }
        }
    }

    // Transfer distances and paths from L(G) to G
Map<String, Integer> Dtoken = new HashMap<>();
    Map<String, List<String>> Ptoken = new HashMap<>();

    for (Map.Entry<String, Integer> entry : Dis.entrySet()) {
        String t = entry.getKey().split(", ")[1];
        int dist = entry.getValue();

        if (dist < Dtoken.getOrDefault(t, Integer.MAX_VALUE)) {
            Dtoken.put(t, dist);
            Ptoken.put(t, Path.get(entry.getKey()));
        }
    }

    System.out.println(Dtoken);
    System.out.println(Ptoken);
}
 * 
 */
