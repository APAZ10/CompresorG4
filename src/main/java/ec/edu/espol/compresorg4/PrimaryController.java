package ec.edu.espol.compresorg4;

import ec.edu.espol.compresorg4.util.ArbolHuffman;
import ec.edu.espol.compresorg4.util.DialogMessage;
import ec.edu.espol.compresorg4.util.Util;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class PrimaryController implements Initializable{
    
    private FileChooser fileChooser;
    private String fileName;
    private String ruta;
    
    @FXML
    private TextField txtComprimir;
    
    @FXML
    private TextField txtDescomprimir;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Select .txt file");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt") );
    }
    
    @FXML
    private void comprimir() throws IOException{
        //validar ruta no sea null, que se haya seleccionado primero, mostrar ruta
        File archivo = fileChooser.showOpenDialog(App.getStage());
        if(archivo==null){
            DialogMessage.NullAlert();
        }else{
            txtComprimir.setText(archivo.getName());
            System.out.println(archivo.getName());
            ruta = archivo.getAbsolutePath();
            System.out.println(ruta);
            String texto = Util.leerTexto(ruta);
            System.out.println(ruta);
            HashMap<String,Integer> mapaFreq = Util.calcularFrecuencias(texto);
            ArbolHuffman ht = new ArbolHuffman();
            ht.calcularArbol(mapaFreq);
            HashMap<String,String> codigosHauf = ht.calcularCodigos();
            String codificadoBinario = ArbolHuffman.codificar(texto, codigosHauf); 
            String codificadoHexa = Util.binarioHexadecimal(codificadoBinario);
            Util.guardarTexto(ruta, codificadoHexa, codigosHauf);
            DialogMessage.finalizarAlert();
            System.out.println("todo correcto");
        }
        
        //verificaciones para bloquear el boton
    }
    
    @FXML
    private void descomprimir() throws IOException{
        //validar ruta no sea null, que se haya seleccionado primero, mostrar ruta
        //debe existir el archivo _compress.txt
        File archivo = fileChooser.showOpenDialog(App.getStage());
        if(archivo==null){
            DialogMessage.NullAlert();
        }else{
            txtDescomprimir.setText(archivo.getName());
            System.out.println(archivo.getName());
            ruta = archivo.getAbsolutePath();
            System.out.println(ruta);
            String texto = Util.leerTexto(ruta);
            String textoBinario = Util.hexadecimalBinario(texto);
            HashMap<String,String> mapDecodificador = Util.leerMapa(ruta);
            String decodificado = ArbolHuffman.decodificar(textoBinario, mapDecodificador);
            System.out.println(decodificado);
            Util.guardarTexto(ruta, decodificado);
            DialogMessage.finalizarAlert();
        }//verificaciones para bloquear el boton
    }
    
    
}
