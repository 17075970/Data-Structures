/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cw;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author 17075970
 */
public class CW {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        boolean HT = false;
        ISupporterDatabase table = new SupporterDatabaseBST();
        
        Scanner scan = new Scanner(System.in);
        System.out.print("\nEnter the command('\033[32mhelp\033[0m' to get list of commands): ");
        String command = scan.next();

        while (!command.equalsIgnoreCase("quit") && !command.equalsIgnoreCase("q")) {
            switch (command) {
                case "s":
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
                    if(b != null){
                        System.out.println(b.getName() + "         " + b.getID());
                    }
                    break;
                case "contains":
                    System.out.println();
                    System.out.print("Enter the name of supporter: ");
                    name = scan.next();
                    System.out.println(table.containsName(name) ? "There is supporter with this name" : "");
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
                case "empty":
                    System.out.println(table.isEmpty() ? "Table is empty" : "Table is not empty");
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
                            
                            table.put(new Supporter(l[1], l[0]));
                        }
                        
                        file.close();
                        
                        System.out.println("Supporters successfully added");
                    }catch(FileNotFoundException e){
                        System.out.println("File not found");
                    }
                    break;
                case "p":
                case "print":
                    System.out.println();
                    table.printSupportersOrdered();
                    break;
                case "r":
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
                    System.out.println("'\033[32madd\033[0m'           to add new supporter in list");
                    System.out.println("'\033[32mclear\033[0m'         to remove all supporters from the list");
                    System.out.println("'\033[32mload\033[0m'          to upload several supporters from file");
                    System.out.println("'\033[32mremove\033[0m' or '\033[32mr\033[0m' to remove one supporter from the list");
                    System.out.println("'\033[32mprint\033[0m' or '\033[32mp\033[0m'  to display all supporters in the list in the alphabetical order");
                    System.out.println("'\033[32mswitch\033[0m' or '\033[32ms\033[0m' to change type of the list");
                    System.out.println("'\033[32msize\033[0m'          to get amount of supporters in your list");
                    System.out.println("'\033[32mcontains\033[0m'      to know is there supporter with this name");
                    System.out.println("'\033[32mget\033[0m'           to get information about supporter");
                    System.out.println("'\033[32mquit\033[0m' or '\033[32mq\033[0m'   to get information about supporter");
                    break;
                default:
                    System.out.println();
                    System.out.println("Illegal command");
                    break;
            }

            System.out.print("\nEnter the command('\033[32mhelp\033[0m' to get list of commands): ");
            command = scan.next();

        }
    }
}
