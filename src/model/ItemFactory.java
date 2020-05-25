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
public class ItemFactory {
    
    /**
     * Cria uma instância de item, e verifica se o mesmo é alfanumérico
     * @param str String
     * @return Instância de Item
     */
    public Item createItem(String str){
        Item item;
        if (Helper.isStringNumeric(str)){
            item=new NumericItem();
        }else{
            item= new AlphanumericItem();
        }
        item.setItem(str);
        return item;   
    }
}
