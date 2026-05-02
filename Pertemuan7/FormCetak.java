package Pertemuan7;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import javax.swing.*;
import java.io.File;

public class FormCetak extends JFrame {

    JButton btnCetak;
    JLabel lblStatus;

    public FormCetak() {
        setTitle("Laporan Sembako - Pertemuan 7");
        setSize(350, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblJudul = new JLabel("Laporan Data Sembako");
        lblJudul.setBounds(80, 20, 200, 25);
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

    void cetakLaporan() {
        try {
            // Compile .jrxml -> JasperReport langsung (tidak perlu file .jasper)
            File dir = new File(".");
            String reportPath = dir.getAbsolutePath() + File.separator
                    + "src" + File.separator
                    + "laporan" + File.separator
                    + "reportSembako.jrxml";

            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
            JasperPrint jp = JasperFillManager.fillReport(jasperReport, null, Koneksi.getConnection());
            JasperViewer.viewReport(jp, false);
            lblStatus.setText("Laporan berhasil ditampilkan.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
            lblStatus.setText("Gagal menampilkan laporan.");
        }
    }

    public static void main(String[] args) {
        new FormCetak().setVisible(true);
    }
}
