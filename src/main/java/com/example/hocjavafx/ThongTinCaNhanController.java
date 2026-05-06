package com.example.hocjavafx;

import com.example.hocjavafx.model.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ThongTinCaNhanController implements Initializable {

    @FXML private Label lblTenDangNhap;
    @FXML private Label lblSoDuTaiKhoan;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        capNhatGiaoDien();
    }

    private void capNhatGiaoDien() {
        // Đã sửa lại đúng nhãn cho từng biến
        lblTenDangNhap.setText(Session.getInstance().getTenNguoiDung());
        lblSoDuTaiKhoan.setText(String.format("%,.0f VNĐ", Session.getInstance().getSoDu()));
    }

    public void napTien(ActionEvent event) {
        double soDuHienTai = Session.getInstance().getSoDu();

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nạp tiền");
        dialog.setHeaderText("Ví hiện tại: " + String.format("%,.0f VNĐ", soDuHienTai));
        dialog.setContentText("Nhập số tiền muốn nạp (VNĐ):");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                double tienNap = Double.parseDouble(result.get());
                if (tienNap <= 0) {
                    thongBaoLoi("Số tiền nạp phải lớn hơn 0!");
                    return;
                }

                // Cập nhật số dư qua Singleton
                Session.getInstance().setSoDu(soDuHienTai + tienNap);

                // Ghi vào lịch sử giao dịch
                Session.getInstance().themLichSu("[+] Nạp tiền: +" + String.format("%,.0f VNĐ", tienNap));

                capNhatGiaoDien();
                thongBaoThanhCong("Nạp thành công " + String.format("%,.0f VNĐ", tienNap));
            } catch (NumberFormatException e) {
                thongBaoLoi("Vui lòng chỉ nhập số hợp lệ!");
            }
        }
    }

    public void rutTien(ActionEvent event) {
        double soDuHienTai = Session.getInstance().getSoDu();

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Rút tiền");
        dialog.setHeaderText("Ví hiện tại: " + String.format("%,.0f VNĐ", soDuHienTai));
        dialog.setContentText("Nhập số tiền muốn rút (VNĐ):");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                double tienRut = Double.parseDouble(result.get());
                if (tienRut <= 0) {
                    thongBaoLoi("Số tiền rút phải lớn hơn 0!");
                    return;
                }
                if (tienRut > soDuHienTai) {
                    thongBaoLoi("Số dư không đủ để rút!");
                    return;
                }

                // Cập nhật số dư qua Singleton
                Session.getInstance().setSoDu(soDuHienTai - tienRut);

                // Ghi vào lịch sử giao dịch
                Session.getInstance().themLichSu("[-] Rút tiền: -" + String.format("%,.0f VNĐ", tienRut));

                capNhatGiaoDien();
                thongBaoThanhCong("Rút thành công " + String.format("%,.0f VNĐ", tienRut));
            } catch (NumberFormatException e) {
                thongBaoLoi("Vui lòng chỉ nhập số hợp lệ!");
            }
        }
    }

    public void quayLai(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hocjavafx/fxml/trangchu.fxml"));
        Parent root = loader.load();
        Scene scene = ((Node) event.getSource()).getScene();
        if (root instanceof Region) {
            ((Region) root).setPrefSize(scene.getWidth(), scene.getHeight());
        }
        scene.setRoot(root);
    }

    private void thongBaoLoi(String noiDung) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(noiDung);
        alert.showAndWait();
    }

    private void thongBaoThanhCong(String noiDung) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thành công");
        alert.setHeaderText(null);
        alert.setContentText(noiDung);
        alert.showAndWait(); // Đã xóa bớt một dòng để không bị lặp popup
    }
}