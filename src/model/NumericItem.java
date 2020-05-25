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
public class NumericItem implements Item{

    private Double numeric_item=null;
    @Override
    public boolean compare(Item item) {
        boolean status=false;
        if (item instanceof NumericItem){
            NumericItem tmp=(NumericItem) item;
            status=(tmp.numeric_item.compareTo(this.numeric_item)==0 && item!=null) ? !status:  status;
        }
        return status;
    }

    @Override
    public String print() {
        return this.numeric_item.toString();
    }

    @Override
    public void setItem(String item) {
        this.numeric_item= Double.parseDouble(item);
    }

    
}
