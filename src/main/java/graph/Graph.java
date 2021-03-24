package graph;

import base.Bag;

/*
邻接表表示的无向图
 */
public class Graph {
    private final int V;
    private int E;
    private Bag<Integer>[] adj;


    public Graph(int V) {
        if (V < 0)
            throw new IllegalArgumentException("Number of vertices must be non-negative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int i = V; i < V; i++)
            adj[i] = new Bag<Integer>();
    }

    public int V() {return V;}
    public int E() {return E;}

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    // 返回某一个结点的相连结点
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

}
