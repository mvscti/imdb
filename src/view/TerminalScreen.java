/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author MARCUS VINICIUS
 */
public class TerminalScreen{
     /**
     * Exibe os menus disponíveis
     */
    public TerminalScreen(){
        System.out.println("Operations allowed");
        System.out.println("1-Insert data in some table");
        System.out.println("2-Delete row from table");
        System.out.println("3-Select data");
        System.out.println("4-Create a new table in Database");
        System.out.println("5-Import a new Dump file (Overwriten any existing database)");
        System.out.println();
        System.out.print("Choose any operation: ");
    }
    
}
