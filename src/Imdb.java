/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import view.LoadSqlFile;
import view.FileInput;
/**
 *
 * @author MARCUS VINICIUS
 */
public class Imdb {
    public static void main(String args[]){
        if (args.length>0){
            String s=args[0];
            FileInput fileController= new FileInput(s);
        }else{
            LoadSqlFile application=new LoadSqlFile();
            application.setVisible(true);
        }
    }
}
