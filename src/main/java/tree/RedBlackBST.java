package tree;

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
    private int size; //结点数目

    private class Node {
        Key key; //键
        Value val; //值
        Node right, left; //左右子结点
        Node parent; //父亲结点
        boolean color; //结点颜色

        public Node(Key key, Value val,  boolean color) {
            this.key = key;
            this.val = val;
            this.color = color;
        }

    }

    private boolean isRed(Node p) {
        if (p == null) return black;
        return p.color == red;
    }

    private void  setColor(Node p, boolean c) {
        if (p != null)
            p.color = c;
    }

    private void flipColor(Node x) {
        x.color = !x.color;
        x.left.color = !x.left.color;
        x.right.color = !x.right.color;
    }

    private void rotateRight(Node p) {
        if (p != null) {
            //l结点保存p的左子结点
            Node l = p.left;
            //p的左子结点设为l的右子结点
            p.left = l.right;
            //如果l的右子结点不为空，则将其父结点设为p
            if (l.right != null) l.right.parent = p;
            //l的父结点设为p的父结点
            l.parent = p.parent;
            //如果p的父结点为空，说明原本p是根结点，旋转后将l设为新的根结点
            if (p.parent == null) root = l;
                //p是其父结点的右子结点，将其父节点的右子结点设为l
            else if (p.parent.right == p) p.parent.right = l;
                //p是其父结点的左子结点，将其父节点的左子结点设为l
            else p.parent.left = l;
            //l的右子结点设为p
            l.right = p;
            //p的父结点设为l
            p.parent = l;
        }
    }

    private void rotateLeft(Node p) {
        if (p != null) {
            Node r = p.right;
            p.right = r.left;
            if (r.left != null) r.left.parent = p;
            r.parent = p.parent;
            if (p.parent == null) root = r;
            else if (p.parent.right == p) p.parent.right = r;
            else p.parent.left = r;
            r.left = p;
            p.parent = r;
        }
    }

    private Node parentOf(Node p) {
        return p != null ? p.parent : null;
    }

    private Node leftOf(Node p) {
        return p != null ? p.left : null;
    }

    private Node rightOf(Node p) {
        return p != null ? p.right : null;
    }

    private boolean colorOf(Node p) {
        return p != null ? p.color :black;
    }

    public int size() {
        return size;
    }

    //非迭代实现
    public void put(Key key, Value val) {
        int cmp = 0;
        Node t = root;
        Node parent = null;

        //如果根结点为空，将根结点设置为一个新结点，根结点是黑色的
        if (root == null) {
            root = new Node(key,val,black);
            size++;
            return;
        }

        //找到待插入的位置
        while (t != null) {
            parent = t;
            cmp = key.compareTo(t.key);
            if (cmp > 0) t = t.right;
            else if (cmp < 0) t = t.left;
            //如果cmp为0说明key已经存在，此时更新该结点的val
            else {
                t.val = val;
                return;
            }
        }

        //新建一个新结点，设定其父结点，注意新建结点是红色的
        Node e = new Node(key,val,red);
        e.parent = parent;
        //为父结点设置左/右子结点
        if (cmp < 0) parent.left = e;
        else parent.right = e;

        //修正红黑树
        fixAfterInsertion(e);

        //结点数目加1
        size++;
    }

    //修正红黑树
    private void fixAfterInsertion(Node x) {
        //当前结点不为空，当前结点不为根结点，当前结点的父结点是红色
        //如果当前结点的父结点为黑色，则说明（1）其父结点是2结点，直接插入不需要修正（2）父结点是3结点，但是是高位3结点，直接插入形成4结点即可
        while (x != null && x != root && x.parent.color == red) {
            //2-3-4树中允许右链接为红色，因此要分情况讨论
            //x的父结点是其父结点的左子结点
            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                //y为x的叔结点（x父结点的兄弟结点）
                Node y = rightOf(parentOf(parentOf(x)));
                //y为红结点表示x的插入结点是个4结点
                //此时需要将原来的4结点拆分，并将原来的中间键上移：将原来的4结点拆成三个2结点，并将中间键所在结点改为红色
                if (colorOf(y) == red) {
                    setColor(parentOf(parentOf(x)),red);
                    setColor(parentOf(x),black);
                    setColor(y,black);
                    //当前结点设为中间键所在的结点，因为我们将其改为红色，相当于新的插入操作，所以要向上迭代
                    x = parentOf(parentOf(x));
                }
                //x的插入结点是3结点
                else {
                    //如果x的父结点是其父结点的右子结点，将其先右旋，将左链接统一变红
                    if (x == rightOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateLeft(x);
                    }
                    //需要注意的是，这种写法的旋转不会改变结点颜色，只是单纯的旋转，因此要自己改变颜色
                    setColor(parentOf(parentOf(x)),red);
                    setColor(parentOf(x),black);
                    rotateRight(parentOf(parentOf(x)));
                    //这里结束以后x的父结点是黑色的不需要向上迭代
                }
            }
            //x的父结点是其父结点的右子结点
            else {
                Node y = leftOf(parentOf(parentOf(x)));
                if (colorOf(y) == red) {
                    setColor(parentOf(parentOf(x)),red);
                    setColor(parentOf(x),black);
                    setColor(y,black);
                    x = parentOf(parentOf(x));
                }
                else {
                    if (x == leftOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateLeft(x);
                    }
                    setColor(parentOf(parentOf(x)),red);
                    setColor(parentOf(x),black);
                    rotateRight(parentOf(parentOf(x)));
                }
            }
        }
        root.color = black;
    }


}
