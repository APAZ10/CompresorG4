module ec.edu.espol.compresorg4 {
    requires javafx.controls;
    requires javafx.fxml;

    opens ec.edu.espol.compresorg4 to javafx.fxml;
    exports ec.edu.espol.compresorg4;
}