package com.example.hocjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class QuanLySanPhamController implements Initializable {

    @FXML private TextField txtTenSP, txtSoLuong, txtTinhTrang;
    @FXML private Label lblTenAnh;
    @FXML private TableView<PhienDauGia> tableSanPham;
    @FXML private TableColumn<PhienDauGia, String> colTenSP, colTrangThai;
    @FXML private TableColumn<PhienDauGia, Double> colGia;
    @FXML private TableColumn<PhienDauGia, Integer> colSoLuong;

    private String duongDanAnhDuocChon = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colTenSP.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));
        colGia.setCellValueFactory(new PropertyValueFactory<>("giaKhoiDiem"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));

        colTrangThai.setCellFactory(column -> new TableCell<PhienDauGia, String>() {
            @Override protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); setStyle(""); }
                else {
                    setText(item);
                    if (item.equals("Đã bán")) setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
                    else if (item.equals("Đang đấu giá")) setStyle("-fx-text-fill: #f1c40f; -fx-font-weight: bold;");
                    else setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold;");
                }
            }
        });

        tableSanPham.setItems(TrangChuController.danhSachDauGia);
    }

    public void chonAnh(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh sản phẩm");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            duongDanAnhDuocChon = file.toURI().toString();
            lblTenAnh.setText(file.getName());
        }
    }

    public void themSanPham(ActionEvent event) {
        try {
            String ten = txtTenSP.getText();
            int sl = Integer.parseInt(txtSoLuong.getText());
            String tinhTrang = txtTinhTrang.getText();

            if (tinhTrang == null || tinhTrang.isEmpty()) tinhTrang = "Chưa cập nhật";
            if (duongDanAnhDuocChon == null || duongDanAnhDuocChon.isEmpty()) duongDanAnhDuocChon = "";

            String tt = (sl > 0) ? "Đang tồn kho" : "Hết hàng";

            TrangChuController.danhSachDauGia.add(new PhienDauGia(
                    TrangChuController.danhSachDauGia.size() + 1,
                    ten, 0.0, 0.0, "Chưa lên sàn", tt, sl, tinhTrang, duongDanAnhDuocChon
            ));

            txtTenSP.clear(); txtSoLuong.clear(); txtTinhTrang.clear();
            lblTenAnh.setText("Chưa chọn ảnh...");
            duongDanAnhDuocChon = "";

        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, "Lỗi nhập liệu! Vui lòng kiểm tra lại Số lượng.").showAndWait();
        }
    }

    public void dayLenSan(ActionEvent event) {
        PhienDauGia phien = tableSanPham.getSelectionModel().getSelectedItem();

        if (phien == null) {
            new Alert(Alert.AlertType.WARNING, "Vui lòng chọn một sản phẩm trong bảng!").showAndWait();
            return;
        }
        if (phien.getSoLuong() <= 0) {
            new Alert(Alert.AlertType.WARNING, "Sản phẩm này đã hết hàng!").showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Đẩy sản phẩm lên sàn");
        dialog.setHeaderText("Cài đặt thời gian kết thúc cho: " + phien.getTenSanPham());

        ButtonType btnXacNhan = new ButtonType("Lên Sàn", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnXacNhan, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(20, 100, 10, 10));

        TextField txtGiaMoi = new TextField();
        txtGiaMoi.setPromptText("Ví dụ: 15000000");

        // --- GIAO DIỆN CHỌN NGÀY GIỜ MỚI ---
        DatePicker datePicker = new DatePicker(LocalDate.now()); // Mặc định hôm nay
        TextField txtGioPhut = new TextField(LocalTime.now().plusMinutes(5).format(DateTimeFormatter.ofPattern("HH:mm")));
        txtGioPhut.setPromptText("Giờ:Phút (VD: 14:30)");

        TextField txtSoLuongLenSan = new TextField();
        txtSoLuongLenSan.setText(String.valueOf(phien.getSoLuong()));

        grid.add(new Label("Giá khởi điểm (VNĐ):"), 0, 0);
        grid.add(txtGiaMoi, 1, 0);
        grid.add(new Label("Ngày kết thúc:"), 0, 1);
        grid.add(datePicker, 1, 1);
        grid.add(new Label("Giờ kết thúc (HH:mm):"), 0, 2);
        grid.add(txtGioPhut, 1, 2);
        grid.add(new Label("Số lượng đưa lên sàn:"), 0, 3);
        grid.add(txtSoLuongLenSan, 1, 3);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == btnXacNhan) {
            try {
                double giaKhoiDiem = Double.parseDouble(txtGiaMoi.getText());
                int slLenSan = Integer.parseInt(txtSoLuongLenSan.getText());

                // Lấy Ngày và Giờ để ghép thành LocalDateTime
                LocalDate date = datePicker.getValue();
                String timeStr = txtGioPhut.getText().trim();

                if (!timeStr.matches("\\d{2}:\\d{2}")) {
                    new Alert(Alert.AlertType.WARNING, "Vui lòng nhập giờ theo định dạng HH:mm (VD: 09:30, 15:45)").showAndWait();
                    return;
                }

                LocalTime time = LocalTime.parse(timeStr);
                LocalDateTime thoiGianKetThuc = LocalDateTime.of(date, time);

                if (thoiGianKetThuc.isBefore(LocalDateTime.now())) {
                    new Alert(Alert.AlertType.WARNING, "Thời gian kết thúc không được ở trong quá khứ!").showAndWait();
                    return;
                }

                if (slLenSan > phien.getSoLuong()) return;

                if (slLenSan == phien.getSoLuong()) {
                    phien.setGiaKhoiDiem(giaKhoiDiem);
                    phien.setGiaHienTai(giaKhoiDiem);
                    phien.setThoiGianKetThuc(thoiGianKetThuc);
                    phien.setTrangThai("Đang đấu giá");
                } else {
                    phien.setSoLuong(phien.getSoLuong() - slLenSan);
                    PhienDauGia phienMoi = new PhienDauGia(
                            TrangChuController.danhSachDauGia.size() + 1,
                            phien.getTenSanPham(), giaKhoiDiem, giaKhoiDiem, "Đang tính...",
                            "Đang đấu giá", slLenSan, phien.getTinhTrang(), phien.getDuongDanAnh()
                    );
                    phienMoi.setThoiGianKetThuc(thoiGianKetThuc);
                    TrangChuController.danhSachDauGia.add(phienMoi);
                }
                tableSanPham.refresh();

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Dữ liệu không hợp lệ!").showAndWait();
            }
        }
    }

    public void quayLai(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/hocjavafx/fxml/trangchu.fxml"));
        Scene scene = ((Node) e.getSource()).getScene();
        if (root instanceof Region) ((Region) root).setPrefSize(scene.getWidth(), scene.getHeight());
        scene.setRoot(root);
    }
}