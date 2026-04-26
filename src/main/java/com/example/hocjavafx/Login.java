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
import javafx.scene.layout.Region; // Thêm thư viện này để ép kích thước màn hình
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private Label lblUsername;

    @FXML
    private TableView<?> tableAuctions;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Code chạy khi màn hình vừa load lên
    }

    public void taiKhoan(ActionEvent event) throws IOException {
    }

    public void matKhau(ActionEvent event) throws IOException {
    }

    // ==========================================
    // CÁC NÚT BẤM CHUYỂN TRANG
    // ==========================================

    public void dangNhap(ActionEvent event) throws IOException {
        chuyenTrang(event, "/com/example/hocjavafx/fxml/trangchu.fxml");
    }

    public void dangKy(ActionEvent event) throws IOException {
        // Gọi hàm dùng chung, truyền vào Event và đường dẫn FXML Đăng Ký
        chuyenTrang(event, "/com/example/hocjavafx/fxml/dangki.fxml");
    }

    public void quenMatKhau(ActionEvent event) throws IOException {
    }

    public void dangNhapAdmin(ActionEvent event) throws IOException {
    }

    // ==========================================
    // HÀM XỬ LÝ LÕI (DÙNG CHUNG ĐỂ CHỐNG GIẬT LAG)
    // ==========================================

    private void chuyenTrang(ActionEvent event, String duongDanFXML) throws IOException {
        // 1. Tải giao diện mới dựa trên đường dẫn truyền vào
        FXMLLoader loader = new FXMLLoader(getClass().getResource(duongDanFXML));
        Parent root = loader.load();

        // 2. Lấy Scene (Căn phòng) hiện tại
        Scene scene = ((Node) event.getSource()).getScene();

        // 3. FIX LỖI NHẢY HÌNH (1-FRAME DELAY):
        // Ép giao diện mới phải bành trướng to bằng Căn phòng hiện tại TRƯỚC KHI xuất hiện
        if (root instanceof Region) {
            Region newRegion = (Region) root;
            newRegion.setPrefSize(scene.getWidth(), scene.getHeight());
        }

        // 4. Lột xác (Thay ruột mà không động chạm tới Stage)
        scene.setRoot(root);
    }
}