package com.calypso.general.datastructures.pi;

import com.google.common.graph.Graph;
import com.google.common.graph.MutableGraph;

import java.util.ArrayList;
import java.util.List;

public class AllPathsFinder {
	
	// check if we may optmize

    public static <N> List<List<N>> findAllPaths(MutableGraph<N> graph, N startNode, N endNode) {
        List<List<N>> allPaths = new ArrayList<>();
        findAllPathsRecursive(graph, startNode, endNode, new ArrayList<>(), allPaths);
        return allPaths;
    }

    private static <N> void findAllPathsRecursive(MutableGraph<N> graph, N currentNode, N endNode, List<N> currentPath, List<List<N>> allPaths) {
        if (currentNode.equals(endNode)) {
            allPaths.add(new ArrayList<>(currentPath));
        } else {
            for (N neighbor : graph.successors(currentNode)) {
                currentPath.add(neighbor);
                findAllPathsRecursive(graph, neighbor, endNode, currentPath, allPaths);
                currentPath.remove(currentPath.size() - 1);
            }
        }
    }
}
