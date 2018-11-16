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

    @Override
    public void clear() {
        table = new Supporter[11];
        for(int n = 0; n < table.length; n++) table[n] = null;
        size = 0;
    }

    @Override
    public boolean containsName(String name) {
        return get(name) != null;
    }

    @Override
    public Supporter get(String name) {
        Supporter ans = null;
        
        int index = hashFunction(name);
        System.out.println("Hash code for this element is " + index);
        System.out.println("Cell " + index + (table[index] == null ? " is empty" : (" is occupied by " + table[index].getName() + "(" + table[index].getID() + ")")));
        if(table[index] != null){
            int n = 1, i = index;
            while(n < table.length && (table[index] == null || !table[index].getName().equals(name))){
                index = probeFunction(i, n);
                n++;
                System.out.println("Cell " + index + (table[index] == null ? " is empty" : (" is occupied by " + table[index].getName() + "(" + table[index].getID() + ")")));
            }
            if(table[index] != null && table[index].getName().equals(name)) ans = table[index];
        }
        System.out.println(ans == null ? "There is no supporter with this name." : "Supporter " + ans.getName() + "(" + ans.getID() + ") is found");
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
        Supporter ans = null;
        int index = hashFunction(supporter.getName());
        System.out.println((r ? ">" : "") + "Hash code for this element is " + index);
        
        if(table[index] == null){
            table[index] = supporter;
            ans = supporter;
            System.out.println((r ? ">" : "") + ans.getName() + "(" + ans.getID() + ") has been added");
        }
        else{
            System.out.println((r ? ">" : "") + "That cell is occupied by " + table[index].getName() + "(" + table[index].getID() + ")");
            int i = 1;
            int n = index;
            while(table[index] != null && i < table.length) {
                index = probeFunction(n, i);
                i++;
                System.out.println((r ? ">" : "") + "   " + "Cell " + index + (table[index] == null ? " is free" : (" is occupied by " + table[index].getName() + "(" + table[index].getID() + ")")));
            }
            if(table[index] == null) {
                table[index] = supporter;
                ans = supporter;
                System.out.println((r ? ">" : "") + ans.getName() + "(" + ans.getID() + ") has been added");
            }
            else {
                System.out.println((r ? ">" : "") + "As a space for this supporter is not found, resize is needed.");
                resize();
                put(supporter);
            }
        }
        
        if(ans != null) size++;
        
        System.out.println((r ? ">" : "") + "The load factor is " + (int)(((double)size / table.length) * 100) + "%");
        if((double)size / table.length >= 0.75) {
            System.out.println((r ? ">" : "") + "Resize is needed");
            resize();
        }
        else System.out.println((r ? ">" : "") + "Now you have " + size + " elements in your list");
        return ans;
    }

    @Override
    public Supporter remove(String name) {
        Supporter ans = null;
        
        int index = hashFunction(name);
        System.out.println("Hash code for this element is " + index);
        System.out.println("Cell " + index + (table[index] == null ? " is empty" : (" is occupied by " + table[index].getName() + "(" + table[index].getID() + ")")));
        if(table[index] != null){
            int n = 1, i = index;
            System.out.println(table[index].getName().equals(name) ? (name + " is found") : (name + " is not there"));
            while(n < table.length && (table[index] == null || !table[index].getName().equals(name))){
                index = probeFunction(i, n);
                n++;
                System.out.println("Cell " + index + (table[index] == null ? " is empty" : (" is occupied by " + table[index].getName() + "(" + table[index].getID() + ")")));
            }
            if(table[index] != null && table[index].getName().equals(name)){
                ans = table[index];
                table[index] = null;
                size--;
            }
        }
        System.out.println(ans == null ? "There is no supporter with this name." : "Supporter " + ans.getName() + "(" + ans.getID() + ") is removed\nNow you have " + size + " elements in your list");
        return ans;
    }

    @Override
    public void printSupportersOrdered() {
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
        for(Supporter n : list) System.out.println(n.getName() + gap.substring(n.getName().length()) + n.getID());
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
        System.out.println("Resizeing has been started");
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
        System.out.println("Resizeing has been finished");
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
}
