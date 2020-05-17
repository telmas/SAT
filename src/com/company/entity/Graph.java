package com.company.entity;

import java.util.*;

public class Graph<T> {
    private final Map<T, List<T>> adjacencyList = new HashMap<>();//todo is DAG safe?
    private Map<T, Boolean> visited;

    public void addVertex(T vertex) {
        getAdjacencyList().put(vertex, new LinkedList<>());
    }

    public void addEdge(T sourceVertex, T destinationVertex) {
        if (!getAdjacencyList().containsKey(sourceVertex)) {
            addVertex(sourceVertex);
        }

        if (!getAdjacencyList().containsKey(destinationVertex)) {
            addVertex(destinationVertex);
        }

        getAdjVertices(sourceVertex).add(destinationVertex);
    }

    public boolean hasVertex(T vertex) {
        return getAdjacencyList().containsKey(vertex);
    }

    public boolean hasEdge(T sourceVertex, T destinationVertex) {
        return getAdjVertices(sourceVertex).contains(destinationVertex);
    }

    public void removeVertex(T vertex) {
        getAdjacencyList().values().forEach(e -> e.remove(vertex));
        getAdjacencyList().remove(vertex);
    }

    public void removeEdge(T sourceVertex, T destinationVertex) {
        List<T> sourceVertexList = getAdjVertices(sourceVertex);
        List<T> destinationVertexList = getAdjVertices(destinationVertex);

        if (sourceVertexList != null) {
            sourceVertexList.remove(destinationVertex);
        }

        if (destinationVertexList != null) {
            destinationVertexList.remove(sourceVertex);
        }
    }

    public int getVertexCount() {
        return getAdjacencyList().keySet().size();
    }

    public int getEdgesCount(boolean isBidirectional) {
        return getAdjacencyList().keySet().stream().mapToInt(vertex -> getAdjVertices(vertex).size()).sum();
    }

    public Map<T, List<T>> getAdjacencyList() {
        return adjacencyList;
    }

    public List<T> getAdjVertices(T vertex) {
        return getAdjacencyList().get(vertex);
    }

    public Graph<T> getReversedGraph() {
        Graph<T> reversedGraph = new Graph<>();
        getAdjacencyList().keySet().forEach(sourceVertex -> {
            getAdjacencyList().get(sourceVertex).forEach(destinationVertex -> {
                reversedGraph.addEdge(destinationVertex, sourceVertex);
            });
        });
        return reversedGraph;
    }

    public void runDepthFirstSearch() {
        //todo stack?
        initDFS();
        getAdjacencyList().keySet().forEach(vertex -> {
            if (!visited.get(vertex)) {
                explore(vertex);
            }
        });
    }

    public void explore(T vertex) {

    }

    public void initDFS() {
        visited = new HashMap<>();
        getAdjacencyList().keySet().forEach(vertex -> {
            visited.put(vertex, false);
        });
    }
}
