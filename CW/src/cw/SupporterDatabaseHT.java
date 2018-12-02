/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cw;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author 17075970
 */
public class SupporterDatabaseHT implements ISupporterDatabase {
    
    private Supporter[] table;
    private int size;
    private boolean r = false;
    
    public SupporterDatabaseHT(){
        clear();
        System.out.println("Hash Table is created.");
    }
    
    /**
     * Empties the database.
     * @pre true
     */
    @Override
    public void clear() { //O(1)
        table = new Supporter[11];
        for(int n = 0; n < table.length; n++) table[n] = null;
        size = 0;
    }

    /**
     * Determines whether a Supporter name exists as a key inside the database
     * @pre true
     * @param name the Supporter name (key) to locate
     * @return true iff the name exists as a key in the database
     */
    @Override
    public boolean containsName(String name) { //O(1)
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
    public Supporter get(String name) { //O(1)
        Supporter ans = null;
        
        int index = hashFunction(name);
        System.out.println("Hash code for this element is " + index);
        System.out.println("Cell " + index + (table[index] == null ? " is empty" : (" is occupied by " + color(table[index].getName()) + "(" + color(table[index].getID()) + ")")));
        if(table[index] != null){
            int n = 1, i = index;
            while(n < table.length && (table[index] == null || !table[index].getName().equals(name))){
                index = probeFunction(i, n);
                n++;
                System.out.println("Cell " + index + (table[index] == null ? " is empty" : (" is occupied by " + color(table[index].getName()) + "(" + color(table[index].getID()) + ")")));
            }
            if(table[index] != null && table[index].getName().equals(name)) ans = table[index];
        }
        System.out.println(ans == null ? "There is no supporter with this name." : "Supporter " + color(ans.getName()) + "(" + color(ans.getID()) + ") is found");
        return ans;
    }

    /**
     * Returns the number of supporters in the database
     * @pre true
     * @return number of supporters in the database. 0 if empty
     */
    @Override
    public int size() { //O(1)
        return size;
    }

    /**
     * Determines if the database is empty or not.
     * @pre true
     * @return true iff the database is empty
     */
    @Override
    public boolean isEmpty() { //O(1)
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
    public Supporter put(Supporter supporter) { //O(1)
        Supporter ans = null;
        int index = hashFunction(supporter.getName());
        System.out.println((r ? ">" : "") + "Hash code for this element is " + index);
        
        if(table[index] == null){
            table[index] = supporter;
            ans = supporter;
            size++;
            System.out.println((r ? ">" : "") + color(ans.getName()) + "(" + color(ans.getID()) + ") has been added");
        }
        else{
            System.out.println((r ? ">" : "") + "   Cell is occupied by " + color(table[index].getName()) + "(" + color(table[index].getID()) + ")");
            int i = 1;
            int n = index;
            
            while(table[index] != null && !table[index].getName().equals(supporter.getName()) && i < table.length) {
                index = probeFunction(n, i);
                i++;
                System.out.println((r ? ">" : "") + "   Cell " + index + (table[index] == null ? " is free" : (" is occupied by " + color(table[index].getName()) + "(" + color(table[index].getID()) + ")")));
            }
            if(i < table.length) {
                if(table[index] == null) size++;
                ans = supporter;
                System.out.println((r ? ">" : "") + color(ans.getName()) + "(" + color(ans.getID()) + (table[index] == null ? ") has been added" : ") has been changed"));
                table[index] = supporter;
            }
            else {
                System.out.println((r ? ">" : "") + "As a space for this supporter is not found, resizing is needed.");
                resize();
                put(supporter);
            }
        }
        
        System.out.println((r ? ">" : "") + "The load factor is " + (int)(((double)size / table.length) * 100) + "%");
        if((double)size / table.length >= 0.75) {
            System.out.println((r ? ">" : "") + "Resizing is needed");
            resize();
        }
        else System.out.println((r ? ">" : "") + "Now you have " + size + " elements in your list");
        return ans;
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
    public Supporter remove(String name) { //O(1)
        Supporter ans = null;
        
        int index = hashFunction(name);
        System.out.println("Hash code for this element is " + index);
        System.out.println("Cell " + index + (table[index] == null ? " is empty" : (" is occupied by " + color(table[index].getName()) + "(" + color(table[index].getID()) + ")")));
        if(table[index] != null){
            int n = 1, i = index;
            System.out.println(table[index].getName().equals(name) ? (name + " is found") : (name + " is not there"));
            while(n < table.length && (table[index] == null || !table[index].getName().equals(name))){
                index = probeFunction(i, n);
                n++;
                System.out.println("Cell " + index + (table[index] == null ? " is empty" : (" is occupied by " + color(table[index].getName()) + "(" + color(table[index].getID()) + ")")));
            }
            if(table[index] != null && table[index].getName().equals(name)){
                ans = table[index];
                table[index] = null;
                size--;
            }
        }
        System.out.println(ans == null ? "There is no supporter with this name." : "Supporter " + color(ans.getName()) + "(" + color(ans.getID()) + ") is removed\nNow you have " + size + " elements in your list");
        return ans;
    }

    /**
     * Prints the names and IDs of all the supporters in the database in alphabetic order.
     * @pre true
     */
    @Override
    public void printSupportersOrdered() { //O(log n)
        Supporter list[] = new Supporter[size];
        int index = 0;
        for(int n = 0; n < table.length; n++){
            if(table[n] != null){
                list[index] = table[n];
                index++;
            }
        }
        
        sort(list, 0, list.length-1);
        
        String gap = "         ";
        int len = 0;
        for(Supporter n : list) if(len < n.getName().length()) len = n.getName().length();
        for(int n = 0; n < len; n++) gap += " ";
        if(!isEmpty()) System.out.println("Name" + gap.substring(4) + "ID");
        for(Supporter n : list) System.out.println(color(n.getName()) + gap.substring(n.getName().length()) + color(n.getID()));
    }
    
    private int hashFunction(String name){
        return name.length() > 1 ? ((int)name.charAt(0) + (int)name.charAt(name.length()-1)) % table.length : (int)name.charAt(0) % table.length;
    }
    
    private int probeFunction(int index, int i){
        int ans = index + i * i;
        while(ans >= table.length) ans -= table.length;
        return ans;
    }
    private void resize(){
        r = true;
        System.out.println("Resizing has been started");
        int oldLen = table.length;
        Supporter[] tempt = table;
        table = new Supporter[tempt.length*2];
        size = 0;
        for(int n = 0; n < tempt.length; n++){
            if(tempt[n] != null){
                System.out.println("***************************************************************************************************");
                put(tempt[n]);
            }
        }
        System.out.println("Size was changed from " + oldLen + " to " + table.length);
        System.out.println("Resizing has been finished");
        r = false;
    }
    public void sort(Supporter[] list, int low, int high) {

        int mid = low + ((high - low) / 2);

        if (high - low > 1) {
            sort(list, low, mid);
            sort(list, mid + 1, high);
        }

        Supporter[] buffer = new Supporter[high - low + 1];

        int m1 = low, m2 = mid + 1;

        for (int i = 0; i < buffer.length; i++) {
            if (m1 < mid + 1 && m2 < high + 1) {
                if (list[m1].getName().compareTo(list[m2].getName()) < 0) {
                    buffer[i] = list[m1];
                    m1++;
                } else {
                    buffer[i] = list[m2];
                    m2++;
                }
            }
            else if(m1 >= mid + 1){
                buffer[i] = list[m2];
                m2++;
            }
            else if(m2 >= high + 1){
                buffer[i] = list[m1];
                m1++;
            }
        }
        
        for(int n = low; n < high + 1; n ++){
            list[n] = buffer[n - low];
        }
    }
    
    private String color(String text){
        return "\033[34m" + text + "\033[0m";
    }
}
