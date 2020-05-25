/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;
import javax.naming.LimitExceededException;
import model.Column;
import model.Database;
import model.Item;
import model.Node;
import model.Table;

/**
 * Classe controladora utilizada para criação das própiras tabelas e inserção e 
 * buscas dos dados
 * @author MARCUS VINICIUS
 */
public class DatabaseController {
    
    /**
     * Cria uma base de dados
     * @param name nome para o banco de dados
     * @return Database
     */
    public Database createDatabase(String name){
        return new Database(name);
    }
    
 
    /**
     * Cria uma tabela em um banco de dados
     * @param database Base de dados
     * @param name nome da tabela
     * @param columns Relação de colunas
     * @return Table
     */
    public Table createTable(Database database,String name, String[] columns){
        Table table= new Table(name,columns);
        try{
            database.addTable(table);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return table;
    }
    
    
    /**
     * Cria uma tabela em um banco de dados
     * @param database base de dados
     * @param name nome da tabela
     * @param columns Relação de colunas
     * @return Table
     */
    public Table createTable(Database database,String name, Column[] columns){
        Table table= new Table(name,columns);
        try{
            database.addTable(table);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return table;
    }
    
    
    /**
     * Cria uma tabela em um banco de dados
     * @param database base de dados
     * @param name nome da tabela
     * @param columns relação de colunas
     * @param pos índices das chaves primárias, iniciando por 0
     * @return Table
     */
    public Table createTable(Database database,String name, Column[] columns, int pos[]){
        Table table= new Table(name,columns);
        try{
            database.addTable(table);
            setPrimaryKeys(table, pos);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return table;
    }
    
    /**
     * Cria uma tabela em um banco de dados
     * @param database base de dados
     * @param name nome da tabela
     * @param columns relação de colunas
     * @param pos índices das chaves primárias, iniciando por 0
     * @return Table
     */
    public Table createTable(Database database,String name, String[] columns, int[] pos){
        Table table= new Table(name,columns);
        try{
            database.addTable(table);
            setPrimaryKeys(table, pos);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return table;
    }
    
    /**
     * Cria uma tabela em um banco de dados
     * @param database base de dados
     * @param name nome da tabela
     * @param columns relação de colunas
     * @param pos relação das chaves primárias
     * @return Table
     */
    public Table createTable(Database database,String name, Column[] columns, Column[] pos){
        Table table= new Table(name,columns);
        try{
            database.addTable(table);
            setPrimaryKeys(table, pos);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return table;
    }
    
    /**
     * Cria as chaves primárias em uma tabela.
     * @param table Tabela
     * @param pos índices de chaves primárias, iniciando por 1
     */
    private void setPrimaryKeys(Table table,int[] pos){
        if (table!=null && pos.length>0){
            table.setIndexes(pos);
        }
    }
    
    /**
     * Cria as chaves primárias em uma tabela
     * @param table Tabela
     * @param pos índices de chaves primárias, iniciando por 0
     */
    private void setPrimaryKeys(Table table,Column[] pos){
        if (table!=null && pos.length>0){
            table.setIndexes(pos);
        }
    }
    
    /**
     * Insere um novo registro em uma tabela
     * @param table Tabela
     * @param data relação de dados, tomando como base a ordem das colunas
     * @throws javax.naming.LimitExceededException Número de registros é maior do que número de colunas
     * @return Status da inserção
     */
    public boolean addRow(Table table, String[] data) throws LimitExceededException{
        int difference;
        //"Completa" com "NULL" um registro que trnha menos dados do que colunas da tabela
        if (data.length<table.getColumnsNumber()){
            difference=table.getColumnsNumber()-data.length;
            String[] tmp=new String[data.length+difference];
            for (int i=0;i<data.length;i++) tmp[i]=data[i];    
        }
        if (data.length>table.getColumnsNumber()) throw new LimitExceededException("The columns number is smaller than row columns on table \""+table.getName()+"\"");
        table.put(data);
        return true;
    }
    
    
    /**
     * Procura por um registro na tabela selecionada
     * @param table Tabela
     * @param value chave para busca
     * @return Lista de valores referentes à busca
     */
    public String[] find(Table table, String value){
        if (!value.isEmpty()){
            Node node=table.find(value);
            if (node!=null){
                String str[]=new String[node.getNodeData().length];
                int count=0;
                for (Item i: node.getNodeData()){
                    str[count]=i.print();
                    ++count;
                }
                return str;
            }
        }
        return null;
    }
    
    /**
     * Procura por um registro na tabela selecionada
     * @param table Tabela
     * @param value chave para busca
     * @return Lista de valores referentes à busca
     */
    public String[] find(Table table, String value[]){
        if (value.length>0){
            Node node=table.find(value);
            if (node!=null){
                String str[]=new String[node.getNodeData().length];
                int count=0;
                for (Item i: node.getNodeData()){
                    str[count]=i.print();
                    ++count;
                }
                return str;
            }
        }
        return null;
    }

    public boolean deleteRow(Table table, String value){
        if (table!=null) return table.delete(value);
        return false;
    }
    
}
