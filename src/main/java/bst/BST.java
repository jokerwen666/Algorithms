package bst;

/**
 * @author Zhang Bowen
 * @Description
 * 二叉搜索树BST
 * 左子树中所有结点的值均小于根结点值
 * 右子树中所有结点的值均大于根节点值
 * 平均情况下的查找、插入操作的时间复杂度为O(lgN)
 * 最差情况下的查找、插入操作的时间复杂度为O(N)\
 * 造成最差情况的原因是，根据一组数据构造的BST不一定是平衡的
 *
 * @ClassName BST
 * @date 2021.02.06 21:29
 */
public class BST <Key extends Comparable<Key>, Value>{
    private Node root;
    private class Node {
        private final Key key;
        private Value val;
        private Node left, right;
        private int N;

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    public int size() {
        return size(root);
    }
    private int size(Node x) {
        return x == null ? 0 : x.N;
    }


    public Value get(Key key) {
        return get(root,key);
    }
    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        //bst中某个结点的左连接中所有结点的键均小于该结点的键，反之亦然
        if (cmp < 0) return get(x.left, key);
        else if(cmp > 0) return get(x.right, key);
        else return x.val;
    }

    public void put(Key key, Value val) {
        root = put(root,key,val);
    }
    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key,val,1);
        int cmp = key.compareTo(x.key);

        if(cmp < 0) x.left = put(x.left,key,val);
        else if(cmp > 0) x.right = put(x.right,key,val);
        else x.val = val;
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Key min() {return min(root).key;}
    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    public Key max() {return max(root).key;}
    private Node max(Node x) {
        if (x.right == null) return x;
        return max(x.right);
    }

    public Key floor(Key key) {
        Node x = floor(root,key);
        return x != null ? x.key : null;
    }
    private Node floor(Node x, Key key) {
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left,key);
        Node t = floor(x.right,key);
        if (t != null) return t;
        else return x;
    }

    public Key celling(Key key) {
        Node x = celling(root,key);
        return x != null ? x.key : null;
    }
    private Node celling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp > 0) return celling(x.right,key);
        Node t = celling(x.left,key);
        if (t != null) return t;
        else return x;
    }

    //这一步需要将root重新赋值，因为删除了最小键结点，可能导致根节点发生改变
    public void deleteMin() {root = deleteMin(root);}
    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        //结点数目会递归发生改变
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void deleteMax() {root = deleteMax(root);}
    private Node deleteMax(Node x) {
        //BST中的最大结点是沿着右子树遍历的最后一个结点，一直沿着右向下查找，直到找到一个没有右结点的结点，删除（返回左结点，该结点没有被引用会被回收）即可
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    //删除某个结点的时候，主要分为四步：
    //（1）找到需要删除的结点
    //（2）如果需要删除结点的左子树或者右子树为空，则直接返回不为空的子树的根结点
    //（3）如果需要删除结点的左子树或者右子树均不为空，则找出右子树中的最小键结点，并将该结点的右孩子结点指向删除过该结点的原右子树的根节点
    //（4）同时将该结点的左孩子指向原左子树的根节点，然后将被删除结点父节点的左链接/右链接指向该结点
    public void delete(Key key) {root = delete(root,key);}
    private Node delete(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left,key);
        else if (cmp > 0) x.right = delete(x.right,key);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(x.right);
            x.right = deleteMin(x.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

}
