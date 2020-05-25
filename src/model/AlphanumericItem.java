/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import control.Helper;

/**
 *
 * @author MARCUS VINICIUS
 */
public class AlphanumericItem implements Item{
    StringBuilder sb=new StringBuilder();
    @Override
    public int compare(Item item) {
        if (item instanceof AlphanumericItem){
            AlphanumericItem tmp=(AlphanumericItem) item;
            int counter=0;
            while(counter<tmp.sb.length() && counter<this.sb.length()){
                int n=compareLetters(this.sb.charAt(counter), tmp.sb.charAt(counter));
                if (n!=0) return n;
                ++counter;
            }
            
            return sb.toString().compareTo(tmp.sb.toString());
            
        }
        return -1;
    }

    private int compareLetters(char a, char b){
        return Character.compare(Character.toUpperCase(a), Character.toUpperCase(b));
    }
    
    @Override
    public String print() {
        return this.sb.toString();
    }

    @Override
    public void setItem(String item) {
        sb=new StringBuilder(item);
    }
}
