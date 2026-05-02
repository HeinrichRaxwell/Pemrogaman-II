package Pertemuan7;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class FormLaporanP7 extends JFrame {

    public static void main(String[] args) {
        new FormLaporanP7().setVisible(true);
    }

    // Setting DB - SQLite
    private static final String DB_URL = "jdbc:sqlite:datamhs.db";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // UI
    JButton btnCetak;
    JLabel  lblStatus;

    public FormLaporanP7() {
        setTitle("Laporan Mahasiswa - Pertemuan 7");
        setSize(350, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblJudul = new JLabel("Laporan Data Mahasiswa");
        lblJudul.setBounds(70, 20, 220, 25);
        lblJudul.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        add(lblJudul);

        btnCetak = new JButton("Cetak Laporan");
        btnCetak.setBounds(100, 60, 140, 35);
        add(btnCetak);

        lblStatus = new JLabel("");
        lblStatus.setBounds(20, 105, 310, 25);
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblStatus);

        btnCetak.addActionListener(e -> cetakLaporan());
    }

    // Cetak
    void cetakLaporan() {
        lblStatus.setText("Memproses laporan...");
        btnCetak.setEnabled(false);

        // Path ke file .jrxml
        // Struktur: src/laporan/LaporanMahasiswa.jrxml
        File dir1   = new File(".");
        String dirr = dir1.getAbsolutePath();
        String reportPath = dirr + File.separator + "src"
                + File.separator + "laporan"
                + File.separator + "LaporanMahasiswa.jrxml";

        try (Connection koneksi = getConnection()) {

            // Compile .jrxml → .jasper
            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);

            // Parameter tambahan (opsional)
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("judul", "Daftar Data Mahasiswa");

            // Fill data ke report
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport, parameters, koneksi
            );

            // Tampilkan di JasperViewer
            JasperViewer.viewReport(jasperPrint, false);

            lblStatus.setText("Laporan berhasil ditampilkan.");

        } catch (JRException e) {
            JOptionPane.showMessageDialog(this,
                "Gagal memproses laporan!\n" + e.getMessage(),
                "Error JasperReports", JOptionPane.ERROR_MESSAGE);
            lblStatus.setText("Gagal memproses laporan.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Gagal koneksi database!\n" + e.getMessage(),
                "Error Database", JOptionPane.ERROR_MESSAGE);
            lblStatus.setText("Gagal koneksi database.");
        } finally {
            btnCetak.setEnabled(true);
        }
    }
}
