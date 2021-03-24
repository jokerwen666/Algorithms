package base;

/**
 * @author Zhang Bowen
 * @Description
 * @ClassName MaxPQ
 * @date 2021.01.25 22:09
 */

/*
用链表实现优先队列
 */
public class MaxPQ<Key extends Comparable<Key>> {
    private int N;
    private Node top;
    private class Node{
        Key key;
        Node next;
    }

    public MaxPQ() {
        top = null;
        N = 0;
    }

    public void insert(Key v) {
        Node current = top;
        Node prior = null;

        //当优先队列非空，同时当待插入结点值小于当前结点时，循环继续
        //要求队列非空是为了避免，current=null, 从而current.key无意义
        while (v.compareTo(current.key) < 0 && !isEmpty()){
            //当遍历到链表尾都没有找到比待插入元素大或者相等的值时，表明待插入元素是新的最小值,此时在链尾插入元素
            if(current.next == null){
                //保存当前current（链尾结点）
                Node oldCurrent = current;
                //构造新的current结点，覆盖掉之前current结点
                current = new Node();
                current.key = v;
                //将current.next指向空
                current.next = null;
                //将oldCurrent.next指向current
                oldCurrent.next = current;
                N++;
                return;
            }

            //将prior指向当前结点，将current指向下一结点
            prior = current;
            current = current.next;
        }

        //当prior为空时，说明要在链头插入元素，此时和栈的push操作一样
        if(prior == null){
            Node oldTop = top;
            top = new Node();
            top.key = v;
            top.next = oldTop;
        }

        //当prior不为空时，说明要在链表中间插入元素
        else {
            //首先构造新的current结点，覆盖掉之前current结点(此时不需要保存是因为，有prior.next指向之前的current结点)
            current = new Node();
            current.key = v;
            //然后将current.next指向之前的current结点
            current.next = prior.next;
            //最后将prior.next指向current结点
            prior.next = current;
        }

        N++;
    }

    public Key max() {
        return top.key;
    }

    public Key delMax() {
        Key key = top.key;
        top = top.next;
        N--;
        return key;
    }

    public boolean isEmpty() {
        return top==null;
    }

    public int size(){
        return N;
    }
}
