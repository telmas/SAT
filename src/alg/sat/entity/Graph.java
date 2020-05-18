package alg.sat.entity;

import java.util.*;

public class Graph<T> {
    private Map<T, List<T>> adjacencyList = new HashMap<>();
    private Map<Integer,  List<T>> ccNum;
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
            if (!getVisited().get(vertex)) {
                explore(vertex);
                setCc(getCc() + 1);
            }
        });
    }

    public void explore(T vertex) {
        getVisited().put(vertex, true);
        preVisit(vertex);
        getAdjVertices(vertex).forEach(adjVertex -> {
            if (!getVisited().get(adjVertex)) {
                explore(adjVertex);
            }
        });
        postVisit(vertex);
    }

    public void preVisit(T vertex) {
        if (!getCcNum().containsKey(getCc())) {
            getCcNum().put(getCc(), new LinkedList<>());
        }
        getCcNum().get(getCc()).add(vertex);
        getPre().put(clock++, vertex);
    }

    public void postVisit(T vertex) {
        getPost().put(clock++, vertex);
    }

    public void initDFS() {
        setPost(new TreeMap<>(Collections.reverseOrder()));
        setPre(new TreeMap<>());
        setCcNum(new HashMap<>());
        setVisited(new HashMap<>());
        getAdjacencyList().keySet().forEach(vertex -> {
            getVisited().put(vertex, false);
        });
        setClock(1);
        setCc(1);
    }

    public Map<Integer, List<T>> computeSCC() {
        Graph<T> reversedGraph = getReversedGraph();
        reversedGraph.runDepthFirstSearch();
        TreeMap<Integer, T> post = reversedGraph.getPost();
        initDFS();
        post.values().forEach(vertex -> {
            if (!getVisited().get(vertex)) {
                explore(vertex);
                setCc(getCc() + 1);
            }
        });
        return getCcNum();
    }

    public Map<Integer, List<T>> getCcNum() {
        return ccNum;
    }

    public void setCcNum(Map<Integer, List<T>> ccNum) {
        this.ccNum = ccNum;
    }

    public Map<T, Boolean> getVisited() {
        return visited;
    }

    public void setVisited(Map<T, Boolean> visited) {
        this.visited = visited;
    }

    public TreeMap<Integer, T> getPre() {
        return pre;
    }

    public void setPre(TreeMap<Integer, T> pre) {
        this.pre = pre;
    }

    public TreeMap<Integer, T> getPost() {
        return post;
    }

    public void setPost(TreeMap<Integer, T> post) {
        this.post = post;
    }

    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }

    public int getScc() {
        return scc;
    }

    public void setScc(int scc) {
        this.scc = scc;
    }

    public int getClock() {
        return clock;
    }

    public void setClock(int clock) {
        this.clock = clock;
    }
}
