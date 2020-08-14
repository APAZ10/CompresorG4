/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.compresorg4.util;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 *
 * @author Paz
 */
public class ArbolHuffman {
    private Node root;
    
    private class Node {
        private String data;
        private Integer freq;
        private Node left;
        private Node right;
        
        public Node(String data, Integer freq) {
            this.data = data;
            this.freq = freq;
        }    
    }
    
    public void calcularArbol(HashMap<String,Integer> mapa){
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
    
    public HashMap<String,String> calcularCodigos (){
        HashMap<String,String> resultado = new HashMap<>();
        calcularCodigos("",resultado,root);
        return resultado;
    }
    //refactor
    private void calcularCodigos(String binary, HashMap<String,String> mapa, Node n){
        if(n.left==null&&n.right==null) mapa.put(n.data, binary);
        else{
            calcularCodigos(binary.concat("1"),mapa,n.left);
            calcularCodigos(binary.concat("0"),mapa,n.right); // implementar con string builder?
        }
    }
    
    public static String codificar (String texto, HashMap<String,String> mapa){
        StringBuilder sb = new StringBuilder();
        for(char c: texto.toCharArray()){
            String codigo = mapa.get(String.valueOf(c)); //comprobar
            sb.append(codigo);
        }
        return sb.toString();
    }
    
    public static String decodificar (String texto, HashMap<String,String> mapa){
        StringBuilder resultado = new StringBuilder();
        StringBuilder agrupador = new StringBuilder();
        for(char c: texto.toCharArray()){
            agrupador.append(c);
            //agrupar hasta encontrar la primera coincidencia ya que
            //los codigos son unicos
            //refactor
            if(mapa.containsValue(agrupador.toString())){
                resultado.append(getKeyFromValue(mapa,agrupador.toString()));
                agrupador = new StringBuilder();
            }
        }
        return resultado.toString();
    }
    private static String getKeyFromValue(HashMap<String,String> mapa, String val){
        for (Entry<String, String> entry : mapa.entrySet()) {
            if (Objects.equals(val, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    //adicional para verificar si el arbol fue bien creado, eliminar al terminar
    public boolean verificarSumaFreqHijos(){
        return verificarSumaFreqHijos(root);
    }
    
    private boolean verificarSumaFreqHijos(Node n){
        if(n==null || (n.left==null && n.right==null)) return true;
        int fleft = n.left.freq;//n.left!=null ? n.left.freq:0;
        int fright = n.right.freq;//n.right!=null ? n.right.freq:0;
        return (fleft+fright==n.freq)&&verificarSumaFreqHijos(n.left)&&verificarSumaFreqHijos(n.right);
    }
    
}
