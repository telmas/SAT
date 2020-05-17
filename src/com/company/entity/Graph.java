package com.company.entity;

import java.util.*;

public class Graph<T> {
    private Map<T, List<T>> adjacencyList = new HashMap<>();//todo is DAG safe?
    private Map<Integer,  List<T>> ccnum;
    private Map<T, Boolean> visited;
    private TreeMap<Integer,T > pre;
    private TreeMap<Integer, T> post;
    private int cc = 1;
    private int scc = 1;
    private int clock = 1;

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

    public void  setAdjacencyList(Map<T, List<T>> adjacencyList) {
        this.adjacencyList = adjacencyList;
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
        initDFS();
        iterateDFS();
    }

    public void iterateDFS() {
        getAdjacencyList().keySet().forEach(vertex -> {
            if (!visited.get(vertex)) {
                explore(vertex);
                cc++;
            }
        });
    }

    public void explore(T vertex) {
        visited.put(vertex, true);
        preVisit(vertex);
        getAdjVertices(vertex).forEach(adjVertex -> {
            if (!visited.get(adjVertex)) {
                explore(adjVertex);
            }
        });
        postVisit(vertex);
    }

    public void preVisit(T vertex) {
        if (!ccnum.containsKey(cc)) {
            ccnum.put(cc, new LinkedList<>());
        }
        ccnum.get(cc).add(vertex);
        pre.put(clock++, vertex);
    }

    public void postVisit(T vertex) {
        post.put(clock++, vertex);
    }

    public void initDFS() {
        cc = 1;
        clock = 1;
        pre = new TreeMap<>();
        post = new TreeMap<>(Collections.reverseOrder());
        ccnum = new HashMap<>();
        visited = new HashMap<>();
        getAdjacencyList().keySet().forEach(vertex -> {
            visited.put(vertex, false);
        });
    }

    public Map<Integer, List<T>> computeSCC() {
        Graph<T> reversedGraph = getReversedGraph();
        reversedGraph.runDepthFirstSearch();
        TreeMap<Integer, T> post = reversedGraph.post;
        initDFS();
        Collection<T> values = post.values();
        values.forEach(vertex -> {
            if (!visited.get(vertex)) {
                explore(vertex);
                cc++;
            }
        });
        return ccnum;
    }
}
