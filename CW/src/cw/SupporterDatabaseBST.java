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

    /**
     * Empties the database.
     * @pre true
     */
    @Override
    public void clear() {
        size = 0;
        head = null;
    }

    /**
     * Determines whether a Supporter name exists as a key inside the database
     * @pre true
     * @param name the Supporter name (key) to locate
     * @return true iff the name exists as a key in the database
     */
    @Override
    public boolean containsName(String name) {
        return get(name) != null;
    }

    /**
     * Returns a Supporter object mapped to the supplied name.
     * @pre true
     * @param name The Supporter name (key) to locate
     * @return the Supporter object mapped to the key name if the name
     * exists as key in the database, otherwise null
     */
    @Override
    public Supporter get(String name) {
        Supporter ans = null;
        
        if(head != null){
            node a = head.get(name, 0);
            if(a != null){
                ans = a.getData();
            }
            else System.out.println("There is no Supporter with this name");
        }
        
        return ans;
    }

    /**
     * Returns the number of supporters in the database
     * @pre true
     * @return number of supporters in the database. 0 if empty
     */
    @Override
    public int size() {
        return size;
    }
    
    /**
     * Determines if the database is empty or not.
     * @pre true
     * @return true iff the database is empty
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Inserts a supporter object into the database, with the key of the supplied
     * supporter's name.
     * Note: If the name already exists as a key, then then the original entry is
     * overwritten. This method should return the previous associated value if one exists, otherwise null
     * @pre true
     */
    @Override
    public Supporter put(Supporter supporter) {
        int num = 0;
        node n = new node(supporter);
        if(head == null){
            head = n;
            size++;
            num++;
        }
        else{
            num = head.add(n);
        }
        
        System.out.println(color(supporter.getName()) + "(" + color(supporter.getID()) + ") has been added");
        System.out.println(num + " of cells have been checked");
        System.out.println("Now you have " + size + " elements in your list");
        System.out.println("Depth is " + depth());
        
        return n.data;
    }

    /**
     * Removes and returns a supporter from the database, with the key
     * the supplied name.
     * @param name The name (key) to remove.
     * @pre true
     * @return the removed supporter object mapped to the name, or null if
     * the name does not exist.
     */
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
        
        System.out.println(ans == null ? "There is no supporter with this name" : color(ans.getName()) + "(" + color(ans.getID()) + ") has been deleted");
        System.out.println((ans == null ? "You still have " : "Now you have ") + size + " elements in your list");
        System.out.println("Detph is " + depth());
        
        return ans;
    }

    /**
     * Prints the names and IDs of all the supporters in the database in alphabetic order.
     * @pre true
     */
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
    
    private int depth(){
        return head == null ? 0 : head.subDeph();
    }
    
    private String color(String text){
        return "\033[34m" + text + "\033[0m";
    }
    
    
    
    private class node{
        private Supporter data;
        private node left = null, right = null;
        
        public node(Supporter data){
            this.data = data;
        }
        
        public int add(node supporter){
            int ans = 1;
            if(supporter.getData().getName().compareTo(data.getName()) < 0){
                System.out.println("    " + color(supporter.getData().getName()) + " is less, than " + color(data.getName()) + ", so we go left");
                if(left == null){
                    System.out.println("    As left is empty, we add " + color(supporter.getData().getName()) + " there");
                    left = supporter;
                    size++;
                    ans++;
                }
                else{
                    System.out.println("    As left is busy, we compare " + color(supporter.getData().getName()) + " with " + color(left.getData().getName()));
                    ans += left.add(supporter);
                }
            }
            else if(supporter.getData().getName().compareTo(data.getName()) > 0){
                System.out.println("    " + color(supporter.getData().getName()) + " is greater, than " + color(data.getName()) + ", so we go right");
                if(right == null){
                    System.out.println("    As right is empty, we add " + color(supporter.getData().getName()) + " there");
                    right = supporter;
                    size++;
                    ans++;
                }
                else{
                    System.out.println("    As right is busy, we compare " + color(supporter.getData().getName()) + " with " + color(right.getData().getName()));
                    ans += right.add(supporter);
                }
            }
            else {
                data = supporter.data;
            }
            return ans;
        }
        
        public node get(String name, int n){
            node ans = null;
            
            if(data.getName().equals(name)){
                ans = this;
                System.out.println(n + " of cells have been checked");
            }
            else if(data.getName().compareTo(name) > 0 && left != null){
                ans = left.get(name, n + 1);
            }
            else if(data.getName().compareTo(name) < 0 && right != null){
                ans = right.get(name, n + 1);
            }
            
            return ans;
        }
        
        public void print(String gap){
            if(left != null){
                left.print(gap);
            }
            System.out.println(color(data.getName()) + gap.substring(data.getName().length()) + color(data.getID()));
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
                System.out.println("This node has both nodes");
            }
            else if(left == null){
                ans = right;
                System.out.println("This node has right node");
            }
            else if(right == null){
                ans = left;
                System.out.println("This node has left node");
            }
            else System.out.println("This node is leaf");
            
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
