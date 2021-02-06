package bst;

/**
 * @author Zhang Bowen
 * @Description
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

}
