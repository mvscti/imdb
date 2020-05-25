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
public class Node {
    private Node next=null; //referência para o próximo Nó
    private Node previous=null; //referência para o Nó anterior
    private Item[] data; //valoares de dados
    private Item[] keys; //conjunto de valores chave
    
    public Node(String[] data){
        ItemFactory factory=new ItemFactory();
        //Popula o array de dados
        this.data=new Item[data.length];
        for (int i=0; i<data.length; i++){
            this.data[i]=factory.createItem(data[i]);
        }
    }
    
    /**
     * Infoma se existe o nó possui um próximo elemento
     * @return True, se sim
     */
    public boolean hasNext(){
        return (this.next != null);
    }
    
    /**
     * Adiciona um novo nó
     * @param node Novo nó
     */
    public void setNext(Node node){
        this.next=node;
    }
    
    /**
     * Retorna a refereência para o próximo elemento
     * @return Node
     */
    public Node getNext(){
        return this.next;
    }
    
    public Item[] getNodeData(){
        return this.data;
    }
    
    /**
     * Retorna as chaves (índices) indentificadoras da linha. Caso não tenha sido
     * informado, o primeiro item é retornado
     * @return 
     */
    public Item[] getKeys(){
        if (this.keys.length>0) return this.keys;
        Item[] key=new Item[1];
        key[0]=this.data[0];
        return key;
    }
    
    /**
     * Informa qual(is) a posição(ões) do(s) valor(es) chave
     * 
     */
    public void setKeysIndexes(Table t){
        if (t!=null){
           Item[] tmp=new Item[t.findKeys().length];
           int count=0;
           for (int i: t.findKeys()) {
               tmp[count]=this.data[i];
               ++count;
           }
           this.keys=tmp;
        }
        
    }

    /**
     * @return Node anterior
     */
    public Node getPrevious() {
        return previous;
    }

    /**
     * @param previous Próximo Node a ser setado
     */
    public void setPrevious(Node previous) {
        this.previous = previous;
    }
    
    /**
     * Sobrescreve método para verificar se o Node possui um outro equivalente
     * como chave primária
     * @param o Node 
     * @return True se forem iguais/False para diferentes
     */
    @Override
    public boolean equals(Object o){
        if (o instanceof Node){
            Node e=(Node) o;
            Item[] items=((Node) o).getKeys();
            int count=0; //contador do laço de repetição do array de itens chave
            int equalNumber=0; //contador de ocorrências em que um item procurado é igual a uma chave
            for (Item i: getKeys()){
               if (i.compare(items[count])) ++equalNumber;
               ++count;
            }
            //Todas as colunas chave são idênticas
            if (equalNumber==getKeys().length) return true;
        }
        
        return false;
    }

}
