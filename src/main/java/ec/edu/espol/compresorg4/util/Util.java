/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.compresorg4.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Paz
 */
public class Util {
    
    public static String leerTexto(String ruta){
        try(FileReader reader = new FileReader(ruta);
                BufferedReader br = new BufferedReader(reader)){
            //Implementar para varias lineas
            return br.readLine();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public static HashMap<String,Integer> calcularFrecuencias(String texto){
        if(texto==null) return null;
        HashMap<String,Integer> mapa = new HashMap<>();
        for(char c: texto.toCharArray()){
            String valor = String.valueOf(c); //comprobar
            if(mapa.containsKey(valor))
                mapa.replace(valor, mapa.get(valor)+1);
            else
                mapa.put(valor, 1);
        }
        return mapa;
    }
    //Hacer refactor
    public static String binarioHexadecimal(String binario){
        int completeZeros = 0;
        int longitud = binario.length();
        StringBuilder resultado = new StringBuilder();
        for(int i=0; i<longitud;i++){
            StringBuilder sb = new StringBuilder();
            for(int j=0; j<4; j++){
                if(i<longitud)
                    sb.append(binario.charAt(i++));
                else{
                    sb.append('0');
                    completeZeros++;
                }
            }
            i--; //evita saltarse chars, cambiar algoritmo
            int decimal = Integer.parseInt(sb.toString(), 2);
            resultado.append(Integer.toString(decimal,16));
        }
        for(int i=0; i<completeZeros;i++) 
            resultado.append('-');
        return resultado.toString();       
    }
    //Hacer refactor
    public static String hexadecimalBinario(String hexa){
        StringBuilder sb = new StringBuilder();
        int cuenta = 0;
        for(char c: hexa.toCharArray()){
            if(c=='-') cuenta++;
            else if(c!='|'){
                int decimal = Integer.valueOf(String.valueOf(c), 16);
                String binario = Integer.toBinaryString(decimal);
                int restante = 4-binario.length();
                while(restante!=0){
                    sb.append("0");
                    restante--;
                }
                sb.append(binario);
            }
        }
        int longitud = sb.toString().length();
        String resultado = sb.delete(longitud-cuenta,longitud).toString();
        return resultado;
    }
    
    //method para guardar en archivo al comprimir y generar _compress.txt
    public static void guardarTexto (String ruta, String texto, HashMap<String,String> mapa){
        guardarTexto(ruta,texto);
        //cambiar como se guarda el nombre
        try(FileWriter wr = new FileWriter(ruta.replace(".txt","_compress.txt"));
                BufferedWriter bw = new BufferedWriter(wr)){
            for(String k: mapa.keySet())
                wr.write(k+"|"+mapa.get(k)+"\n");
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    //method para guardar en archivo al descomprimir
    public static void guardarTexto (String ruta, String texto){
        try(FileWriter wr = new FileWriter(ruta);
                BufferedWriter bw = new BufferedWriter(wr)){
            //Se puede implementar para varias lineas?
            bw.write(texto);
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public static HashMap<String,String> leerMapa (String ruta){
        HashMap<String,String> mapa = new HashMap<>();
        //cambiar como se guarda el nombre
        try(FileReader reader = new FileReader(ruta.replace(".txt","_compress.txt"));
                BufferedReader br = new BufferedReader(reader)){
            String line;
            while((line=br.readLine())!=null){
                String[] info = line.split("\\|");
                mapa.put(info[0], info[1]);
            }
        }catch(IOException ex){
            System.out.println("error");
        }
        return mapa;
    }
    
}
