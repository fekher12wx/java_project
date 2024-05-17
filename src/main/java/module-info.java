module com.example.planmanagmentsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires de.jensd.fx.glyphs.fontawesome;
    //requires mysql.connector.java;


    opens com.example.planmanagmentsystem to javafx.fxml;
    exports com.example.planmanagmentsystem;
}