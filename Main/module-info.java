module com.example.loginui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.loginui to javafx.fxml;
    exports com.example.loginui;
}