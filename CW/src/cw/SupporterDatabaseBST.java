/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cw;

import java.io.FileInputStream;
import java.util.Scanner;

/**
 *
 * @author 17075970
 */
public class SupporterDatabaseBST implements ISupporterDatabase{
    int size;
    node head;
    
    public SupporterDatabaseBST(){
        clear();
        System.out.println("Binary Search Tree is created");
    }

    @Override
    public void clear() {
        size = 0;
        head = null;
    }

    @Override
    public boolean containsName(String name) {
        return get(name) != null;
    }

    @Override
    public Supporter get(String name) {
        Supporter ans = null;
        
        if(head != null){
            ans = head.get(name, 0).getData();
        }
        
        return ans;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Supporter put(Supporter supporter) {
        int num = 0;
        size++;
        node n = new node(supporter);
        if(head == null){
            head = n; 
        }
        else{
            num = head.add(n);
        }
        
        System.out.println(supporter.getName() + "(" + supporter.getID() + ") has been added");
        System.out.println(num + " of cells have been checked");
        System.out.println("Now you have " + size + " elements in your list");
        System.out.println("Deph is " + deph());
        
        return n.data;
    }

    @Override
    public Supporter remove(String name) {
        Supporter ans = null;
        
        node prev = null;
        node current = head;
        while(current != null && !current.getData().getName().equals(name)){
            prev = current;
            if(current.getData().getName().compareTo(name) > 0){
                current = current.getLeft();
            }
            else{
                current = current.getRight();
            }
        }
        
        if(current != null){
            ans = current.getData();
            
            if(prev == null){
                head = head.remove();
            }
            else{
                if(current.getData().getName().compareTo(prev.getData().getName()) < 0){
                    prev.setLeft(current.remove());
                }
                else{
                    prev.setRight(current.remove());
                }
            }
        }
        
        if(ans != null) size--;
        
        return ans;
    }

    @Override
    public void printSupportersOrdered() {
        if(head != null){
            int len = head.getMaxLen(0);
            String gap = "         ";
            for(int n = 0; n < len; n++) gap += " ";
            System.out.println("Name" + gap.substring(4) + "ID");
            head.print(gap);
        }
    }
    
    private int deph(){
        return head == null ? 0 : head.subDeph();
    }
    
    private class node{
        private Supporter data;
        private node left = null, right = null;
        
        public node(Supporter data){
            this.data = data;
        }
        
        public int add(node newbie){
            int ans = 1;
            if(newbie.getData().getName().compareTo(data.getName()) < 0){
                System.out.println("    " + newbie.getData().getName() + " is less, than " + data.getName() + ", so we go left");
                if(left == null){
                    System.out.println("    " + "As left is empty, we add " + newbie.getData().getName() + " there");
                    left = newbie;
                }
                else{
                    System.out.println("    " + "As left is busy, we compare " + newbie.getData().getName() + " with " + left.getData().getName());
                    ans += left.add(newbie);
                }
            }
            else{
                System.out.println("    " + newbie.getData().getName() + " is greater, than " + data.getName() + ", so we go right");
                if(right == null){
                    System.out.println("    " + "As right is empty, we add " + newbie.getData().getName() + " there");
                    right = newbie;
                }
                else{
                    System.out.println("    " + "As right is busy, we compare " + newbie.getData().getName() + " with " + right.getData().getName());
                    ans += right.add(newbie);
                }
            }
            return ans;
        }
        
        public node get(String name, int n){
            node ans = null;
            
            if(data.getName().equals(name)){
                ans = this;
                System.out.println(n + " of cells have been checked");
            }
            else if(data.getName().compareTo(name) > 0 && left != null) ans = left.get(name, n + 1);
            else if(data.getName().compareTo(name) < 0 && right != null)ans = right.get(name, n + 1);
            
            return ans;
        }
        
        public void print(String gap){
            if(left != null){
                left.print(gap);
            }
            System.out.println(data.getName() + gap.substring(data.getName().length()) + data.getID());
            if(right != null){
                right.print(gap);
            }
        }
        
        public int getMaxLen(int len){
            int ans = len;
            if(left != null) ans = left.getMaxLen(ans);
            if(ans < data.getName().length()) ans = data.getName().length();
            if(right != null) ans = right.getMaxLen(ans);
            return ans;
        }
        
        public node remove(){
            node ans = null;
            
            if(left != null && right != null){
                right.add(left);
                ans = right;
            }
            else if(left == null) ans = right;
            else if(right == null) ans = left;
            
            return ans;
        }
        
        public int subDeph(){
            int l = 0, r = 0;
            if(left != null) l = left.subDeph();
            if(right != null) r = right.subDeph();
            return 1 + (l < r ? r : l);
        }
        
        public Supporter getData(){
            return data;
        }
        
        public node getLeft(){
            return left;
        }
        
        public node getRight(){
            return right;
        }
        
        public void setLeft(node n){
            left = n;
        }
        
        public void setRight(node n){
            right = n;
        }
    }
    
}
