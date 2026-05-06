package com.example.hocjavafx;

// Đã thêm import PhienDauGia từ gói model
import com.example.hocjavafx.PhienDauGia;
import com.example.hocjavafx.model.Session;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;

public class TrangChuController implements Initializable {

    @FXML private Label lblUsername;
    @FXML private TableView<PhienDauGia> tableAuctions;
    @FXML private TableColumn<PhienDauGia, Integer> colID;
    @FXML private TableColumn<PhienDauGia, String> colTenSP;
    @FXML private TableColumn<PhienDauGia, Double> colGiaKhoiDiem;
    @FXML private TableColumn<PhienDauGia, Double> colGiaHienTai;
    @FXML private TableColumn<PhienDauGia, String> colThoiGian;
    @FXML private TableColumn<PhienDauGia, String> colTrangThai;

    public static ObservableList<PhienDauGia> danhSachDauGia = FXCollections.observableArrayList();
    private static Timeline dongHo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        capNhatHienThiUser();

        // Khởi tạo dữ liệu mẫu nếu danh sách trống
        if (danhSachDauGia.isEmpty()) {
            PhienDauGia p1 = new PhienDauGia(1, "Laptop Gaming RTX 4060", 15000000, 15000000, "", "Đang đấu giá", 5, "Mới 100%", "");
            p1.setThoiGianKetThuc(LocalDateTime.now().plusMinutes(5));

            PhienDauGia p2 = new PhienDauGia(2, "iPhone 15 Pro Max", 20000000, 20000000, "", "Đang đấu giá", 2, "Like New 99%", "");
            p2.setThoiGianKetThuc(LocalDateTime.now().plusSeconds(30));

            danhSachDauGia.addAll(p1, p2);
        }

        // Đổ dữ liệu vào các cột
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTenSP.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));
        colGiaKhoiDiem.setCellValueFactory(new PropertyValueFactory<>("giaKhoiDiem"));
        colGiaHienTai.setCellValueFactory(new PropertyValueFactory<>("giaHienTai"));

        // Định dạng hiển thị tiền VNĐ cho đẹp
        colGiaKhoiDiem.setCellFactory(column -> new TableCell<>() {
            @Override protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(String.format("%,.0f VNĐ", item));
            }
        });
        colGiaHienTai.setCellFactory(column -> new TableCell<>() {
            @Override protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(String.format("%,.0f VNĐ", item));
            }
        });

        colThoiGian.setCellValueFactory(new PropertyValueFactory<>("thoiGian"));
        colTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));

        // Chỉ hiển thị các phiên đang đấu giá hoặc vừa kết thúc
        FilteredList<PhienDauGia> danhSachTrenSan = new FilteredList<>(danhSachDauGia, phien -> {
            String tt = phien.getTrangThai();
            return tt.equals("Đang đấu giá") || tt.equals("Đã bán") || tt.equals("Hết thời gian");
        });

        tableAuctions.setItems(danhSachTrenSan);

        // Chạy đồng hồ đếm ngược
        if (dongHo != null) dongHo.stop();
        dongHo = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime now = LocalDateTime.now();
            for (PhienDauGia phien : danhSachDauGia) {
                if (phien.getTrangThai().equals("Đang đấu giá") && phien.getThoiGianKetThuc() != null) {
                    long totalSeconds = ChronoUnit.SECONDS.between(now, phien.getThoiGianKetThuc());
                    if (totalSeconds <= 0) {
                        phien.setThoiGian("00:00:00");
                        phien.setTrangThai(phien.getGiaHienTai() > phien.getGiaKhoiDiem() ? "Đã bán" : "Hết thời gian");
                    } else {
                        long h = totalSeconds / 3600;
                        long m = (totalSeconds % 3600) / 60;
                        long s = totalSeconds % 60;
                        phien.setThoiGian(String.format("%02d:%02d:%02d", h, m, s));
                    }
                }
            }
            tableAuctions.refresh();
        }));
        dongHo.setCycleCount(Timeline.INDEFINITE);
        dongHo.play();
    }

    @FXML
    public void xemChiTietSP(ActionEvent event) {
        PhienDauGia phien = tableAuctions.getSelectionModel().getSelectedItem();
        if (phien == null) { thongBaoLoi("Vui lòng chọn sản phẩm trong bảng!"); return; }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Chi tiết sản phẩm");
        dialog.setHeaderText("Thông tin: " + phien.getTenSanPham());

        VBox vbox = new VBox(15);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);
        try {
            if (phien.getDuongDanAnh() != null && !phien.getDuongDanAnh().isEmpty()) {
                imageView.setImage(new Image(phien.getDuongDanAnh()));
            }
        } catch (Exception ignored) {}

        Label lblGia = new Label("💰 Giá hiện tại: " + String.format("%,.0f VNĐ", phien.getGiaHienTai()));
        lblGia.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
        Label lblTinhTrang = new Label("✨ Tình trạng: " + phien.getTinhTrang());
        Label lblTonKho = new Label("📦 Số lượng trong kho: " + phien.getSoLuong());

        vbox.getChildren().addAll(imageView, lblGia, lblTinhTrang, lblTonKho);
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }

    @FXML
    public void thamGiaDauGia(ActionEvent event) {
        PhienDauGia phien = tableAuctions.getSelectionModel().getSelectedItem();
        if (phien == null) { thongBaoLoi("Chọn một sản phẩm để đấu giá!"); return; }

        int soLuong = phien.getSoLuong();

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Ra giá đấu");
        dialog.setHeaderText("Sản phẩm: " + phien.getTenSanPham() + " (Số lượng: " + soLuong + ")");
        dialog.setContentText("Nhập giá cho mỗi sản phẩm (VNĐ):");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            try {
                double giaMoiMoiMon = Double.parseDouble(result.get());
                double tongTienPhaiTra = giaMoiMoiMon * soLuong;

                if (giaMoiMoiMon <= phien.getGiaHienTai()) {
                    thongBaoLoi("Giá ra phải lớn hơn giá hiện tại!");
                    return;
                }

                // Cập nhật lấy số dư từ Singleton
                double soDuHienTai = Session.getInstance().getSoDu();

                if (tongTienPhaiTra > soDuHienTai) {
                    thongBaoLoi("Số dư không đủ! Tổng tiền cho " + soLuong + " món là: " + String.format("%,.0f", tongTienPhaiTra) + " VNĐ");
                    return;
                }

                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setHeaderText("Xác nhận đấu giá");
                confirm.setContentText("Tổng tiền sẽ trừ: " + String.format("%,.0f", tongTienPhaiTra) + " VNĐ.\nBạn đồng ý chứ?");

                if (confirm.showAndWait().get() == ButtonType.OK) {
                    // Cập nhật lưu số dư vào Singleton
                    Session.getInstance().setSoDu(soDuHienTai - tongTienPhaiTra);
                    phien.setGiaHienTai(giaMoiMoiMon);

                    // Thêm lịch sử giao dịch qua Singleton
                    Session.getInstance().themLichSu("[-] Đấu giá: " + phien.getTenSanPham() + " (x" + soLuong + ") - Trừ: " + String.format("%,.0f VNĐ", tongTienPhaiTra));

                    capNhatHienThiUser();
                    tableAuctions.refresh();
                }
            } catch (Exception e) { thongBaoLoi("Vui lòng nhập số tiền hợp lệ!"); }
        }
    }

    public void capNhatHienThiUser() {
        if (lblUsername != null) {
            // Cập nhật lấy tên và số dư từ Singleton
            String ten = Session.getInstance().getTenNguoiDung();
            double tien = Session.getInstance().getSoDu();
            lblUsername.setText("Xin chào, " + ten + " | Số dư: " + String.format("%,.0f VNĐ", tien));
        }
    }

    private void thongBaoLoi(String msg) { new Alert(Alert.AlertType.WARNING, msg).showAndWait(); }

    // Các hàm điều hướng màn hình
    public void quanLyTaiKhoan(ActionEvent e) throws IOException { chuyenTrang(e, "/com/example/hocjavafx/fxml/thongtincanhan.fxml"); }
    public void dangXuat(ActionEvent e) throws IOException { chuyenTrang(e, "/com/example/hocjavafx/fxml/login.fxml"); }
    public void quanLySanPham(ActionEvent e) throws IOException { chuyenTrang(e, "/com/example/hocjavafx/fxml/quanlysanpham.fxml"); }
    public void xemLichSu(ActionEvent e) throws IOException { chuyenTrang(e, "/com/example/hocjavafx/fxml/lichsu.fxml"); }
    public void vaoPhongLive(ActionEvent e) throws IOException { chuyenTrang(e, "/com/example/hocjavafx/fxml/phonglive.fxml"); }

    private void chuyenTrang(ActionEvent event, String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = loader.load();
        Scene scene = ((Node) event.getSource()).getScene();
        if (root instanceof Region) ((Region) root).setPrefSize(scene.getWidth(), scene.getHeight());
        scene.setRoot(root);
    }
}