/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import control.Helper;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 *
 * @author MARCUS VINICIUS
 */
public class Table {
    private static final int TSIZE=228331; //número primo próximo da quantidade de dados que a maior tabela possui
    private final String name; //nome da tabela
    private Column[] columns; //lista de colunas
    private Node[] hashTable=new Node[TSIZE]; //Tabela de Hash
    private int nIndexes=0; //número de chaves da tabela
    private int nNodes=0; //número total de nós vinculados à tabela
    private Table next=null; //referência para a próxima Tabela, para busca no hash de Database
    /**
     * Instancia uma tabela e informando seu nome
     * @param name Nome da tabela
     */
    public Table(String name){
        this.name=name;
    }
    
    /**
     * Instancia uma tabela e informando seu nome e suas colunas
     * @param name Nome da tabela
     * @param columns Lista de colunas
     */
    public Table(String name, Column[] columns){
        this.name=name;
        this.columns=columns;
    }
    
    
     /**
     * Instancia uma tabela e informando seu nome, suas colunas e informa uma linha
     * @param name Nome da tabela
     * @param columns Lista de colunas
     * @param data Linha de uma tabela
     */
    public Table(String name, Column[] columns, String[] data){
        this.name=name;
        this.columns=columns;
        if (data.length>0) put(data);
    }
    
    /**
     * Instancia uma tabela e informando seu nome, suas colunas
     * @param name Nome da tabela
     * @param columns Lista de colunas
     */
    public Table(String name, String[] columns){
        this.name=name;
        this.columns=new Column[columns.length];
        for (int i=0; i<columns.length; i++)
            this.columns[i]=Column.stringToColumn(columns[i]);
    }
    
    /**
     * Instancia uma tabela e informando seu nome, suas colunas e informa uma linha
     * @param name Nome da tabela
     * @param columns Lista de colunas
     * @param data Linha de uma tabela
     */
    public Table(String name, String[] columns, String[] data){
        this.name=name;
        this.columns=new Column[columns.length -1];
        for (int i=0; i<columns.length; i++)
            this.columns[i]=Column.stringToColumn(columns[i]);
        if (data.length>0) put(data);
    }
    
    /**
     * Retorna o nome da tabela
     * @return nome da tabela 
     */
    public String getName(){
        return this.name;
    }
    /**
     * Insere uma linha na tabela
     * @param data linha de uma tabela
     * @return status da inserção
     */
    public boolean put(String[] data){
        Node node, tmp;
        int sum=0;
        if (data.length>0){
            node= new Node(data);
            int[] keys_array=findKeys();
            //informa quais são os itens chave da linha
            node.setKeysIndexes(this);
            for (int i=0;i<keys_array.length; i++){
                int pos=hashing(data[i]);
                sum+=pos;
            }
            sum%=(TSIZE)-1;
            // O(1)
            if (this.hashTable[sum]==null){
                node.setPrevious(this.hashTable[sum]);
                this.hashTable[sum]=node;
            }else{
                //O(n)
                try{
                    tmp=findLastNode(this.hashTable[sum], node);
                     tmp.setNext(node);
                    node.setPrevious(tmp);
                }catch(Exception e){ System.err.println(e.getMessage()); }
            }
            ++this.nNodes;
            return true;
        }
        return false;
    }
    
    /**
     * Retorna o número total de registro da tabela
     * @return total de registros
     */
    public int getTotalOfRows(){
        return this.nNodes;
    }
    
    /**
     * Retorna o registro para a busca desejada
     * @param v valor para chave
     * @return um conjunto de Node (linha na tabela)
     */
    public Node find(String v){
        int k=hashing(v);
        ItemFactory factory= new ItemFactory();
        Item s=factory.createItem(v);
        if (this.hashTable[k]!=null){
            Node node=this.hashTable[k];
            while(node!=null){
                Item[] tmp=node.getKeys();
                if (tmp[0].compare(s)) return node;
                node=node.getNext();
            }
            
        }
        return null;
    }
    
    /**
     * Retorna o registro para a busca desejada
     * @param v valor para chave
     * @return um conjunto de Node (linha na tabela)
     */
    public Node find(String[] v){
        int k=0;
        for (int i=0; i<v.length; i++) k+=hashing(v[i]);
        k%=(TSIZE)-1;
        if (v.length<findKeys().length) return null;
        if (this.hashTable[k]!=null){
            Node  searchedNode,node=this.hashTable[k];
            searchedNode=new Node(v);
            if (node!=null) searchedNode.setKeysIndexes(this);
            while(node!=null){
               if (node.equals(searchedNode)) return node;
                node=node.getNext();
            }
            
        }
        return null;
    }
    
    
    /**
     * Encontra o último Node de uma lista
     * @param node Node incial da tabela de hash 
     * @return Node
     * @throws SQLIntegrityConstraintViolationException Integridade de chave primária violada
     */
    private Node findLastNode(Node node, Node novo) throws SQLIntegrityConstraintViolationException{
        if (novo!=null && comparesNodes(node, novo)) throw new SQLIntegrityConstraintViolationException("Integrity Constraint (PRIMARY KEY) has been violated on table \""+getName()+"\"");
        if (node.hasNext())
            return findLastNode(node.getNext(), novo);
        return node;
        
    }
    
    private boolean comparesNodes(Node x, Node y){
        return x.equals(y);
    }
    
    /**
     * Remove uma linha da tabela para tabelas que tenham chave composta 
     * @param key conjunto de valores para as chaves
     * @return status da operação
     */
    public boolean delete(String[] key){
        Node node=find(key); //procura pelo elemento desejado
       if (node!=null){
           Node previous=node.getPrevious(); //elemento anterior ao encontraado
           int sum=0;
           for (int i=0; i<key.length; i++) sum+=hashing(key[0]); //obtém o índice da tabela de hash em que se encontra o elemento
           //O elemento é único na lista O(1)
           if (previous==null && !node.hasNext()){
               this.hashTable[sum]=null;
               node=null;
           }
           else if(previous==null){ //o elemento é o primeiro da lista, porém não o único
               Node tmp=node.getNext();
               tmp.setPrevious(this.hashTable[sum]);
               this.hashTable[sum]=tmp;
               node=null;
           }else{ //O elemento é o i-ésimo elemento da lista, 1<i<n. O(n)
               Node tmp=node.getPrevious();
               tmp.setNext(node.getNext());
               node=null;    
            }
            --this.nNodes; //decrementa o contador de registros da tabela
            return true;
       }
       return false;
    }
    
    /**
     * Remove uma linha da tabela, procurando pelo única chave primária da tabela
     * @param k conjunto de valores para as chaves
     * @return 
     */
    public boolean delete(String k){
       Node node=find(k); //procura pelo elemento desejado
       if (node!=null){
           Node previous=node.getPrevious(); //elemento anterior ao encontraado
           int key=hashing(k); //obtém o índice da tabela de hash em que se encontra o elemento
           //O elemento é único na lista O(1)
           if (previous==null && !node.hasNext()){
               this.hashTable[key]=null;
               node=null;
           }
           else if(previous==null){ //o elemento é o primeiro da lista, porém não o único
               Node tmp=node.getNext();
               tmp.setPrevious(this.hashTable[key]);
               this.hashTable[key]=tmp;
               node=null;
           }else{ //O elemento é o i-ésimo elemento da lista, 1<i<n. O(n)
               Node tmp=node.getPrevious();
               tmp.setNext(node.getNext());
               node=null;    
            }
            --this.nNodes; //decrementa o contador de registros da tabela
            return true;
       }
       return false;
    }
    
    
    /**
     * Define quais devem ser as colunas chave
     * @param columns Lista de Column
     */
    public void setIndexes(Column[] columns){
        for (int i=0;i<columns.length; i++){
            for (int j=0; j<this.columns.length; j++){
                if (columns[i].equals(this.columns[j])){
                    this.columns[j].setKey(true);
                    ++this.nIndexes;
                    break;
                }
                    
            }
        }
    }
    
    /**
     * Define qual a posição das colunas chave, iniciando por 0
     * @param pos Posições das colunas
     */
    public void setIndexes(int[] pos){
        for (int j=0; j<pos.length; j++){
            if (pos[j]<this.columns.length){
                this.columns[pos[j]].setKey(true);
                ++this.nIndexes;
            }else System.err.println("WARNING: The index number "+pos[j]+" does not exists on table '"+getName()+"' and will be ignored");
        }

    }
    
    
    /**
     * Encontra o índice no vetor de colunas de cada uma das chaves primárias
     * @return conjunto de índices
     */
    public int[] findKeys(){
        int indexes[]=new int[this.nIndexes];
        int[] empty_array={0};
        int count=0;
        for (int i=0; i<this.columns.length; i++){
            if (this.columns[i].isKey()){
                indexes[count]=i;
                ++count;
            }
        }
        if (this.nIndexes==0) return empty_array;
        return indexes;
    }
    
    /**
     * Retorna o número de colunas que a tabela possui
     * @return número de colunas
     */
    public int getColumnsNumber(){
        return this.columns.length;
    }
    
    
    
    /**
     * Retorna a posição (índice) de uma coluna
     * @param column
     * @return Maior ou igual a 0: Posiçao encontrada/ -1 Posição inexistente (coluna desconhecida)
     */
    public int findColumnIndex(Column column){
        for (int i=0; i<this.columns.length; i++){
            if (this.columns[i].equals(column))
                return i;
        }
        return -1;
    }
    
    /**
     * Retorna a posição (índice) de uma coluna
     * @param column
     * @return Maior ou igual a 0: Posiçao encontrada/ -1 Posição inexistente (coluna desconhecida)
     */
    public int findColumnIndex(String column){
        Column tmp=Column.stringToColumn(column);
        for (int i=0; i<this.columns.length; i++){
            if (this.columns[i].equals(tmp))
                return i;
        }
        return -1;
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
     * Verifica se existe referência para uma prócima tabela
     * @return 
     */
    public boolean hasNext(){
        return !(this.next==null);
    }
    
    /**
     * Retorna a referência do próximo
     * @return Table
     */
    public Table getNext(){
        return this.next;
    }
    
    /**
     * Insere uma nova tabela na lista encadeada de tabelas de banco de dados
     * @param t Tabela
     */
    public void setNext(Table t){
        this.next=t;
    }
    
    /**
     * Função de hash com encadeamento externo
     * @param value íncide a ser buscado
     * @return índice para a busca solicitada
     */
    private int hashing(String value){
        int k=createKey(value);
        return (k % TSIZE);
    }
    
    
}
