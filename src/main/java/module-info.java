module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens app to javafx.fxml;
    exports app;
    exports data.windows;
    opens data.windows to javafx.fxml;
    exports data.micro_objects;
    exports data.macro_objects;
    opens data.micro_objects to javafx.fxml;
    exports data.functional;
    opens data.functional;
    exports data.interfaces;
    opens data.interfaces to javafx.fxml;
    exports data.world;
    opens data.world to javafx.fxml;
}