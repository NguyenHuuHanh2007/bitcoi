package com.example.hocjavafx;

import com.example.hocjavafx.model.Session;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class PhongLiveController implements Initializable {

    @FXML private TextArea txtChatLive;
    @FXML private TextField txtNhapLieu; // Nối với ô nhập chữ ở giao diện

    private Timeline botLive;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtChatLive.appendText("Hệ thống: Đang kết nối đến Server WebSocket...\n");
        txtChatLive.appendText("Hệ thống: Kết nối thành công! Bạn đang ở Phòng Live.\n");
        txtChatLive.appendText("--------------------------------------------------\n\n");

        // Động cơ sinh tin nhắn ảo mỗi 2 giây
        botLive = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            String[] userAo = {"Hoang_De_99", "ThichMuaSam", "Hacker_Lo", "PhuGiaDubai", "Giau_Nhat_VN"};
            String[] hanhDong = {"đã thả tim ❤️", "vừa tham gia phòng!", "bảo: Món này mướt quá ae", "đang hóng...", "đã ra giá 1,200,000 VNĐ"};

            String user = userAo[(int) (Math.random() * userAo.length)];
            String hd = hanhDong[(int) (Math.random() * hanhDong.length)];

            txtChatLive.appendText("[" + thoiGianHienTai() + "] " + user + " " + hd + "\n");
        }));
        botLive.setCycleCount(Timeline.INDEFINITE);
        botLive.play();
    }

    // =====================================
    // CHỨC NĂNG TƯƠNG TÁC LIVE
    // =====================================

    // 1. Nút Gửi bình luận
    public void guiBinhLuan(ActionEvent event) {
        String tinNhan = txtNhapLieu.getText().trim();
        if (!tinNhan.isEmpty()) {
            // Lấy tên từ Singleton
            txtChatLive.appendText("[" + thoiGianHienTai() + "] " + Session.getInstance().getTenNguoiDung() + " (Bạn): " + tinNhan + "\n");
            txtNhapLieu.clear(); // Gửi xong thì xóa trắng ô nhập
        }
    }

    // 2. Nút Thả tim icon
    public void thaTim(ActionEvent event) {
        // Lấy tên từ Singleton
        txtChatLive.appendText("[" + thoiGianHienTai() + "] " + Session.getInstance().getTenNguoiDung() + " (Bạn) đã thả tim ❤️❤️❤️\n");
    }

    // 3. Nút Ra Giá Trực Tiếp
    public void raGiaLive(ActionEvent event) {
        String input = txtNhapLieu.getText().trim();

        if (input.isEmpty()) {
            thongBaoLoi("Vui lòng gõ số tiền bạn muốn ra giá vào ô chat trước khi bấm nút!");
            return;
        }

        try {
            double giaTra = Double.parseDouble(input);

            if (giaTra <= 0) {
                thongBaoLoi("Số tiền phải lớn hơn 0!");
                return;
            }

            // Lấy số dư hiện tại từ Singleton
            double soDuHienTai = Session.getInstance().getSoDu();

            if (giaTra > soDuHienTai) {
                thongBaoLoi("Tài khoản không đủ! Bạn chỉ còn " + String.format("%,.0f VNĐ", soDuHienTai) + ". Vui lòng nạp thêm.");
                return;
            }

            // Đủ tiền -> Xử lý giao dịch trừ tiền
            Session.getInstance().setSoDu(soDuHienTai - giaTra);

            // Lưu vào lịch sử qua hàm tiện ích
            Session.getInstance().themLichSu("[-] Đấu giá Live thành công - Trừ: " + String.format("%,.0f VNĐ", giaTra));

            // Xóa ô nhập liệu
            txtNhapLieu.clear();

            // Phát thông báo chấn động lên khung chat Live
            txtChatLive.appendText("\n🔥🔥🔥 [" + thoiGianHienTai() + "] ĐẠI GIA " + Session.getInstance().getTenNguoiDung().toUpperCase() + " ĐÃ RA GIÁ KỶ LỤC: " + String.format("%,.0f VNĐ", giaTra) + " !!! 🔥🔥🔥\n\n");

        } catch (NumberFormatException e) {
            thongBaoLoi("Lỗi: Vui lòng chỉ nhập SỐ TIỀN để ra giá (Không ghi chữ).");
        }
    }

    // =====================================
    // CÁC HÀM TIỆN ÍCH
    // =====================================

    // Lấy giờ hiện tại cho ngầu
    private String thoiGianHienTai() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    // Nút Rời phòng
    public void quayLai(ActionEvent event) throws IOException {
        botLive.stop();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hocjavafx/fxml/trangchu.fxml"));
        Parent root = loader.load();
        Scene scene = ((Node) event.getSource()).getScene();
        if (root instanceof Region) { ((Region) root).setPrefSize(scene.getWidth(), scene.getHeight()); }
        scene.setRoot(root);
    }

    // Hàm báo lỗi chung
    private void thongBaoLoi(String noiDung) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cảnh báo");
        alert.setHeaderText(null);
        alert.setContentText(noiDung);
        alert.showAndWait();
    }
}