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
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

public class PrimaryController implements Initializable{
    
    private FileChooser fileChooser;
    private String fileName;
    private String rutaComprimir;
    private String rutaDescomprimir;
    
    @FXML
    private Label txtComprimir;
    @FXML
    private Label txtDescomprimir;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Select .txt file");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt") );
    }
    
    @FXML
    private void fileComprimir() throws IOException{
        File archivo = fileChooser.showOpenDialog(App.getStage());
        if(archivo==null){
            DialogMessage.nullAlert();
        }else{
            txtComprimir.setText(archivo.getName());
            rutaComprimir = archivo.getAbsolutePath();
        }
    }
    
    @FXML
    private void comprimir() throws IOException{
        if(rutaComprimir==null){
            DialogMessage.nullAlert();
        }else{
            String texto = Util.leerTextoDescomprimido(rutaComprimir);
            if(texto!=null){
                HashMap<String,Integer> mapaFreq = Util.calcularFrecuencias(texto);
                ArbolHuffman ht = new ArbolHuffman();
                ht.calcularArbol(mapaFreq);
                HashMap<String,String> codigosHauf = ht.calcularCodigos();
                String codificadoBinario = ArbolHuffman.codificar(texto, codigosHauf); 
                String codificadoHexa = Util.binarioHexadecimal(codificadoBinario);
                Util.guardarTexto(rutaComprimir, codificadoHexa, codigosHauf);
                DialogMessage.finalizarAlert();
            }else
                DialogMessage.errorAlert();
            txtComprimir.setText("Seleccionar otro archivo");
            rutaComprimir=null;
        }
    }
    
    @FXML
    private void fileDescomprimir() throws IOException{
        File archivo = fileChooser.showOpenDialog(App.getStage());
        if(archivo==null){
            DialogMessage.nullAlert();
        }else{
            txtDescomprimir.setText(archivo.getName());
            rutaDescomprimir = archivo.getAbsolutePath();
        }
    }
    
    @FXML
    private void descomprimir() throws IOException{
        if(rutaDescomprimir==null)
            DialogMessage.nullAlert();
        else{
            String texto = Util.leerTextoComprimido(rutaDescomprimir);
            if(texto!=null){
                String textoBinario = Util.hexadecimalBinario(texto);
                HashMap<String,String> mapDecodificador = Util.leerMapa(rutaDescomprimir);
                String decodificado = ArbolHuffman.decodificar(textoBinario, mapDecodificador);
                Util.guardarTexto(rutaDescomprimir, decodificado);
                new File(rutaDescomprimir.replace(".txt","_compress.txt")).delete();
                DialogMessage.finalizarAlert();
            }else
                DialogMessage.errorAlert();
            txtDescomprimir.setText("Seleccionar otro archivo");
            rutaDescomprimir=null;
        }
    }
      
}
