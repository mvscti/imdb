/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import control.DatabaseController;
import model.Database;
import model.Table;

/**
 * Classe exemplo para criação das próprias tabelas, inserção de bsucas de dados
 * @author MARCUS VINICIUS
 */
public class UserDefinied {
 
    /**
     * Exibe os menus disponíveis
     */
    public void showMainMenu(){
        System.out.println("Operations allowed");
        System.out.println("1-Insert data in some table");
        System.out.println("2-Delete row from table");
        System.out.println("3-Select data");
        System.out.println("4-Create a new table in Database");
        System.out.println("5-Import a new Dump file (Overwriten any existing database)");
        System.out.println();
        System.out.print("Choose any operation: ");
    }
   
    public UserDefinied(){
        DatabaseController dbc= new DatabaseController();
        //Criando uma base de dados
        Database db=dbc.createDatabase("usda");
        String[] columns={"datasrc_id", "authors", "title", "year", "journal"}; 
        //Posições das colunas chave, iniciando por 0
        //para definir uma chave composta, basta criar um vetor com os índices
        int[] keys ={0};
        Table t1=dbc.createTable(db, "data_src", columns, keys);
        //Registros
        String[] data1 = {"D1066", "G.V. Mann", "The Health and Nutritional", "1962", "566"}; 
        String[] data2 = {"D1073", "J.P. McBride, R.A.", "Maclead Sodium and potassium in fish from the Canadian Pacific coast", "1956", "Journal of the American Dietetic Association"}; 
        String[] data3 = {"D1417", "O. Longe", "Effect of boiling on the carbohydrate constituents of some non-leafy vegetables", "1981", "Food Chemistry"}; 
        try{
            //Adicionando linhas na tabela
            if (dbc.addRow(t1, data1)) System.out.println("1 row inserted on table "+t1.getName());
            if (dbc.addRow(t1, data2)) System.out.println("1 row inserted on table "+t1.getName());
            if (dbc.addRow(t1, data3)) System.out.println("1 row inserted on table "+t1.getName());
        }catch(Exception e){System.out.println(e.getMessage());}
        //Removendo um registro
        if (dbc.deleteRow(t1, "D1073")) System.out.println("Deleted");
        String[] s;
        //Chave para busca
        //Em caso de uma busca em uma tabela com chave composta, basta criar um array 
        //com o número de índices chave que se deseja procurar
        String[] key={"D1066"};
        //Procurando por um registro
        s=dbc.find(t1,key);
         if (s!=null){
            System.out.print("{");
            for (int i=0;i<s.length; i++){
                if (i!=0) System.out.print(",");
                System.out.print("'"+s[i]+"'");
            }
            System.out.println("}");
        }else{
            System.out.println("Not found");
        }
    }
    
    
}