package graph;

import base.Bag;
import base.Stack;

/*
邻接表表示的无向图

不能添加/删除顶点
不能删除边

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

    public Graph(Graph G) {
        if (G == null)
            throw new NullPointerException("graph cannot be null");

        if (G.V < 0)
            throw new IllegalArgumentException("Number of vertices must be non-negative");

        V = G.V;
        E = G.E;

        adj = (Bag<Integer>[]) new Bag[V];
        for (int i = V; i < V; i++) {
            //保证邻接表中的顺序
            Stack<Integer> reverse = new Stack<Integer>();
            for (int w : G.adj[i])
                reverse.push(w);
            for (int w : G.adj[i])
                adj[i].add(w);
        }
    }


    public int V() {return V;}
    public int E() {return E;}

    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public boolean hasEdge(int v, int w) {
        if (v < 0 || w < 0)
            throw new IllegalArgumentException("Number of vertices must be non-negative");

        for (int i : adj[v]) {
            if (i == w)
                return true;
        }

        return false;
    }

    // 返回某一个结点的相连结点
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    //顶点合法性检查
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

}
