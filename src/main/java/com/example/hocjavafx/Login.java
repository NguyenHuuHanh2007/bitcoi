package com.example.hocjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private Label lblUsername;

    @FXML
    private TableView<?> tableAuctions; // Sau này bạn sẽ map với class SanPham/Auction của bạn [cite: 112, 116]

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Code chạy khi màn hình trang chủ vừa load lên
        // Có thể load dữ liệu từ database vào TableView ở đây [cite: 130]
    }
    // Sự kiện khi bấm nút Đăng Xuất
    public void taiKhoan(ActionEvent event) throws IOException {

    }
    public void matKhau(ActionEvent event) throws IOException {

    }
    public void dangNhap(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hocjavafx/fxml/trangchu.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.hide();

        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
    }
    public void dangKy(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hocjavafx/fxml/dangki.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
    public void quenMatKhau(ActionEvent event) throws IOException {

    }
    public void dangNhapAdmin(ActionEvent event) throws IOException {

    }
}