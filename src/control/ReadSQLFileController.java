/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;
import model.Column;
import model.Database;
import model.Item;
import model.Table;
import model.Node;

/**
 * Classe Singleton que faz a leitura do arquivo usda.sql ou qualquer arquivo,
 * SQL no mesmo formato deste, cria a base de dados e insere os dados nelas
 * @author MARCUS VINICIUS
 */
public final class ReadSQLFileController {
    private static ReadSQLFileController instance=null;
    private static boolean flagCreateTable=false;
    private static boolean flagInserData=false;
    private static String tableName="";
    private static final Database DATABASE=new Database("usda");
    private static String columnsName="";
    private static String data="";
    private static Table table=null;
   
    private ReadSQLFileController(String sqlFilePath) throws IOException{
        try{
            
            String extension=sqlFilePath.substring(sqlFilePath.lastIndexOf(".") + 1);            
            if (!extension.equalsIgnoreCase("sql")){
                javax.swing.JOptionPane.showMessageDialog(null,"Failed load data. The file must be a SQL script","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            BufferedReader in = new BufferedReader(new FileReader(sqlFilePath));
            String str;
            long init;
            long end; 
            long difference; 
            init = System.currentTimeMillis(); 
            while ((str = in.readLine()) != null) {
                str=str.trim().replaceAll("\\s+$", ""); //remove espações em branco antes e após o conteúdo da String
                CharSequence cs1 = "CREATE TABLE";
                //Início de criação de tabela encontrado
                if (!flagCreateTable && str.toUpperCase().contains(cs1) ){
                    tableName=str;
                    flagCreateTable=!flagCreateTable;
                    continue;
                }
                //Linha que se encontra uma coluna de uma tabela
                if (flagCreateTable && !str.equalsIgnoreCase(");")){
                    String[] tmp=str.split(" ");
                    String columnNameTmp="";
                    for (int i=0; i<tmp.length; i++) {
                        if (!tmp[i].isEmpty()){
                            columnNameTmp=tmp[i];
                            break;
                        }
                    }
                    columnsName+=columnNameTmp+" ";//índice aos quais se encontram o nome da coluna
                }else if(flagCreateTable && str.equalsIgnoreCase(");")){ //final da criação de uma tabela
                    Table t=createTable(tableName, columnsName);
                    if (t!=null) DATABASE.addTable(t);
                }
                
                 //---------------- Verificação de PRIMARY KEYS-------------
                 CharSequence cs3 = "ALTER TABLE";
                 if (str.toUpperCase().contains(cs3)){
                     String[] tmp=str.split(" ");
                     tableName=(tmp.length>1)? tmp[tmp.length-1]: "";
                 }
                 CharSequence cs4 = "PRIMARY KEY";
                 if (str.toUpperCase().contains(cs4)){
                     String[] tmp;
                     tmp=str.split(Pattern.quote("("));
                     tmp=str.split(Pattern.quote("("));
                     if (tmp.length>=2) tmp=tmp[1].split(Pattern.quote(")"));
                     if (tmp.length>=1) tmp=tmp[0].split(Pattern.quote(","));
                     setTablePrimaryKeys(tableName, tmp);
                 }     
            }
            end = System.currentTimeMillis(); 
            difference = end - init; 
            in.close();
            System.out.println("Reading the file took " + (difference / 1000) + " second(s)"); 
            
            init = System.currentTimeMillis();
            in = new BufferedReader(new FileReader(sqlFilePath));
            //Leitura para inserção de dados
            while((str=in.readLine())!=null){
                //----------- Verificação de COPY de dados--------------
                CharSequence cs1 = "FROM STDIN;";
                if (!flagInserData && str.toUpperCase().contains(cs1)){
                    String[] tmp=str.split(" ");
                   table=findTableByName(tmp[1]); //nome da tabela onde serão inseridos os valores
                   flagInserData=!flagInserData;
                   continue;
                }
                //Linha de dados
                if (flagInserData && !str.equalsIgnoreCase("\\.")){
                    String tmp[]=str.split("\\t");
                    addRow(table, tmp);
                }else if(flagInserData && str.equalsIgnoreCase("\\.")){
                    flagInserData=!flagInserData;
                    System.out.println(table.getName()+" has "+table.getTotalOfRows()+" row(s)");
                }
                CharSequence cs2 = "ALTER TABLE";
                if (str.contains(cs2)) break; //termina o laço de inserção de dados
            }
            end = System.currentTimeMillis(); 
            difference = end - init; 
            System.out.println("It took " + (difference / 1000) + " second(s) to insert data"); 
            System.out.println();
        }catch(IOException io){System.out.println(io.getMessage());}
    }
    
    /**
     * Retorna uma referência da classe (Singleton)
     * @param sqlFilePath Diretório para arquivo sql
     * @return Instância da classe ReadSQLFileController
     */
    public static ReadSQLFileController setSQLFile(String sqlFilePath) throws IOException{
        if (instance==null) instance=new ReadSQLFileController(sqlFilePath);
        return instance;
    }
            
    /**
     * Cria uma nova tabela no banco de dados
     * @param tableName nome da tabela
     * @param columnsNames Nomes das colunas, separados por espaço em branco " "
     * @return nova referência de Table
     */
    private Table createTable(String tableName, String columnsNames){
        //Cria as colunas
        String[] columnsNamesArray=columnsNames.split(" ");
        Column[] colums=new Column[columnsNamesArray.length];
        for (int i=0; i<columnsNamesArray.length; i++) colums[i]=new Column(columnsNamesArray[i]);
        CharSequence strCreateTable="CREATE TABLE";
        Table table=null;
        //Cria a tabela
        if (tableName.toUpperCase().contains(strCreateTable)){
            String[] tableNameArray=tableName.toLowerCase().split(" ");//Quebra a srtirng CREATE TABLE
            table=new Table(tableNameArray[2].trim().replaceAll("\\s+$", ""), colums); //Pega o índice que contém o nome da tabela
            columnsName="";
            ReadSQLFileController.tableName=null;
            flagCreateTable=!flagCreateTable;
        }
        return table;
    }
    
    /**
     * Procura por uma tabela no Banco de dados
     * @param tablename nome da tabela
     * @return Referência de uma tabela
     */
    private Table findTableByName(String tablename){
        if (DATABASE!=null) return DATABASE.getTableByName(tablename);
        return null;
    }
     
    /**
     * Adiciona um novo registro em uma tabela
     * @param table tabela
     * @param data Linha de dados
     */
    private void addRow(Table table,String[] data){
        if (table!=null){
            for (int i=0;i<data.length;i++) data[i]=data[i].trim().replaceAll("\\s+$", "");
            table.put(data);
        }
    }
    
    
    /**
     * Procura por um registro na tabela selecionada
     * @param t Tabela
     * @param value chave para busca
     * @return Lista de valores referentes à busca
     */
    public static String[] find(String t, String value){
        if (instance!=null){
            Table table=instance.findTableByName(t);
            if (table!=null && !value.isEmpty()){
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
        }else{
            System.out.println("The method setSQLFile must be invoked by first");
            return null;
        }
    }
 
    /**
     * Procura por um registro na tabela selecionada
     * @param t Nome da Tabela
     * @param value chave para busca
     * @return Lista de valores referentes à busca
     */
    public static String[] find(String t, String value[]){
        if (instance!=null){
            Table table=instance.findTableByName(t);
            if (table!=null && value.length>0){
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
        }else{
            System.out.println("The method setSQLFile must be invoked by first");
            return null;
        }
        
    }
    
    /**
     * Define quais são as chaves primárias de uma tabela
     * @param tablename nome da tabela
     * @param columnsIndexes nomes das chaves primárias
     */
    private void setTablePrimaryKeys(String tablename, String[] columnsIndexes){
        if (DATABASE!=null){
            Table t=findTableByName(tablename);
            if (t!=null){
                Column[] columns= new Column[columnsIndexes.length];
                for (int i=0; i<columnsIndexes.length; i++) columns[i]=new Column(columnsIndexes[i].replaceAll("(^ )|( $)", ""));
                t.setIndexes(columns);
            }
            
        }
    }
}
