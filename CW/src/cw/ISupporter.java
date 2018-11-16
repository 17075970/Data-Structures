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
public interface ISupporter {
    /**
     * Retrieves the supporter's ID
     * @pre true
     * @return the supporter's ID
     */
    public String getID();
    
    /**
     * Retrieves the name of the supporter
     * @pre true
     * @return the name of the supporter
     */
    public String getName();
}
