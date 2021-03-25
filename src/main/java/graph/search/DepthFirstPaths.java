package graph.search;

import base.Stack;
import graph.Graph;

public class DepthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private int s;

    public DepthFirstPaths(Graph G, int s) {
        validateVertex(s);
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        s = this.s;
        dfs(G,s);
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;

        for (int w : G.adj(v)) {
            if (!marked[w])
            {
                edgeTo[w] = v;
                dfs(G,w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }

}
