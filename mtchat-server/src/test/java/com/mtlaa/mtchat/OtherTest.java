package com.mtlaa.mtchat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Supplier;


@Slf4j
public class OtherTest {
    @Test
    public void test1() throws InterruptedException {
        System.out.println(maxLenSub(""));
        System.out.println(maxLenSub("aaaaaaaaa"));
        System.out.println(maxLenSub("abcde"));
        System.out.println(maxLenSub("aajaaijkon"));
        System.out.println(maxLenSub("a"));
        System.out.println(maxLenSub(""));
    }
    private String maxLenSub(String s){
        if(s.isEmpty()) return "";
        Map<Character, Integer> map = new HashMap<>();
        int left = 0, right = 0;
        int maxLen = 0;
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!map.containsKey(c)){
                map.put(c, i);
            } else{
                if(map.get(c)+1>start){
                    start = map.get(c) + 1;
                }
                map.put(c, i);
            }
            if(i-start+1>maxLen){
                right = i;
                left = start;
                maxLen = i - start + 1;
            }   
        }
        return s.substring(left, right+1);
    }











}

class MinStack {
    Deque<Integer> data;
    Deque<Integer> minStack;

    public MinStack() {
        data = new ArrayDeque<>();
        minStack = new ArrayDeque<>();
    }

    public void push(int val) {
        data.push(val);
        if (minStack.isEmpty() || val <= minStack.peek()){
            minStack.push(val);
        }
    }

    public void pop() {
        int val = data.pop();
        if (!minStack.isEmpty() && val == minStack.peek()){
            minStack.pop();
        }
    }

    public int top() {
        return data.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}

class ListNode {
    int val;
    ListNode next;
    public ListNode() {}
    public ListNode(int val) { this.val = val; }
    public ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }