package redblackBST;

import LLRBTree.LLRBTree;

import java.util.TreeMap;

/**
 * Q1、为什么从每个叶子到根的所有路径上不能有两个连续的红色结点？
 * A1：这样做是为了给搜索性能提供保证
 * 因为红黑树中规定每条路径中的黑结点数量相同，因此为了保证最长的路径最多是最短路径的的两倍，我们需要规定不能一条路径上不能有连续的红色结点
 * 由于每条路径上的黑色节点都必须相等，所以最短的路径肯定是全是黑色节点。
 * 那最长的路径呢，如果要求红色的点不能连续，那最长的路径肯定是红黑相隔，由于根节点是黑色的，所以，最长的路径应该是黑色节点和红色节点一样多，也就是黑色节点的两倍。
 * 正是由于有了红黑结点的区分以及规定不能有连续红结点，才使得红黑树可以自己在插入的过程中进行动态的自平衡调整，使得在最极端的情况（按需插入）时也不会出现传统BST完全左倾/右倾的情况
 * 因此在红黑树中最差的情况下也可以使得树的高度（树的高度等于根节点的高度）不会超过2lgN，也就是说从根结点到叶子结点的最长路径不会超过2lgN
 *
 * Q2、为什么新加入的结点需要是红结点？
 * A2：当前红黑树中从根节点到每个叶子节点的黑色节点数量是一样的，此时假如新插入的是黑色节点的话，必然破坏规则(它所在的路径上会多出一个黑色节点)，
 * 但加入红色节点却不一定，除非其父节点就是红色节点才会破坏规则，因此加入红色节点，破坏规则的可能性小一些。
 *
 * Q3、自顶向下实现基于2-3-4树的红黑树时候，为什么要在插入操作的向下搜索过程中分裂任何能遇到的4-结点？
 * A3：在自顶向下的构造2-3-4树的过程中如果我们需要向树底部的一个2结点或者3结点插入键，则直接插入扩充为3结点、4结点即可，
 * 但是如果需要向一个4结点中插入新键，则需要将4结点的中间键分裂出去传递给该结点的父结点。如果父结点是2结点或3结点直接插入即可，
 * 若我们必须分裂一个4结点，而它的父结点也是一个4结点，则将其结点也分裂。更容易的方法是：在向下搜索树过程中分裂任何遇到的4-结点，从而保证搜索路径不会在一个4-结点终结。
 *
 * @author Zhang Bowen
 * @ClassName RedBlackBST
 * @date 2021.02.21 18:00
 */

public class RedBlackBST<Key extends Comparable<Key>, Value> {
    private static final boolean red = true; //红链接
    private static final boolean black = false; //黑链接

    private Node root; //根节点

    private class Node {
        Key key; //键
        Value val; //值
        Node right, left; //左右子结点
        Node parent; //父亲结点
        int N; //以该结点为根结点的子树中的结点总数
        boolean color; //由父结点指向该结点的链接颜色

        public Node(Key key, Value val, int n, boolean color) {
            this.key = key;
            this.val = val;
            N = n;
            this.color = color;
        }
    }



}