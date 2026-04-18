import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class Main extends JFrame {

    JTextField nimTF, namaTF, semesterTF, kelasTF;
    DefaultTableModel tableModel;
    JTable table;

    // Simpan NIM yg sedang dipilih untuk keperluan update
    private String nimDipilih = null;

    public Main() {
        DatabaseHelper.initDB();

        setTitle("Data Mahasiswa - Pertemuan 5");
        setSize(650, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        // ===== Panel Form Input =====
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 5, 10),
            BorderFactory.createTitledBorder("Input Data Mahasiswa")));

        formPanel.add(new JLabel("NIM:"));
        nimTF = new JTextField();
        formPanel.add(nimTF);

        formPanel.add(new JLabel("Nama:"));
        namaTF = new JTextField();
        formPanel.add(namaTF);

        formPanel.add(new JLabel("Semester:"));
        semesterTF = new JTextField();
        formPanel.add(semesterTF);

        formPanel.add(new JLabel("Kelas:"));
        kelasTF = new JTextField();
        formPanel.add(kelasTF);

        // Tombol baris pertama
        JButton simpanBtn = new JButton("Simpan");
        JButton tampilBtn = new JButton("Tampil Data");
        formPanel.add(simpanBtn);
        formPanel.add(tampilBtn);

        // Tombol baris kedua
        JButton updateBtn = new JButton("Update");
        JButton hapusBtn  = new JButton("Hapus");
        formPanel.add(updateBtn);
        formPanel.add(hapusBtn);

        // ===== Tabel =====
        String[] kolom = {"NIM", "Nama", "Semester", "Kelas"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabel read-only, edit lewat form
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(22);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(0, 10, 10, 10),
            BorderFactory.createTitledBorder("Tabel Data Mahasiswa")));

        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // ===== Klik baris tabel → isi form =====
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    nimDipilih = tableModel.getValueAt(row, 0).toString();
                    nimTF.setText(nimDipilih);
                    namaTF.setText(tableModel.getValueAt(row, 1).toString());
                    semesterTF.setText(tableModel.getValueAt(row, 2).toString());
                    kelasTF.setText(tableModel.getValueAt(row, 3).toString());
                    nimTF.setEditable(false); // NIM tidak boleh diubah saat edit
                }
            }
        });

        // ===== Aksi Simpan =====
        simpanBtn.addActionListener(e -> {
            String nim    = nimTF.getText().trim();
            String nama   = namaTF.getText().trim();
            String semStr = semesterTF.getText().trim();
            String kelas  = kelasTF.getText().trim();

            if (nim.isEmpty() || nama.isEmpty() || semStr.isEmpty() || kelas.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Semua field harus diisi!", "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int semester = Integer.parseInt(semStr);
                boolean berhasil = DatabaseHelper.simpanData(nim, nama, semester, kelas);
                if (berhasil) {
                    JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
                    clearFields();
                    refreshTabel();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Gagal menyimpan! NIM mungkin sudah ada.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Semester harus berupa angka!", "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            }
        });

        // ===== Aksi Tampil Data =====
        tampilBtn.addActionListener(e -> refreshTabel());

        // ===== Aksi Update =====
        updateBtn.addActionListener(e -> {
            if (nimDipilih == null || nimDipilih.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Pilih data dari tabel dulu!", "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            String nama   = namaTF.getText().trim();
            String semStr = semesterTF.getText().trim();
            String kelas  = kelasTF.getText().trim();

            if (nama.isEmpty() || semStr.isEmpty() || kelas.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Semua field harus diisi!", "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int semester = Integer.parseInt(semStr);
                boolean berhasil = DatabaseHelper.updateData(nimDipilih, nama, semester, kelas);
                if (berhasil) {
                    JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
                    clearFields();
                    refreshTabel();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Gagal update! NIM tidak ditemukan.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Semester harus berupa angka!", "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            }
        });

        // ===== Aksi Hapus =====
        hapusBtn.addActionListener(e -> {
            if (nimDipilih == null || nimDipilih.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Pilih data dari tabel dulu!", "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Yakin hapus data NIM: " + nimDipilih + "?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                boolean berhasil = DatabaseHelper.hapusData(nimDipilih);
                if (berhasil) {
                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                    clearFields();
                    refreshTabel();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Gagal hapus!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    // Refresh isi tabel dari database
    void refreshTabel() {
        tableModel.setRowCount(0);
        try (Connection conn = DatabaseHelper.connect();
             ResultSet rs = DatabaseHelper.getAllData(conn)) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getInt("semester"),
                    rs.getString("kelas")
                });
            }
        } catch (SQLException ex) {
            System.out.println("Gagal load data: " + ex.getMessage());
        }
    }

    // Kosongkan semua field input
    void clearFields() {
        nimTF.setText("");
        namaTF.setText("");
        semesterTF.setText("");
        kelasTF.setText("");
        nimTF.setEditable(true); // kembalikan NIM bisa diisi lagi
        nimDipilih = null;
        nimTF.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
