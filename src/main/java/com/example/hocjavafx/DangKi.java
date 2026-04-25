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
import javafx.scene.layout.Region; // Thư viện để ép kích thước

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DangKi implements Initializable {

    @FXML
    private Label lblUsername;

    @FXML
    private TableView<?> tableAuctions;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Khởi tạo dữ liệu khi mở trang Đăng Ký
    }

    public void dangKiNgay(ActionEvent event) throws IOException {
        // Đăng ký xong thì chuyển ngược về trang Đăng Nhập cực kỳ êm ái
        chuyenTrang(event, "/com/example/hocjavafx/fxml/login.fxml");
    }

    // ==========================================
    // HÀM XỬ LÝ LÕI (COPY TỪ CLASS LOGIN SANG)
    // ==========================================

    private void chuyenTrang(ActionEvent event, String duongDanFXML) throws IOException {
        // 1. Tải giao diện mới (trong trường hợp này là login.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource(duongDanFXML));
        Parent root = loader.load();

        // 2. Lấy Scene hiện tại
        Scene scene = ((Node) event.getSource()).getScene();

        // 3. Ép kích thước khung mới bằng với kích thước màn hình hiện tại
        if (root instanceof Region) {
            Region newRegion = (Region) root;
            newRegion.setPrefSize(scene.getWidth(), scene.getHeight());
        }

        // 4. Lột xác!
        scene.setRoot(root);
    }
}