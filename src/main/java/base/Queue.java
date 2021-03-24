package base;

import java.util.Iterator;

/**
 * @author Zhang Bowen
 * @Description
 * @ClassName Queue
 * @date 2021.01.25 17:41
 */

/*
用链表实现一个先进先出的队列
 */
public class Queue<Item> implements Iterable<Item>{
    private Node first;
    private Node last;
    private int N;

    private class Node{
        Item item;
        Node next;
    }

    public boolean isEmpty(){
        return first == null;
    }

    public int size(){
        return N;
    }

    //从链表尾添加元素
    public void enqueue(Item item){
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty())
            first = last;
        else
            oldLast.next = last;
        N++;

    }

    //从链表头删除元素
    public Item dequeue(){
        Item item = first.item;
        first = first.next;
        if (isEmpty())
            last = null;
        N--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;
        public boolean hasNext(){
            return current != null;
        }
        public void remove(){ }
        public Item next(){
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
