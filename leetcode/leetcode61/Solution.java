package leetcode61;

import java.util.Scanner;

/**
 * @author Zhang Bowen
 * @Description
 * @ClassName Solution
 * @date 2021.01.26 20:28
 */
public class Solution {

    //时间复杂度O(n*k)，太慢了
    public ListNode rotateRight2(ListNode head, int k) {
        if (head == null)
            return null;

        //如果链表只包含一个结点，则直接返回
        if (head.next == null)
            return head;

        ListNode prior;
        ListNode current;
        for (int i = 0; i < k; i++){
            prior = null;
            current = head;
            while (current.next != null){
                prior = current;
                current = current.next;
            }
            //将prior.next指向null
            prior.next = null;
            //将current.next指向head
            current.next = head;
            //将head指向current
            head = current;
        }
        return head;
    }

    public ListNode rotateRight(ListNode head, int k){
        //链表为空返回null
        if (head == null)
            return null;

        //如果链表只包含一个结点，则直接返回
        if (head.next == null)
            return head;

        ListNode current = head;
        int N = 1;

        //找到链表中的尾结点
        while (current.next != null){
            N++;
            current = current.next;
        }
        //将尾结点指向首结点，使链表构成环
        current.next = head;

        int pos = N-(k%N);

        if (pos == N)
            return head;

        for (int i = 0; i < pos; i++){
            current = current.next;
        }
        head = current.next;
        current.next = null;
        return head;
    }
}
