package LLRBTree;

import bst.BST;
import redblackBST.RedBlackBST;

import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 * 该类是一个基于2-3树的左倾红黑树的递归实现，与jdk1.8自带的TreeMap红黑树不一样
 *
 * 2-3树分为2结点和3结点，2结点就是BST中的普通结点，结点存储1个值，有2个子结点，3结点存储2个值，有三个子结点
 * 2-3-4树分为2结点和4结点，4结点存储3个值，有4个子结点
 *
 * 左倾红黑树的红链接均为左连接，没有任何一个结点与两个红链接相连（没有4-结点），
 * 规定红链接一定是左链接是为了减少复杂度（3-结点存在右倾左倾两种情况），便于代码实现
 * 左倾的红黑树是关于黑链接完美平衡的，任意空链接到根结点的路径上的黑链接数量相同
 * 红黑树的根结点总是黑色的
 *
 * 左倾的2-3红黑树与左倾自顶向下构造的2-3-4红黑树在插入的代码上只有一行区别（2-3-4树将2-3树最后的颜色反转提前，可以发现在2-3树中经过最后的颜色反转消除了4结点）
 *
 * AVL（平衡二叉树）：它是一棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树
 *
 * @author Zhang Bowen
 *
 * @ClassName LLRBTree
 * @date 2021.02.21 18:00
 */

public class LLRBTree <Key extends Comparable<Key>, Value>{
    private static final boolean red = true; //红链接
    private static final boolean black = false; //黑链接

    private Node root; //根节点

    private class Node {
        Key key; //键
        Value val; //值
        Node right, left; //左右子树
        int N; //以该结点为根结点的子树中的结点总数
        boolean color; //由父结点指向该结点的链接颜色

        public Node(Key key, Value val, int n, boolean color) {
            this.key = key;
            this.val = val;
            N = n;
            this.color = color;
        }
    }

    //插入/更新结点
    public void put(Key key, Value val) {
        root = put(root,key,val);
        root.color = black;

    }
    private Node put(Node x, Key key, Value val) {
        //插入一个新结点，与普通BST的结点插入相同
        if (x == null) return new Node(key,val,1,red);

        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val);
        else if(cmp > 0)  x.right = put(x.right, key, val);
        else x.val = val;

        //插入后进行平衡处理
        return balance(x);
    }

    //查找结点
    public Value get(Key key) {
        return get(root,key);
    }
    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left,key);
        else if(cmp > 0) return get(x.right,key);
        else return x.val;
    }

    //删除最小结点
    //删除最小值的时候如果删除的是3结点是很简单的，只需要删除一个结点，留下一个2结点即可，
    //但是如果删除一个2结点就有可能导致树的平衡性发生变化（从根结点到每个叶子结点路径中的黑链接数量不一样）
    //处理办法是从上到下进行变换，保证在删除的时候被删除值所在的结点不是一个2结点
    //因此我们需要做的是在删除操作的向下搜索的过程中合并所看到的所有2结点，这一操作与自顶向下构造2-3-4的插入操作是互逆的（2-3-4树的插入是分解4结点）
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("LLRBTree underflow");

        //根结点的左右子结点均为黑色的说明根结点本身是2-结点，此时将根结点本身转红将其作为3结点的一部分（虽然根结点没有父结点）
        if (!isRed(root.left) && !isRed(root.right))
            root.color = red;
        root = deleteMin(root);
        if (!isEmpty()) root.color = black;
    }
    //需要特别说明的是，我们可以保证每一步处理的结点x均不为2结点，因为已经自顶向下的合并过2结点了
    private Node deleteMin(Node x) {
        //因为已经保证被删除的结点不可能是2结点，因此从根结点出发沿着左子树一路搜索下去，找到最后一个结点，删除即可
        if (x.left == null) return null;

        //意味着x的左子节点为一个2节点，这时我们需要将这个2结点改为3结点或者临时的4结点（删除最小值一定在左子树，右子树不需要处理）
        //isRed(x.left)为true，说明x和x.left此时和合为一个3结点，此时x.color必为black
        //isRedisRed(x.left.left)为true，说明x.left和x.left.left此时合为一个3-结点，此时x.color必为red（因为可以保证此时x结点已经被处理为非2结点）
        if (!isRed(x.left) && !isRed(x.left.left))
            x = moveRedLeft(x);

        //沿着左子树递归处理下一层
        x.left = deleteMin(x.left);

        //在删除的过程中可能会产生临时的4结点，这里需要解开4结点
        return balance(x);
    }

    //删除最大结点
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("LLRBTree underflow");

        if (!isRed(root.left) && !isRed(root.right))
            root.color = red;

        root = deleteMax(root);
        if (!isEmpty()) root.color = black;
    }
    private Node deleteMax(Node x) {
        //如果一个结点的左节点为2-结点，则将其右旋，这么做是为了保证待删除的键所对应的BST结点没有子结点
        if (isRed(x.left)) x = rotateRight(x);

        if (x.right == null) return null;

        if (!isRed(x.right) && !isRed(x.right.left))
            x = moveRedRight(x);

        x.right = deleteMax(x.right);

        return balance(x);
    }

    //删除结点
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return;

        if (!isRed(root.left) && !isRed(root.right))
            root.color = red;

        root = delete(root,key);
        if (!isEmpty()) root.color = black;
    }

    private Node delete(Node x, Key key) {
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            if (!isRed(x.left) && !isRed(x.left.left))
                x = moveRedLeft(x);
            x.left = delete(x.left,key);
        }

        else if (cmp > 0) {
            if (isRed(x.left))
                x = rotateRight(x);
            if (!isRed(x.right) && !isRed(x.right.left))
                x = moveRedRight(x);
            x.right = delete(x.right,key);
        }

        else {
            //如果x为待删除键时，还需要将子结点调成非2-结点，否则在删除
            if (isRed(x.left))
                x = rotateRight(x);
            if (!isRed(x.right) && !isRed(x.right.left))
                x = moveRedRight(x);
        }
    }



    //判断是否为红结点
    private boolean isRed(Node x) {
        if (x == null) return black;
        return x.color == red;
    }

    //当出现破坏二叉平衡树规则的时候可以通过旋转操作来进行修复
    //左（右）旋转操作的本质是将原来父结点的左（右）链接旋转为新父结点的右（左）链接以此将树重新归为平衡状态
    //通过旋转可以将原本的父结点变为新父结点的左孩子或者有右孩子，以此来控制树的高度保持平衡

    //左旋转
    private Node rotateLeft(Node h) {
        Node x = h.right; //保存h的右孩子x
        h.right = x.left; //将x的左孩子置为h的右孩子
        x.left = h; //将x的左孩子置为h
        x.color = h.color; //将由父结点指向x的链接颜色置为原先指向h的颜色
        h.color = red; //指向h的链接颜色置为红色
        x.N = h.N;
        h.N = size(h.right) + size(h.left) + 1;
        return x;
    }

    //右旋转
    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = red;
        x.N = h.N;
        h.N = size(x.right) + size(x.left) + 1;
        return x;
    }

    //颜色转换
    //颜色变换可以看作用来拆分4-结点或者组合成为4-结点
    private void flipColor(Node x) {
        x.color = !x.color;
        x.left.color = !x.left.color;
        x.right.color = !x.right.color;
    }

    //平衡函数，用于保证树的平衡
    private Node balance(Node x) {
        //右链接红色，左链接黑色，左旋转，保证左倾
        if (isRed(x.right) && !isRed(x.left)) x = rotateLeft(x);

        //左链接红色，左子结点的左连接是红色，右旋转，保证没有连续的红结点
        //没有连续的红结点是为了保证最长路径不会长于最短路径的2倍，因为红黑树中是关于黑链接完美平衡的
        //这里不需要担心x.left.left是个非法指针，因为如果x.left.left如果不存在，则x.left必为null，则isRed(x.left)必为false，不会执行isRed(x.left.left)
        if (isRed(x.left) && isRed(x.left.left)) x = rotateRight(x);

        //左链接红色，右链接红色，改变颜色,这一步会消除4-结点
        if (isRed(x.right) && isRed(x.left)) flipColor(x);

        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    //删除最小键过程中调整2结点
    private Node moveRedLeft(Node x) {
        //如果结点x的左右子结点均为2结点，则将三者拼成临时的4结点即可，此时父结点由3结点变为2结点或者由4结点变为3结点
        //这里出现2结点没有关系，因为我们最终只关心待删除结点不是2结点即可
        //flipColor函数可以理解为将父结点、左右子结点拉在一起组合成一个临时的4结点
        flipColor(x);
        //如果结点x的右子结点为3结点，那么我们需要从右子结点中借一个键移动到父结点中，然后将父结点中的最小键（父结点一定不是2结点）移动到左子结点中合并为一个3结点
        if (isRed(x.right.left)) {
            x = rotateRight(x);
            //处理完以后再次变换颜色，将新创建的2结点与父亲节点、兄弟结点解开
            flipColor(x);
        }
        return x;
    }

    //删除最大键过程中调整2结点
    private Node moveRedRight(Node x) {
        flipColor(x);

        if (isRed(x.left.left)) {
            x.left = rotateLeft(x.left);
            x = rotateRight(x);
            //处理完以后再次变换颜色，将新创建的2结点与父亲节点、兄弟结点解开
            flipColor(x);
        }
        return x;
    }

    //求最小值
    public Key min() {return min(root).key;}
    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    //求最大值
    public Key max() {return max(root).key;}
    private Node max(Node x) {
        if (x.right == null) return x;
        return max(x.right);
    }

    public int size() {return size(root);}
    private int size(Node x) {return x == null ? 0 : x.N;}

    public boolean isEmpty() { return root == null;}

    public boolean contains(Key key) { return get(key) != null;}

}
