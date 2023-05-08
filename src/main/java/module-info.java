module app.kursova {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens app.kursova to javafx.fxml;
    exports app.kursova;
    exports app.kursova.windows;
    opens app.kursova.windows to javafx.fxml;
    exports micro_objects;
    opens micro_objects to javafx.fxml;
    exports Methods;
    opens Methods to javafx.fxml;
}