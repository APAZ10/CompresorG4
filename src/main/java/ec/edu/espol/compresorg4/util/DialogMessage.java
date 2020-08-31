/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.compresorg4.util;

import javafx.scene.control.Alert;

/**
 *
 * @author lfrei
 */
public class DialogMessage {
    public static void NullAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Seleccionar Archivo");
        alert.setContentText("No ha seleccionado un archivo");
        alert.showAndWait();
    }
    public static void finalizarAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Terminado");
        alert.setContentText("Proceso finalizado correctamente");
        alert.showAndWait();
    }
}
