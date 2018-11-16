/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cw;

/**
 *
 * @author 17075970
 */
public interface ISupporterDatabase {
    /**
     * Empties the database.
     * @pre true
     */
    public void clear();
    
    /**
     * Determines whether a Supporter name exists as a key inside the database
     * @pre true
     * @param name the Supporter name (key) to locate
     * @return true iff the name exists as a key in the database
     */
    public boolean containsName(String name);
        
    /**
     * Returns a Supporter object mapped to the supplied name.
     * @pre true
     * @param name The Supporter name (key) to locate
     * @return the Supporter object mapped to the key name if the name
     * exists as key in the database, otherwise null
     */
    public Supporter get(String name);
    
        
    /**
     * Returns the number of supporters in the database
     * @pre true
     * @return number of supporters in the database. 0 if empty
     */
    public int size();
	
	/**
     * Determines if the database is empty or not.
     * @pre true
     * @return true iff the database is empty
     */
    public boolean isEmpty();
    
    /**
     * Inserts a supporter object into the database, with the key of the supplied
     * supporter's name.
     * Note: If the name already exists as a key, then then the original entry is
     * overwritten. This method should return the previous associated value if one exists, otherwise null
     * @pre true
     */
    public Supporter put(Supporter supporter);
    
    /**
     * Removes and returns a supporter from the database, with the key
     * the supplied name.
     * @param name The name (key) to remove.
     * @pre true
     * @return the removed supporter object mapped to the name, or null if
     * the name does not exist.
     */
    public Supporter remove(String name);
    
    /**
     * Prints the names and IDs of all the supporters in the database in alphabetic order.
     * @pre true
     */
    public void printSupportersOrdered();
}
