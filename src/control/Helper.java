/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.text.DecimalFormatSymbols;

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
    
    /**
     * Converte uma String em um número correspondente, juntando dois
     * dígitos e fazendo rotações DE BITS a cada grupo de dígitos
     * @param v String a ser convertida
     * @return 
     */
    public static int convertStringToIndex(String v){
        byte[] bytes=v.toLowerCase().getBytes(); //converte a String em código ASCII correspondente
        int count_shift=0; //contador de rotações à esquerda
        byte sum;
        if (bytes.length>0) sum=bytes[0];
        else return -1;
        for (int i=1; i<bytes.length;i++){
            if (i%2!=0)
                ++count_shift;
            if (i+1<bytes.length){
                int tmp=bytes[i+1]<<count_shift;
                sum+=tmp;
                if (sum<0) sum*=-1;
            }
        }
        return sum;
    }
}
