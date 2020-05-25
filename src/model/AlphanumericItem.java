/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author MARCUS VINICIUS
 */
public class AlphanumericItem implements Item{
    private String alphanumeric_item=null;
    @Override
    public boolean compare(Item item) {
        boolean status=false;
        if (item instanceof AlphanumericItem){
            AlphanumericItem tmp=(AlphanumericItem) item;
            status=(alphanumeric_item.equalsIgnoreCase(tmp.alphanumeric_item) && item!=null) ? !status:  status;
        }
            return status;
            
    }

    @Override
    public String print() {
        return this.alphanumeric_item;
    }

    @Override
    public void setItem(String item) {
        this.alphanumeric_item=item;
    }
}
