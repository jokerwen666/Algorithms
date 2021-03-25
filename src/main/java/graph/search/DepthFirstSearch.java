package graph.search;

import base.Stack;
import graph.Graph;

import java.util.Iterator;

public class DepthFirstSearch {
    private boolean[] marked;
    private int count;

    // 将无向图G中与v相连的所有顶点进行标记
    public DepthFirstSearch(Graph G, int s) {
        validateVertex(s);
        marked = new boolean[G.V()];
        dfs(G,s);
    }

    // 递归实现dfs
    private void dfs(Graph G, int v) {
        count++;
        marked[v] = true;

        // 遍历顶点v邻接表中的每一个没有被访问过的顶点，递归查找
        // 所有与v相连的顶点都被遍历后返回
        for (int w : G.adj(v)) {
            // 如果v的某个顶点已经被标记过，说明在之前已经经过了这个点，不需要再递归
            if (!marked[w])
                dfs(G,w);
        }
    }

    // 非递归实现的dfs
    private void dfsWithStack(Graph G, int v) {
        marked[v] = true;

        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.V()];
        for (int x = 0; x < G.V(); x++)
            adj[x] = G.adj(x).iterator();

        Stack<Integer> stack = new Stack<Integer>();
        // 栈为空表示所有与v相连的顶点都已经被检索且处理
        while (!stack.isEmpty()) {
            // 当前顶点为栈顶元素
            int s = stack.peak();
            // 当前顶点还有没被处理的相连结点
            if (!adj[s].hasNext()) {
                int w = adj[s].next();
                // 如果w还未被标记，说明结点w尚未被处理，此时将该结点标记并进栈，相当于更深一次处理（dfs深度优先）
                if (!marked[w]) {
                    marked[w] = true;
                    stack.push(w);
                }

            }

            // 当前的所有相连结点均被处理，从栈顶弹出
            else
                stack.pop();
        }
    }

    public boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }

    public int count() {
        return count;
    }


    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

}
