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
import javafx.scene.layout.Region; // Bổ sung thư viện này

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TrangChuController implements Initializable {

    @FXML
    private Label lblUsername;

    @FXML
    private TableView<?> tableAuctions;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Code chạy khi màn hình trang chủ vừa load lên
        // Có thể load dữ liệu từ database vào TableView ở đây
    }
    public void dangXuat(ActionEvent event) throws IOException {
        // Đăng xuất thì đá người dùng văng ra lại màn hình Login mượt mà
        chuyenTrang(event, "/com/example/hocjavafx/fxml/login.fxml");
    }
    public void quanLySanPham(ActionEvent event) throws IOException{
        chuyenTrang(event, "/com/example/hocjavafx/fxml/quanlysanpham.fxml");
    }

    // ==========================================
    // HÀM XỬ LÝ LÕI
    // ==========================================

    private void chuyenTrang(ActionEvent event, String duongDanFXML) throws IOException {
        // 1. Tải giao diện mới
        FXMLLoader loader = new FXMLLoader(getClass().getResource(duongDanFXML));
        Parent root = loader.load();

        // 2. Lấy Scene hiện tại
        Scene scene = ((Node) event.getSource()).getScene();

        // 3. Ép kích thước khung mới bằng với kích thước màn hình hiện tại để chống chớp giật
        if (root instanceof Region) {
            Region newRegion = (Region) root;
            newRegion.setPrefSize(scene.getWidth(), scene.getHeight());
        }

        // 4. Lột xác!
        scene.setRoot(root);
    }
}