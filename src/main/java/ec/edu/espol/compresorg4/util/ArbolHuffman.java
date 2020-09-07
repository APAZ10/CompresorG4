/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.compresorg4.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

/**
 *
 * @author Paz
 */
public class ArbolHuffman {
    private Node root;
    
    private class Node {
        private final String data;
        private final Integer freq;
        private Node left;
        private Node right;
        
        public Node(String data, Integer freq) {
            this.data = data;
            this.freq = freq;
        }    
    }
    
    public void calcularArbol(Map<String,Integer> mapa){
        PriorityQueue<Node> cola = new PriorityQueue<>((Node n1, Node n2)-> n1.freq-n2.freq);
        for(Entry<String, Integer> entry : mapa.entrySet())
            cola.offer(new Node(entry.getKey(),entry.getValue()));
        while(cola.size()>1){
            Node n1 = cola.poll();
            Node n2 = cola.poll();
            Node parent = new Node(n1.data+n2.data,n1.freq+n2.freq);
            parent.left = n1;
            parent.right = n2;
            cola.offer(parent);
        }
        root = cola.poll();
    }
    
    public Map<String,String> calcularCodigos (){
        Map<String,String> resultado = new HashMap<>();
        calcularCodigos("",resultado,root);
        return resultado;
    }

    private void calcularCodigos(String binary, Map<String,String> mapa, Node n){
        if(n.left==null&&n.right==null) mapa.put(n.data, binary);
        else{
            calcularCodigos(binary.concat("1"),mapa,n.left);
            calcularCodigos(binary.concat("0"),mapa,n.right);
        }
    }
    
    public static String codificar (String texto, Map<String,String> mapa){
        StringBuilder sb = new StringBuilder();
        for(char c: texto.toCharArray()){
            String codigo = mapa.get(String.valueOf(c));
            sb.append(codigo);
        }
        return sb.toString();
    }
    
    public static String decodificar (String texto, Map<String,String> mapa){
        StringBuilder resultado = new StringBuilder();
        StringBuilder agrupador = new StringBuilder();
        for(char c: texto.toCharArray()){
            agrupador.append(c);
            if(mapa.containsValue(agrupador.toString())){
                resultado.append(getKeyFromValue(mapa,agrupador.toString()));
                agrupador = new StringBuilder();
            }
        }
        return resultado.toString();
    }
    
    private static String getKeyFromValue(Map<String,String> mapa, String val){
        for (Entry<String, String> entry : mapa.entrySet()) {
            if (entry.getValue().equals(val)) 
                return entry.getKey();
        }
        return null;
    }

}
