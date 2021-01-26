package leetcode20;

import java.util.Scanner;

/**
 * @author Zhang Bowen
 * @Description
 * @ClassName Solution
 * @date 2021.01.26 00:25
 */

public class Solution {
    static class Stack {
        private Node top;
        class Node{
            char ch;
            Node next;
        }

        public Stack() {
            top = null;
        }

        public void push(char ch){
            Node oldTop = top;
            top = new Node();
            top.ch = ch;
            top.next = oldTop;
        }

        public void pop(){
            top = top.next;
        }

        public boolean isEmpty(){
            return top==null;
        }

        public boolean isPair(char ch){
            //如果栈为空，返回false
            if (isEmpty()) return false;

            if (ch == ')'){
                return top.ch == '(';
            }

            else if (ch == '}'){
                return top.ch == '{';
            }

            else if (ch == ']'){
                return top.ch == '[';
            }

            //其他情况返回false
            else return false;
        }
    }


    public static boolean isValid(String s) {
        Stack stack = new Stack();
        for (int i = 0; i < s.length(); i++){
            if (stack.isPair(s.charAt(i)))
                stack.pop();
            else stack.push(s.charAt(i));
        }

        return stack.isEmpty();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String str = input.nextLine();
        boolean flag = isValid(str);
        System.out.print(flag);
    }
}
