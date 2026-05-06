module com.example.hocjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.hocjavafx to javafx.fxml;
    exports com.example.hocjavafx;
    exports com.example.hocjavafx.model;
    opens com.example.hocjavafx.model to javafx.fxml;
}