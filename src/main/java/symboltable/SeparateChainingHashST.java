package symboltable;


import java.util.*;

/*
基于拉链法的散列表
 */
public class SeparateChainingHashST<Key, Value> {
    HashSet<Integer> hashSet = new HashSet<Integer>();


    private static final int INIT_CAPACITY = 4;
    private int n;
    private int m;
    private SequentialSearchST<Key,Value>[] st;

    public SeparateChainingHashST() {
        this(INIT_CAPACITY);
    }

    public SeparateChainingHashST(int M) {
        this.m = M;
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
        for (int i = 0; i < M; i++)
            st[i] = new SequentialSearchST<Key, Value>();
    }

    private void resize(int chains) {
        SeparateChainingHashST<Key,Value> temp = new SeparateChainingHashST<Key, Value>(chains);
        for (int i = 0; i < m; i++) {
            for (Key key : st[i].keys()) {
                temp.put(key, st[i].get(key));
            }
        }
        this.m  = temp.m;
        this.n  = temp.n;
        this.st = temp.st;
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff % m);
    }

    public Value get(Key key) {
        return st[hash(key)].get(key);
    }

    public void put(Key key, Value val) {
        if (key == null)
            throw new NullPointerException("key cannot be null");

        //赋值如果是null，则相当于删除
        if (val == null) {
            remove(key);
            return;
        }

        //当每条链的平均长度为8时，将散列表大小翻倍
        if (n >= 8*m) resize(2*m);

        int i = hash(key);
        if (!st[i].contains(key)) n++;
        st[i].put(key,val);
    }

    public void remove(Key key) {
        if (key == null)
            throw new NullPointerException("key cannot be null");
        if (st[hash(key)].delete(key))
            n--;

        //当链表数目大于初始大小且每条链上的平均长度小于2时，说明空余太多了，此时将散列表大小缩小一半
        if (m > INIT_CAPACITY && n < 2*m) resize(m/2);
    }


}
