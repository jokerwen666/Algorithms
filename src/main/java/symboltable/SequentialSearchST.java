package symboltable;

import base.Queue;

public class SequentialSearchST<Key, Value> {
    private Node first;
    private int size;

    //接口作为返回值，返回的是具体实现了该接口的子类
    public Iterable<? extends Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (Node x = first; x != null; x = x.next)
            queue.enqueue(x.key);
        return queue;
    }

    private class Node {
        Key key;
        Value val;
        Node next;

        public Node(Key key, Value val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    public int size() {
        return size;
    }

    public boolean contains(Key key) {
        if (key == null)
            throw new NullPointerException("key cannot be null");

        return get(key) != null;
    }

    public Value get(Key key) {
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key))
                return x.val;
        }
        return null;
    }

    public void put(Key key, Value val) {
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.val = val;
                return;
            }
        }
        //没有命中，在链表头插入新结点
        first = new Node(key, val, first);
        size++;
    }

    public boolean delete(Key key) {
        if (key == null)
            throw new NullPointerException("key cannot be null");

        //如果链表为空无法删除
        if (first == null)
            return false;

        //如果key是链表首元素，将first后移
        if (first.key == key) {
            first = first.next;
            return true;
        }

        //定义一个前置引用指向当前结点的父结点
        Node prior = first;
        //x从first的下一结点开始
        for (Node x = first.next; x != null; x = x.next) {
            //命中，将prior指向x的下一结点，x结点没有被引用会被回收
            if (key.equals(x.key)) {
                prior.next = x.next;
                return true;
            }
            //没有命中，prior和x向后移动
            prior = x;
        }
        //查无此key，返回false
        return false;
    }
}
