module com.example.media1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.media1 to javafx.fxml;
    exports com.example.media1;
}