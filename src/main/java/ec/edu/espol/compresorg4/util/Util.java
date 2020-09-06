/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.compresorg4.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Paz
 */
public class Util {
    
    public static String leerTextoDescomprimido(String ruta){
        StringBuilder sb=new StringBuilder();
        boolean verificar=new File(ruta.replace(".txt","_compress.txt")).exists();
        if(!verificar){
            try(FileReader reader = new FileReader(ruta);
                    BufferedReader br = new BufferedReader(reader)){
                String line;
                while((line=br.readLine())!=null){
                    sb.append(line).append("*");
                }
                return sb.toString();
            }catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
        return null;
    }
    
    public static String leerTextoComprimido(String ruta){
        StringBuilder sb=new StringBuilder();
        boolean verificar=new File(ruta.replace(".txt","_compress.txt")).exists();
        if(verificar){
            try(FileReader reader = new FileReader(ruta);
                    BufferedReader br = new BufferedReader(reader)){
                String line;
                while((line=br.readLine())!=null){
                    sb.append(line);
                }
                return sb.toString();
            }catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
        return null;
    }
    
    public static HashMap<String,Integer> calcularFrecuencias(String texto){
        if(texto==null) return null;
        HashMap<String,Integer> mapa = new HashMap<>();
        for(char c: texto.toCharArray()){
            String valor = String.valueOf(c);
            if(mapa.containsKey(valor))
                mapa.replace(valor, mapa.get(valor)+1);
            else
                mapa.put(valor, 1);
        }
        return mapa;
    }

    public static String binarioHexadecimal(String binario){
        int completeZeros = 0;
        int longitud = binario.length();
        StringBuilder resultado = new StringBuilder();
        int ind = 0;
        while(ind<longitud){
            StringBuilder sb = new StringBuilder();
            for(int j=0; j<4; j++){
                if(ind<longitud)
                    sb.append(binario.charAt(ind++));
                else{
                    sb.append('0');
                    completeZeros++;
                }
            }
            int decimal = Integer.parseInt(sb.toString(), 2);
            resultado.append(Integer.toString(decimal,16));
        }
        for(int i=0; i<completeZeros;i++) 
            resultado.append('-');
        return resultado.toString();       
    }

    public static String hexadecimalBinario(String hexa){
        StringBuilder sb = new StringBuilder();
        int cuenta = 0;
        for(char c: hexa.toCharArray()){
            if(c=='-') cuenta++;
            else{
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
        try(FileWriter wr = new FileWriter(ruta.replace(".txt","_compress.txt"));
                BufferedWriter bw = new BufferedWriter(wr)){
            for(String k: mapa.keySet())
                wr.write(k+"|"+mapa.get(k)+"\n");
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    //method para guardar en archivo al descomprimir y comprimir
    public static void guardarTexto (String ruta, String texto){
        try(FileWriter wr = new FileWriter(ruta);
                BufferedWriter bw = new BufferedWriter(wr)){
            for(char c:texto.toCharArray()){
                if(c!='*')
                    bw.write(c);
                else
                    bw.write("\n");
            }
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public static HashMap<String,String> leerMapa (String ruta){
        HashMap<String,String> mapa = new HashMap<>();
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
