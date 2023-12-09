module com.example.shoplistv {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.kotlin;

    opens com.example.shoplistv2 to javafx.fxml;
    exports com.example.shoplistv2;
    exports com.example.shoplistv2.data.model;
    opens com.example.shoplistv2.data.model to com.fasterxml.jackson.databind;

}