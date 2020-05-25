/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.naming.LimitExceededException;
import model.Column;
import model.Database;
import model.Item;
import model.Node;
import model.Table;

/**
 * Classe controladora utilizada para criação das próprias tabelas e inserção e 
 * buscas dos dados
 * @author MARCUS VINICIUS
 */
public class DatabaseController {
    private static Database database=null;
    /**
     * Cria uma base de dados
     * @param name nome para o banco de dados
     * @return Database
     */
    public Database createDatabase(String name){
        database=new Database(name);
        return database;
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
     * Realiza Inner Join em Tabelas relacionadas
     * @param sourceTableName Representação textual de uma tabela
     * @param relatedTableName Representação textual de uma tabela relacionada
     * @return Número de ocorrências para a operação
     */
    public int performInnerJoin(String sourceTableName, String relatedTableName){
        Table table=database.getTableByName(sourceTableName);
        Table relatedTable=database.getTableByName(relatedTableName);
        if (table!=null && relatedTable!=null) {
            return table.innerJoinOperation(relatedTable);
        }
        return 0;
    }
    /**
     * Realiza Inner Join em Tabelas relacionadas att
     * @param sourceTableName Representação textual de uma tabela
     * @param columnSourceTableName Representação textual da coluna da tabela base
     * @param relatedTableName Representação textual de uma tabela relacionada
     * @param columnRelatedTableName Representação textual da coluna da tabela relacionada
     */
    public int performInnerJoin(String sourceTableName, String columnSourceTableName, String relatedTableName, String columnRelatedTableName ){
        Table table=database.getTableByName(sourceTableName);
        Table relatedTable=database.getTableByName(relatedTableName);
        int counter=0;
        if (table!=null && relatedTable!=null) {
            Column columnSource=new Column(columnSourceTableName);
            Column columnRelated=new Column(columnRelatedTableName);
            if (table.columnExists(columnSource) && relatedTable.columnExists(columnRelated)){
                for (Item item:table.findAll(columnSource)){
                    counter+=relatedTable.getTotalOfRowsByColumnName(columnRelated.getName(), item.print());
                }
            }
        }
        return counter;
    }
    
    /**
     * Realiza Left Join em Tabelas relacionadas
     * @param sourceTableName Representação textual de uma tabela
     * @param relatedTableName Representação textual de uma tabela relacionada
     * @return Número de ocorrências para a operação
     */
    public int performLeftJoin(String sourceTableName, String relatedTableName){
        Table table=database.getTableByName(sourceTableName);
        Table relatedTable=database.getTableByName(relatedTableName);
        if (table!=null && relatedTable!=null) return table.leftJoinOperation(relatedTable);
        return 0;
    }
    
    /**
     * Realiza Right Join em Tabelas relacionadas
     * @param sourceTableName Representação textual de uma tabela
     * @param relatedTableName Representação textual de uma tabela relacionada
     * @return Número de ocorrências para a operação
     */
    public int performRightJoin(String sourceTableName, String relatedTableName){
        Table table=database.getTableByName(sourceTableName);
        Table relatedTable=database.getTableByName(relatedTableName);
        if (table!=null && relatedTable!=null) {
            return table.rightJoinOperation(relatedTable);
        }
        return 0;
    }
    
    /**
     * Informa as tabelas relacionadas
     * @param tableName Representação textual da tabela a ser pesquisada
     * @return Representação Textual das tabelas relacionadas 
     */
    public String[] getRelatedTables(String tableName){
        if (tableName!=null && !(tableName.isEmpty())){
            if (database==null) database=ReadSQLFileController.getDatabase();
            Table t=database.getTableByName(tableName);
            Table tmp[]= new Table[database.getTables().length];
            List<String> auxTableJoin=new ArrayList<String>();
            if (t!=null){
                if(t.getJoinTables()!=null){
                    for (Table table: t.getJoinTables())
                        auxTableJoin.add(table.getName());
                }
                //Não tem chave estrangeira
                for (Table aux: database.getTables()){
                    if (aux.getJoinTables()!=null && !aux.getName().equalsIgnoreCase(tableName)){
                        Table[] comparable=aux.getJoinTables();
                        for (int j=0; j<comparable.length; j++){
                            if (comparable[j].getName().equalsIgnoreCase(tableName)){
                                auxTableJoin.add(aux.getName());
                                break;
                            }
                        }
                    }
                }
                return auxTableJoin.toArray(new String[auxTableJoin.size()]);    
            }
        }
        return new String[]{};
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
     * Retorna o nome de todas as colunas de um Banco de Dados
     * @return nome das tabelas
     */
    public String[] getTablesName(){
        if (database==null) database=ReadSQLFileController.getDatabase();
        String[] tablesName=new String[database.getNumberOfTables()];
        int count=0;
        for (Table aux: database.getTables()){
            tablesName[count]=aux.getName();
            ++count;
        }
        return tablesName;
    }
    
    /**
     * Representação textual de todas as colunas de uma tabela
     * @param tableName Nome da tabela
     * @return array com nome das colunas de uma tabela
     */
    public String[] getColumnsByTablename(String tableName){
        if (database==null) database=ReadSQLFileController.getDatabase();
        
        if ((tableName!=null)  ||  (!tableName.isEmpty())){
            Table table=database.getTableByName(tableName);
            String[] columnsName=new String[table.getColumnsNumber()]; 
            int count=0;
            if (table!=null){
                for (Column c: table.getColumns()){
                    columnsName[count]=c.getName();
                    ++count;
                }
                return columnsName;
            }
        }
        return new String[]{" "};
    }
    
    /**
     * Representação textual de todas as colunas de uma tabela
     * @param table Tabela a ser pesquisada
     * @return array com nome das colunas de uma tabela
     */
    public String[] getColumnsByTablename(Table table){
        if (database==null) database=ReadSQLFileController.getDatabase();
        
        if (table!=null){
            String[] columnsName=new String[table.getColumnsNumber()]; 
            int count=0;
            if (table!=null){
                for (Column c: table.getColumns()){
                    columnsName[count]=c.getName();
                    ++count;
                }
                return columnsName;
            }
        }
        return new String[]{" "};
    }
    
    /**
     * Retorna o número total de registros para todas as tabelas
     * @return array multimensinal contendo nome da tabela e número de registros
     */
    
    public String[][] getTotalOfRowsByTable(){
        if (database==null) database=ReadSQLFileController.getDatabase();
        String[][] total= new String[database.getNumberOfTables()][3];
        int count=0;
        for (Table aux: database.getTables()){
            String columnName=aux.getName();
            int columnRows=aux.getTotalOfRows();
            total[count][0]=columnName; //nome da tabela
            total[count][1]=Integer.toString(columnRows); //total de registros
            total[count][2]=Integer.toString(aux.getTotalOfCollisions());
            ++count;
        }
        return total;
    }
    
    
    /**
     * Procura por um registro chave chave na tabela selecionada
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
    * Retorna o total de ocorrências para uma busca com critério.
    * Operação de comparação por igualdade
    * @param table tabela
    * @param columnName Nome da coluna
    * @param criteria critério a ser comparado
    * @return total de ocorrências
    */
    public int getTotalOfOccurrences(Table table, String columnName, String criteria){
        int total=0;
        if (table!=null) total=table.getTotalOfRowsByColumnName(columnName, criteria);
        return total;
    }
    
    /**
    * Retorna o total de ocorrências para uma busca com critério.
    * Operação de comparação por igualdade
    * @param tableName tabela
    * @param columnName Nome da coluna
    * @param criteria critério a ser comparado
    * @return total de ocorrências
    */
    public int getTotalOfOccurrences(String tableName, String columnName, String criteria){
        int total=0;
        if (tableName!=null && !tableName.isEmpty()) {
            Table table;
            table=database.getTableByName(tableName);
            if (table!=null) total=table.getTotalOfRowsByColumnName(columnName, criteria);
        }
        return total;
    }
    
    /**
     * Retorna o nome das colunas relacionadas a uma tabela
     * @param sourceTableName representação textual de uma Tabela
     * @return Array Multidimensional contendo as colunas referenciadas
     */
    public HashMap<String,String> getRelatedColumnsName(String sourceTableName){
        HashMap<String, String> columns=new HashMap<String, String>();
        Table table=database.getTableByName(sourceTableName);
        String[] relatedTablesName=getRelatedTables(sourceTableName);
        if (table!=null && relatedTablesName.length>0) {
                for (String aux: relatedTablesName){
                    Table tmp=database.getTableByName(aux);
                    if (tmp!=null){
                        for (String relatedColums: getColumnsByTablename(aux)){
                            for (String tableColumns: getColumnsByTablename(sourceTableName)){
                                if (tableColumns.equals(relatedColums)){
                                    columns.put(tableColumns, relatedColums);
                                }
                            }
                        }
                    }
                }  
            return columns;    
        }
        return null;
    }
    
    /**
     * Procura por um registro na tabela selecionada, através de uma chave composta
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

    
    /**
     * Informa os nomes das chaves primárias para uma tabela
     * @param tableName Representação textual de uma Tabela
     * @return Conjunto de representações textuais das colunas chave de uma tabela
     */
    public String[] getPrimaryKeyFromTable(String tableName){
        if (database!=null){
            Table t=database.getTableByName(tableName);
            if (t!=null){
                Column[] keysColumn=t.findKeysColumns();
                String keysName[]=new String[keysColumn.length];
                for (int i=0; i<keysColumn.length; i++){
                    keysName[i]=keysColumn[i].getName();
                }
                return keysName;
            }
        }
        return new String[]{};
    }
    
    /**
     * Remove um registro de uma tabela tendo como base um valor chave
     * @param table Tabela
     * @param value registro a ser removido (chave)
     * @return status da operação
     */
    public boolean deleteRow(Table table, String value){
        if (table!=null) return table.delete(value);
        return false;
    }
    
    /**
     * Remove um registro de uma tabela tendo como base um valor chave
     * @param tableName representação textual de uma tabela
     * @param value array de registros a serem removidos (chave)
     * @return status da operação
     */
    public boolean deleteRow(String tableName, String[] value){
        if (database!=null){
            Table table=database.getTableByName(tableName);
            if (table!=null) return table.delete(value);
        }
        return false;
    }
    
    /**
     * Executa N testes aletaórios em uma tabela
     * @param tableName Representação textual de uma tabela
     * @param n Número de testes aleatórios a serem realizados
     * @return número de testes bem sucedidos
     */
    public int runTestsRandomly(String tableName, int n){
        if (database!=null){
            Table t=database.getTableByName(tableName);
            if (t!=null && n>0){
                int counter=0;
                for (int i=0; i<n; i++){
                    if (t.randomAccessData()) ++counter;
                }
                return counter;
            }
        }
        return 0;
    }
    
}
