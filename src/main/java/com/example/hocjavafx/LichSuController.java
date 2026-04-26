package com.example.hocjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LichSuController implements Initializable {
    @FXML private ListView<String> listLichSu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listLichSu.setItems(Session.lichSuGiaoDich);
    }

    public void quayLai(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/hocjavafx/fxml/trangchu.fxml"));
        Scene scene = ((Node) event.getSource()).getScene();
        if (root instanceof Region) ((Region) root).setPrefSize(scene.getWidth(), scene.getHeight());
        scene.setRoot(root);
    }
}