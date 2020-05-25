/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;
import control.DatabaseController;
import control.ReadSQLFileController;
import java.io.IOException;
import model.Table;

/**
 *
 * @author MARCUS VINICIUS
 */
public class FileInput implements Runnable{
   private String fileName=null;
   
   public FileInput(String file){
       this.fileName=file;
   }
    
   @Override
   public void run(){
       //Importando os dados do arquivo SQL.
       synchronized(this){
            try {
                ReadSQLFileController.setSQLFile(fileName);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }         
            notify();
        }
    }
}
//   public FileInput(String file){
//        //Importando os dados do arquivo SQL.
//        try {
//            ReadSQLFileController.setSQLFile(file);
//        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//        }
//        long init;
//        long end; 
//        long difference; 
//        String[] param={ "01001", "305"};
//        init = System.currentTimeMillis();
//        //Procurando um item
//        String[] s=ReadSQLFileController.find("nut_data", param);
//        end = System.currentTimeMillis(); 
//        difference = end - init; 
//        if (s!=null){
//            System.out.print("{");
//            for (int i=0;i<s.length; i++){
//                if (i!=0) System.out.print(",");
//                System.out.print("'"+s[i]+"'");
//            }
//            System.out.println("}");
//            System.out.println("Found in "+difference+" seconds");
//            System.out.println();
//        }else{
//            System.out.println("Not found");
//        }
//        
//        //Busca por chave composta
//        String[] param2={ "93600", "435"};
//        init = System.currentTimeMillis();
//        s=ReadSQLFileController.find("nut_data", param2);
//        end = System.currentTimeMillis(); 
//        difference = end - init; 
//        if (s!=null){
//            System.out.print("{");
//            for (int i=0;i<s.length; i++){
//                if (i!=0) System.out.print(",");
//                System.out.print("'"+s[i]+"'");
//            }
//            System.out.println("}");
//            System.out.println("Found in "+difference+" seconds");
//        }else{
//            System.out.println("Not found");
//        }
//        
//   }