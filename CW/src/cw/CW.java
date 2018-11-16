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
public class CW {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        boolean HT = false;
        ISupporterDatabase table = new SupporterDatabaseBST();
        
        Scanner scan = new Scanner(System.in);
        System.out.print("\nEnter the command('help' to get list of commands): ");
        String command = scan.next();

        while (!command.equalsIgnoreCase("end")) {
            switch (command) {
                case "switch":
                    System.out.println();
                    table = HT ? new SupporterDatabaseBST() : new SupporterDatabaseHT();
                    HT = !HT;
                    break;
                case "get":
                    System.out.println();
                    System.out.print("Enter the name of supporter: ");
                    String name = scan.next();
                    Supporter b = table.get(name);
                    System.out.println(b.getName() + "         " + b.getID());
                    break;
                case "contains":
                    System.out.println();
                    System.out.print("Enter the name of supporter: ");
                    name = scan.next();
                    System.out.println(table.containsName(name) ? "There is supporter with this name" : "There is no supporter with this name");
                    break;
                case "add":
                    System.out.println();
                    System.out.print("Enter the name of supporter: ");
                    name = scan.next();
                    System.out.print("Enter the ID of supporter: ");
                    String ID = scan.next();
                    table.put(new Supporter(name, ID));
                    break;
                case "clear":
                    System.out.println();
                    table.clear();
                    break;
                case "load":
                    try{
                        System.out.println();
                        System.out.print("Enter the name of file: ");
                        name = scan.next();
                        FileInputStream file = new FileInputStream(name);
                        Scanner fileScan = new Scanner(file);
                        
                        while (fileScan.hasNext()) {
                            System.out.println("---------------------------------------------------------------------------------------------------");
                            String[] l = fileScan.nextLine().split(",");
                            
                            table.put(new Supporter(l[1] + " " + l[2], l[0]));
                        }
                        
                        System.out.println("Supporters successfully added");
                    }catch(FileNotFoundException e){
                        System.out.println("File not found");
                    }
                    break;
                case "show":
                    System.out.println();
                    table.printSupportersOrdered();
                    break;
                case "remove":
                    System.out.println();
                    System.out.print("Enter the name of supporter: ");
                    name = scan.next();
                    table.remove(name);
                    break;
                case "size":
                    System.out.println();
                    System.out.println("You have " + table.size() + " elements in your list");
                    break;
                case "help":
                    System.out.println();
                    System.out.println("You can use next commands:");
                    System.out.println("'add'       to add new supporter in list");
                    System.out.println("'clear'     to remove all supporters from the list");
                    System.out.println("'load'      to upload several supporters from file");
                    System.out.println("'remove'    to remove one supporter from the list");
                    System.out.println("'show'      to display all supporters in the list in the alphabetical order");
                    System.out.println("'switch'    to change type of the list");
                    System.out.println("'size'      to get amount of supporters in your list");
                    System.out.println("'contains'  to know is there supporter with this name");
                    System.out.println("'get'       to get information about supporter");
                    break;
                default:
                    System.out.println();
                    System.out.println("Illegal command");
                    break;
            }

            System.out.print("\nEnter the command('help' to get list of commands): ");
            command = scan.next();

        }

        //SupporterDatabaseHT table1 = new SupporterDatabaseHT();
        //table.put(new Supporter("c", "1"));
        /*table.put(new Supporter("e", "2"));
        table.put(new Supporter("a", "3"));
        table.put(new Supporter("b", "4"));
        table.put(new Supporter("h", "5"));
        table.put(new Supporter("f", "6"));
        table.put(new Supporter("g", "7"));
        table.put(new Supporter("d", "8"));
        
        table.lodadFromFile("names.txt");*/
 /*table.printSupportersOrdered();
        
        SupporterDatabaseBST table1 = new SupporterDatabaseBST();*/
 /*table.put(new Supporter("c", "1"));
        table.put(new Supporter("e", "2"));
        table.put(new Supporter("a", "3"));
        table.put(new Supporter("b", "4"));
        table.put(new Supporter("h", "5"));
        table.put(new Supporter("f", "6"));
        table.put(new Supporter("g", "7"));
        table.put(new Supporter("d", "8"));
         */
        //table1.lodadFromFile("names.txt");
        //System.out.println(table1.size());
        //table1.printSupportersOrdered();
    }

}
