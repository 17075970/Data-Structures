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
class Supporter implements ISupporter {
    private String name, ID;
    
    public Supporter(String name, String ID){
        this.name = name;
        this.ID = ID;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return name;
    }
    
}
