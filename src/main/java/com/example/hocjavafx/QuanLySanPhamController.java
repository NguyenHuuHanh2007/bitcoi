package com.example.hocjavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QuanLySanPhamController implements Initializable {

    @FXML private TableView<SanPham> tableSanPham;
    @FXML private TableColumn<SanPham, Integer> colId;
    @FXML private TableColumn<SanPham, String> colTen;
    @FXML private TableColumn<SanPham, Double> colGia;
    @FXML private TableColumn<SanPham, Integer> colSoLuong;

    @FXML private TextField txtTenSanPham;
    @FXML private TextField txtGiaSanPham;
    @FXML private TextField txtSoLuong;

    private ObservableList<SanPham> danhSachSanPham;
    private int idTuTang = 1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 1. Cài đặt các cột
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTen.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));
        colGia.setCellValueFactory(new PropertyValueFactory<>("giaBan"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));

        // 2. Khởi tạo danh sách và gắn vào bảng
        danhSachSanPham = FXCollections.observableArrayList();
        tableSanPham.setItems(danhSachSanPham);

        // 3. TÍNH NĂNG MỚI: Lắng nghe sự kiện click chọn 1 dòng trong bảng
        tableSanPham.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Khi người dùng bấm vào 1 dòng, tự động bốc dữ liệu dòng đó ném lên các ô TextField
                txtTenSanPham.setText(newSelection.getTenSanPham());
                txtGiaSanPham.setText(String.valueOf(newSelection.getGiaBan()));
                txtSoLuong.setText(String.valueOf(newSelection.getSoLuong()));
            }
        });
    }

    // ==========================================
    // CHỨC NĂNG THÊM - SỬA - XÓA
    // ==========================================

    public void themSanPham(ActionEvent event) {
        try {
            if (txtTenSanPham.getText().isEmpty()) {
                thongBaoLoi("Tên sản phẩm không được để trống!");
                return;
            }

            String ten = txtTenSanPham.getText();
            double gia = Double.parseDouble(txtGiaSanPham.getText());
            int soLuong = Integer.parseInt(txtSoLuong.getText());

            SanPham spMoi = new SanPham(idTuTang++, ten, gia, soLuong);
            danhSachSanPham.add(spMoi);

            xoaTrangCacO(); // Gọi hàm xóa trắng dọn dẹp

        } catch (NumberFormatException e) {
            thongBaoLoi("Giá và Số lượng phải là chữ số hợp lệ!");
        }
    }

    public void suaSanPham(ActionEvent event) {
        // 1. Kiểm tra xem người dùng đã chọn dòng nào chưa
        SanPham spDangChon = tableSanPham.getSelectionModel().getSelectedItem();

        if (spDangChon == null) {
            thongBaoLoi("Vui lòng click chọn một sản phẩm trong bảng để sửa!");
            return;
        }

        try {
            // 2. Lấy dữ liệu mới từ các ô TextField
            String tenMoi = txtTenSanPham.getText();
            double giaMoi = Double.parseDouble(txtGiaSanPham.getText());
            int soLuongMoi = Integer.parseInt(txtSoLuong.getText());

            // 3. Cập nhật lại thông tin cho đối tượng đang chọn (Dùng các hàm Setter)
            spDangChon.setTenSanPham(tenMoi);
            spDangChon.setGiaBan(giaMoi);
            spDangChon.setSoLuong(soLuongMoi);

            // 4. Bắt cái bảng "f5" (refresh) lại để hiển thị chữ mới
            tableSanPham.refresh();

            xoaTrangCacO();
            tableSanPham.getSelectionModel().clearSelection(); // Bỏ chọn dòng

        } catch (NumberFormatException e) {
            thongBaoLoi("Giá và Số lượng phải là chữ số hợp lệ!");
        }
    }

    public void xoaSanPham(ActionEvent event) {
        // 1. Lấy sản phẩm đang được bôi xanh trong bảng
        SanPham spDangChon = tableSanPham.getSelectionModel().getSelectedItem();

        if (spDangChon == null) {
            thongBaoLoi("Vui lòng click chọn một sản phẩm trong bảng để xóa!");
            return;
        }

        // 2. Xóa khỏi "Dây rốn dữ liệu" -> Bảng sẽ tự động mất dòng đó
        danhSachSanPham.remove(spDangChon);

        xoaTrangCacO();
    }

    // ==========================================
    // CÁC HÀM TIỆN ÍCH & ĐIỀU HƯỚNG
    // ==========================================

    private void xoaTrangCacO() {
        txtTenSanPham.clear();
        txtGiaSanPham.clear();
        txtSoLuong.clear();
    }

    public void quayLai(ActionEvent event) throws IOException {
        chuyenTrang(event, "/com/example/hocjavafx/fxml/trangchu.fxml");
    }

    private void thongBaoLoi(String noiDung) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(noiDung);
        alert.showAndWait();
    }

    private void chuyenTrang(ActionEvent event, String duongDanFXML) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(duongDanFXML));
        Parent root = loader.load();
        Scene scene = ((Node) event.getSource()).getScene();
        if (root instanceof Region) {
            ((Region) root).setPrefSize(scene.getWidth(), scene.getHeight());
        }
        scene.setRoot(root);
    }
}