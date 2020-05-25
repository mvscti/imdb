/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

/**
 * Classe utilitária para o projeto
 * @author MARCUS VINICIUS
 */
public class Helper {
        
    /**
     * Verifica se um dado é nhumérico ou alfanumérico
     * @param str String
     * @return boolean
     */
    public static boolean isStringNumeric(String str){
        return str.matches("-?\\d+(\\.\\d+)?"); 

    }
   
    public static int hashCode(Double o){
        int h = (o.hashCode() & 0x7fffffff);
        h= h^ (h << 11);
        if (h<0) h=h*-1;
        return h;
    }
    
    /**
     * Converte uma String em um número correspondente, juntando dois
     * dígitos e fazendo rotações DE BITS a cada grupo de dígitos
     * @param v String a ser convertida
     * @return 
     */
    public static int convertStringToIndex(String v){
            int k=v.hashCode() & 0x7fffffff;
            if (k<0) k*=-1;
            return k;
    }
}
