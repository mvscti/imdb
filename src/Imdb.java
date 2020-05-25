/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import view.GraphicalMainScreen;
import control.FileInput;
import control.Helper;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author MARCUS VINICIUS
 */
public class Imdb {
    public static void main(String args[]){
        //CLI
        if (args.length>0){
            String s=args[0];
            System.out.println("Loading dump file. Please Wait....");
            System.out.println();
            FileInput fileController= new FileInput(s);
            Thread t=new Thread(fileController);
            t.start();
            synchronized(t){
                try {
                    t.wait();
                    System.out.println("The dump file load successfully completed");
                } catch (InterruptedException ex) {
                    Logger.getLogger(Imdb.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            GraphicalMainScreen application=new GraphicalMainScreen(true);
            application.setVisible(true);
        }else{ //GUI
            GraphicalMainScreen application=new GraphicalMainScreen();
            application.setVisible(true);
        }
    }
}
