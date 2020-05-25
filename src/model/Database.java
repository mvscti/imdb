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
public class Database {
    private static final int TSIZE=37; //número primo próximo a 30
    private int nTables=0; //limite de tabelas do Banco de dados
    private final String name; // nome do banco de dados
    private Table hashTable[]= new Table[TSIZE];//hash de Tabelas do banco de Dados
    
    public Database(String name){
        this.name=name;
    }
    
    /**
     * Insere uma tabela no Banco de dados
     * @param table Tabela a ser inserida
     */
    public void addTable(Table table){
        if (table!=null){
            int key=hashing(table.getName());
            if (this.hashTable[key]==null) this.hashTable[key]=table;
            else{
                Table t=findLastTable(this.hashTable[key]);
                t.setNext(table);
            }
            ++this.nTables;
        }
    }
    
    /**
     * Retorna uma tabela por nome
     * @param name Nome da Tabela
     * @return Tabela
     */
    public Table getTableByName(String name){
        return find(name);
    }
    
    /**
     * Retotna o número de tabelas da Base de dados
     * @return Número de tabelas
     */
    public int getNumerOfTables(){
        return this.nTables;
    }
    
    /**
     * Função de hash com encadeamento externo
     * @param value íncide a ser buscado
     * @return índice para a busca solicitada
     */
    private int hashing(String value){
        int k=createKey(value);
        return (k % TSIZE)-1;
    }
    
    /**
     * Cria um valor numérico para chave
     * @param value 
     * @return valor numérico para chave
     */
    private int createKey(String value){
        //Índice numérico
        if (Helper.isStringNumeric(value)){
            Integer valueNumeric=Integer.parseInt(value);
            return valueNumeric;
        }
        //Índice alfanumérico
        else{
            return Helper.convertStringToIndex(value);
        }
    }
    
    /**
     * Retorna o registro para a busca desejada
     * @param v valor para chave
     * @return uma tabela
     */
    private Table find(String v){
        int k=hashing(v);
        if (this.hashTable[k]!=null){
            Table node=this.hashTable[k];
            while(node!=null){
                if (node.getName().equalsIgnoreCase(v)) return node;
                node=node.getNext();
            }
        }
        return null;
    }
    
    /**
     * Encontra a última tabela la lista de tabelas
     * @param root
     * @return 
     */
    private Table findLastTable(Table root){
        if (root.hasNext()) return root.getNext();
        return root;
    }
    
    
}
