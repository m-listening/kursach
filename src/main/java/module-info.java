module app.kursova {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens app.kursova to javafx.fxml;
    exports app.kursova;
    exports app.kursova.windows;
    opens app.kursova.windows to javafx.fxml;
}