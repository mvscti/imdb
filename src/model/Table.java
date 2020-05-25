/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import control.Helper;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author MARCUS VINICIUS
 */
public class Table {
    private static final char[] symbols; //gerador de caracteres aleatórios
    static {
      StringBuilder tmp = new StringBuilder();
      for (char ch = '0'; ch <= '9'; ++ch)
        tmp.append(ch);
      for (char ch = 'a'; ch <= 'z'; ++ch)
        tmp.append(ch);
      symbols = tmp.toString().toCharArray();
    } 
    
    private static final int TSIZE=228331; //número primo próximo da quantidade de dados que a maior tabela possui
    private final String name; //nome da tabela
    private Column[] columns; //lista de colunas
    public Node[] hashTable=new Node[TSIZE]; //Tabela de Hash
    private Table[] joinTables; //referência para Tabelas com Junção
    private int nIndexes=0; //número de chaves da tabela
    private int nNodes=0; //número total de nós vinculados à tabela
    private Table next=null; //referência para a próxima Tabela, para busca no hash de Database
    private int nCollisions=0; //número de colisões para a tabela
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
     * Acessa uma posição aleatória no vetor de dados
     * @return status da operação
     */
    public boolean randomAccessData(){
        Random random= new Random();
        int column=random.nextInt(TSIZE%(getColumnsNumber()));
        if (column<0) column=0;
        if (column>=0 && column<getColumnsNumber()){
            //Procura por um item alfanumérico
            if (random.nextInt(30)%2==0){
                random.nextInt(symbols.length-1);
                int n=random.nextInt(symbols.length-1);
                char[] v=new char[n];
                for (int i=0; i<n; i++){
                    v[i]=symbols[random.nextInt(symbols.length-1)];
                }
                return (getTotalOfRowsByColumnName(getColumnAt(column).getName(), v.toString()))>0;
            }else{ //Porcura um item numérico
                Integer number=random.nextInt(TSIZE);
                String v=number.toString();
                return (getTotalOfRowsByColumnName(getColumnAt(column).getName(), v))>0;
            }
        }
        return false;
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
                // /Li/
                try{
                    tmp=findLastNode(this.hashTable[sum], node);
                    ++this.nCollisions;
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
     * Retorna o número total de colisões para a tabela
     * @return número de colisões
     */
    public int getTotalOfCollisions(){
        return this.nCollisions;
    }
    
    
    /**
     * Retorna o número total de registro da tabela
     * @return total de registros
     */
    public int getTotalOfRows(){
        return this.nNodes;
    }
    
    /**
     * Retorna uma coluna localizada em uma posição informada
     * @param position posição em que a coluna se econtra, inciando por 0
     * @return Coluna referente a busca
     */
    public Column getColumnAt(int position){
        if (position>=0 && position<getColumnsNumber()) return this.columns[position];
        return null;
    }
    
    /**
     * Retorna todas as colunas de uma tabela
     * @return array d Column
     */
    public Column[] getColumns(){
        return this.columns;
    }
   
        
    /**
     * Calcula o número de ocorrências para a busca desejada,
     * através de um valor chave não necessitando ser chave primária.
     * Realiza uma busca Linear
     * @param columnName nome de uma coluna
     * @param v valor para a busca
     * @return Número de ocorrências de Node (linha na tabela)
     */
    public int getTotalOfRowsByColumnName(String columnName, String v){
        int index=findColumnIndex(columnName);
        int total=0;
        if (index==0 && this.nIndexes==0){//índice é chave única
            Node node=find(v);
            if (node!=null) ++total;   
        }else{ //não é chave ou a tabela possui chave composta
            int position=findColumnIndex(columnName);
            int count=0;
            ItemFactory factory= new ItemFactory();
            Item comparable= factory.createItem(v);
            while(count<TSIZE){
                Node aux=this.hashTable[count];
                while(aux!=null){
                    Item item=aux.getItemAt(position);
                    if (item.compare(comparable)==0) ++total;
                    aux=aux.getNext();
                }
                ++count;
            }
        }
        return total;
    }
    
    
    /**
     * Calcula o número de ocorrências para a busca desejada,
     * através de um valor chave não necessitando ser chave primária.
     * Realiza uma busca Linear
     * @param columnName array de nomes para colunas
     * @param v array de valores para a busca
     * @return Número de ocorrências de Node (linha na tabela)
     */
    public int getTotalOfRowsByColumnName(String[] columnName, String[] v){
        if (columnName.length!=v.length) return 0;
        int[] keys=findKeys();
        int i;
        for (i=0; i<keys.length && columnName.length==keys.length; i++){
            if (findColumnIndex(columnName[i])!=keys[i]) break;
        }
        int total=0;
        if (i==keys.length){//índice é chave composta
            Node node=find(v);
            if (node!=null) ++total;   
        }else{ //não é chave ou a tabela possui chave composta
            int count=0;
            ItemFactory factory= new ItemFactory();
            Item comparable[]=new Item[v.length];
            int tmp=0;
            for (String s: v){
                comparable[tmp] = factory.createItem(v[tmp]);
                ++tmp;
            }
            while(count<TSIZE){
                Node aux=this.hashTable[count];
                while(aux!=null){
                    for (i=0; i<comparable.length; i++){
                        int position=findColumnIndex(columnName[i]);
                        Item item=aux.getItemAt(position);
                        if (item.compare(comparable[i])!=0) break;
                    }
                    if (i==comparable.length) ++total;
                    aux=aux.getNext();
                }
                ++count;
            }
        }
        return total;
    }
    
    
    /**
     * Retorna todos os dados referente a uma coluna da tabela
     * @param c Coluna a ser pesquisada
     * @return array com os dados referentes; 
     */
    public Item[] findAll(Column c){
        Item[] item=null;
        int position=findColumnIndex(c);
        if (position>=0){
            item=new Item[getTotalOfRows()];
            int counter=0;
            int i=0;
            Node aux=this.hashTable[i];
            while(counter<getTotalOfRows()){
                while(aux!=null){
                    item[counter]=aux.getItemAt(position);
                    ++counter;
                    aux=aux.getNext();
                }
                ++i;
                aux=this.hashTable[i];
            }
        }
        return item;
    }
    
    
    /**
     * Calcula o número de ocorrências para a busca desejada,
     * através de um valor chave, não necessitando ser chave primária.
     * Realiza uma busca Linear
     * @param columnName nome de uma coluna
     * @param v valor para chave
     * @return Número de ocorrências de Node (linha na tabela)
     */
    public boolean checkValue(String columnName, String v){
        int index=findColumnIndex(columnName);
        if (index==0 && this.nIndexes==0){//índice é chave única
            Node node=find(v);
            if (node!=null) return true;
        }else{ //não é chave ou a tabela possui chave composta
            int position=findColumnIndex(columnName);
            int count=0;
            ItemFactory factory= new ItemFactory();
            Item comparable= factory.createItem(v);
            while(count<TSIZE){
                if (this.hashTable[count]!=null){
                    Node aux=this.hashTable[count];
                    while(aux!=null){
                        Item item=aux.getItemAt(position);
                        if (item.compare(comparable)==0) return true;
                        aux=aux.getNext();
                    }
                }
                ++count;
            }
        }
        return false;
    }
    
    /**
     * Calcula o número de ocorrências para a busca desejada,
     * através de um valor chave, não necessitando ser chave primária.
     * Realiza uma busca Linear
     * @param column Coluna a ser pesquisada
     * @param v valor para chave
     * @return Número de ocorrências de Node (linha na tabela)
     */
    public boolean checkValue(Column column, String v){
        int position=findColumnIndex(column);
        int count=0;
        ItemFactory factory= new ItemFactory();
        Item comparable= factory.createItem(v);
        while(count<TSIZE){
            if (this.hashTable[count]!=null){
                Node aux=this.hashTable[count];
                while(aux!=null){
                    Item item=aux.getItemAt(position);
                    if (item!=null && item.compare(comparable)==0) return true;
                    aux=aux.getNext();
                }
            }
            ++count;
        }
        
        return false;
    }
    
    /**
     * Retorna o registro para a busca desejada, através de um valor chave
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
                if (tmp[0].compare(s)==0) return node;
                node=node.getNext();
            }
            
        }
        return null;
    }
    
    /**
     * Retorna o registro para a busca desejada, através de uma chave composta
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
    
    /**
     * Compara dois Nodes
     * @param x Node 1
     * @param y Node 2
     * @return 
     */
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
            for (int i=0; i<key.length; i++) sum+=hashing(key[i]); //obtém o índice da tabela de hash em que se encontra o elemento
            sum%=(TSIZE)-1;
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
     * Encontra o índice no vetor de colunas de cada uma das chaves primárias
     * @return conjunto de índices
     */
    public Column[] findKeysColumns(){
        Column indexes[]= new Column[this.nIndexes];
        Column[] empty_array={};
        int count=0;
        for (int i=0; i<this.columns.length; i++){
            if (this.columns[i].isKey()){
                indexes[count]=this.columns[i];
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
     * Verifica se uma determinada coluna exista na relação de colunas da tabela
     * @param c Coluna
     * @return status da operação
     */
    public boolean columnExists(Column c){
        for (Column columns: getColumns()){
            if (columns.equals(c)) return true;
        }
        return false;
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
            return Helper.hashCode(Double.parseDouble(value));
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
     * Calcula o número de ocorrências de igualdade de chaves com outra tabela
     * @param t Tabela a ser realizada a operação
     * @return Total de ocorrências
     */
    public int innerJoinOperation(Table t){
        int counter=0;
        if (t!=null){
            int n=0, countKeys=1;
            boolean sameKeys=false; //flag que verifica se chaves primária e estrageira são as mesmas na tabela relacionada
            List<Column> joinColumns=new ArrayList<>();
            for (Column joinColumnTables: t.getColumns()){
                for (Column tableColumns: getColumns()){
                    if (joinColumnTables.equals(tableColumns)) joinColumns.add(tableColumns);
                }
            }
            for (Column keysColumns: t.findKeysColumns()){
                for (Column joinColumn: joinColumns){
                    if (keysColumns.equals(joinColumn)) ++countKeys;
                }
            }
            if (t.findKeysColumns().length==countKeys-1 && joinColumns.size()!=0) sameKeys=!sameKeys; //Verifica se as chaves primárias e esttrangeiras são as mesmas
            int nNodes=0; //contador de nós testados,usado na tentativa de diminuir o tempo da busca
            //O(n)
            for (int i=0; i<TSIZE && nNodes<getTotalOfRows(); i++){
                if (this.hashTable[i]!=null){
                    Node aux=this.hashTable[i];
                    // O|Li|
                    String v[]=new String[joinColumns.size()];
                    while(aux!=null){
                       int nComparator=0;
                       int temp=0;
                       int countLoop=0;
                       for (Column tmp: joinColumns){
                           if (countLoop>nComparator && countLoop>temp) break;
                           int indexColumn=findColumnIndex(tmp);
                           //Busca por índice chave simples ou composta
                           if (sameKeys){
                               v[temp]=aux.getItemAt(indexColumn).print();
                               ++temp;
                           }else if (indexColumn>=0) //Item não é chave
                               nComparator+=(t.checkValue(tmp.getName(), aux.getItemAt(indexColumn).print()))? 1 : 0;
                           ++countLoop;
                       }
                       if (!sameKeys && nComparator==joinColumns.size()){
                            //tabela relacionada não tem chave composta
                            String columsNames[]=new String[joinColumns.size()];
                            String k[]=new String [joinColumns.size()];
                            for (int o=0; o<joinColumns.size(); o++){
                                columsNames[o]=joinColumns.get(0).getName();
                                int indexColumn=findColumnIndex(columsNames[o]);
                                k[o]=aux.getItemAt(indexColumn).print();
                            }
                            counter+=t.getTotalOfRowsByColumnName(columsNames, k);
                       }else if (sameKeys && t.find(v)!=null) ++counter; //tabela relacionada tem chave composta
                       ++nNodes;
                       aux=aux.getNext();
                    }
                }
            }
        }
        return counter;
    }
    
    
    
    /**
     * Calcula o número de ocorrências de igualdade de chaves com outra tabela para uma operação Left Join
     * @param t Tabela a ser realizada a operação
     * @return Total de ocorrências
     */
    public int leftJoinOperation(Table t){
        return getTotalOfRows();
    }
    
    
    /**
     * Calcula o número de ocorrências de igualdade de chaves com outra tabela para uma operação Left Join
     * @param t Tabela a ser realizada a operação
     * @return Total de ocorrências
     */
    public int rightJoinOperation(Table t){
        return getTotalOfRows();
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

    /**Informa as tabelas que fazem junção a esta
     * @return  Retorna as tabelas que fazem junção a esta
     */
    public Table[] getJoinTables() {
        return joinTables;
    }

    /**
     * @param joinTables the joinTables to set
     */
    public void setJoinTables(Table[] joinTables) {
        this.joinTables = joinTables;
    }    
}
