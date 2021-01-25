package stack;

import queue.Queue;

import javax.xml.soap.Node;
import java.util.Iterator;

/**
 * @author Zhang Bowen
 * @Description
 * @ClassName Stack
 * @date 2021.01.25 20:25
 */

/*
用链表实现后进先出（LIFO）的栈

Item是类型参数，用于表示用例将会使用的某种具体类型的象征性占位符
 */
public class Stack<Item> implements Iterable<Item> {
    private Node top;
    private int N;

    private class Node{
        Item item;
        Node next;
    }

    public Stack() {
        top = null;
        N = 0;
    }

    public boolean isEmpty(){
        return top==null;
    }

    public int size(){
        return N;
    }

    //从链表头插入元素
    public void push(Item item){
        Node oldTop = top;
        top = new Node();
        top.item = item;
        top.next = oldTop;
        N++;
    }

    //从链表头删除元素
    public Item pop(){
        Item item = top.item;
        //将node类型的引用top指向原先栈顶元素的下一元素，原先栈顶元素（node对象）没有被引用，因此会被回收
        top = top.next;
        N--;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new Stack.ListIterator();
    }

    private class ListIterator implements Iterator<Item>
    {
        private Stack.Node current = top;
        public boolean hasNext(){
            return current != null;
        }
        public void remove(){ }
        public Item next(){
            Item item = top.item;
            current = current.next;
            return item;
        }
    }
}
