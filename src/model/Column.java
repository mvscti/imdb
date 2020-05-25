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
public class Column {
    private String name;
    private boolean key=false;
    
    public Column(String name){
       setName(name);
    }

    public Column(String name, boolean key){
        setName(name);
        setKey(key);
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * Coluna é chave ou não
     * @return the key
     */
    public boolean isKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(boolean key) {
        this.key = key;
    }
    
    /**
     * Recebe o nome de uma coluna (String) e retorna uma referência para coluna
     * @param str Nome da coluna
     * @return Referência de uma coluna;
     */
    public static Column stringToColumn(String str){
        Column column= new Column(str);
        return column;
    }
    
    /**
     * Verifica se uma coluna é igual a informada
     * @param column
     * @return true ou false
     */
    @Override
    public boolean equals(Object column){
        boolean status=false;
        if (column instanceof Column)
            status=((Column) column).getName().equalsIgnoreCase(this.getName()) ? !status: status; 
        return status;
    }
    
}
