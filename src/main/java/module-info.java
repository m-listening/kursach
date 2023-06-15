module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens app to javafx.fxml;
    exports app;
    exports data.windows;
    opens data.windows to javafx.fxml;
    exports data.objects.micro_objects;
    exports data.objects.macro_objects;
    exports data.functional;
    opens data.functional;
    exports data.interfaces;
    opens data.interfaces to javafx.fxml;
    exports data.world;
    opens data.world to javafx.fxml;
    opens data.objects.micro_objects;
    exports data.functional.forObjects;
    opens data.functional.forObjects;
    exports data.functional.PressedHandlers;
    opens data.functional.PressedHandlers;
    exports data.functional.forObjects.micro;
    opens data.functional.forObjects.micro;
}