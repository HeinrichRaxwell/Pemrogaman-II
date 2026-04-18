import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.Locale;

public class FormGaji extends JFrame {

    JTextField idTF, namaTF, gajiPokokTF, tunjanganTF, potonganTF, totalGajiTF;
    JComboBox<String> jabatanCB;
    DefaultTableModel tableModel;
    JTable table;
    JPopupMenu popupMenu;

    public FormGaji() {
        setTitle("Haidar Reyhan - 231011400547 | Form Gaji Karyawan");
        setSize(750, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        // ===== PANEL FORM =====
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 5, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Input Data Gaji"));
        formPanel.setPreferredSize(new Dimension(750, 250));

        formPanel.add(new JLabel("  ID Karyawan :"));
        idTF = new JTextField();
        formPanel.add(idTF);

        formPanel.add(new JLabel("  Nama Karyawan :"));
        namaTF = new JTextField();
        formPanel.add(namaTF);

        formPanel.add(new JLabel("  Jabatan :"));
        String[] jabatan = {"Staff", "Supervisor", "Manager", "Direktur", "Security", "OB"};
        jabatanCB = new JComboBox<>(jabatan);
        jabatanCB.addActionListener(e -> autoIsiGajiPokok());
        formPanel.add(jabatanCB);

        formPanel.add(new JLabel("  Gaji Pokok :"));
        gajiPokokTF = new JTextField();
        gajiPokokTF.setEditable(false);
        gajiPokokTF.setBackground(new Color(240, 240, 240));
        formPanel.add(gajiPokokTF);

        formPanel.add(new JLabel("  Tunjangan :"));
        tunjanganTF = new JTextField("0");
        formPanel.add(tunjanganTF);

        formPanel.add(new JLabel("  Potongan :"));
        potonganTF = new JTextField("0");
        formPanel.add(potonganTF);

        formPanel.add(new JLabel("  Total Gaji :"));
        totalGajiTF = new JTextField();
        totalGajiTF.setEditable(false);
        totalGajiTF.setBackground(new Color(255, 255, 200));
        formPanel.add(totalGajiTF);

        // Panel Tombol
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JButton btnHitung  = new JButton("Hitung");
        JButton btnSimpan  = new JButton("Simpan");
        JButton btnHapus   = new JButton("Hapus");
        JButton btnBersih  = new JButton("Bersihkan");

        btnPanel.add(btnHitung);
        btnPanel.add(btnSimpan);
        btnPanel.add(btnHapus);
        btnPanel.add(btnBersih);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(btnPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // ===== TABEL =====
        String[] kolom = {"ID", "Nama", "Jabatan", "Gaji Pokok", "Tunjangan", "Potongan", "Total Gaji"};
        tableModel = new DefaultTableModel(kolom, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setBackground(new Color(70, 130, 180));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setRowHeight(22);

        // ===== POPUP MENU (klik kanan tabel) =====
        popupMenu = new JPopupMenu();
        JMenuItem tambahMenuItem = new JMenuItem("Tambah (klik kanan)");
        JMenuItem hapusMenuItem  = new JMenuItem("Hapus Baris");
        popupMenu.add(tambahMenuItem);
        popupMenu.add(hapusMenuItem);

        table.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0) table.setRowSelectionInterval(row, row);
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
            // Klik baris → isi ke form
            public void mouseClicked(MouseEvent e) {
                int baris = table.getSelectedRow();
                if (baris >= 0) {
                    idTF.setText(tableModel.getValueAt(baris, 0).toString());
                    namaTF.setText(tableModel.getValueAt(baris, 1).toString());
                    jabatanCB.setSelectedItem(tableModel.getValueAt(baris, 2).toString());
                    gajiPokokTF.setText(tableModel.getValueAt(baris, 3).toString());
                    tunjanganTF.setText(tableModel.getValueAt(baris, 4).toString());
                    potonganTF.setText(tableModel.getValueAt(baris, 5).toString());
                    totalGajiTF.setText(tableModel.getValueAt(baris, 6).toString());
                }
            }
        });

        // KeyPress DEL di tabel → hapus baris
        table.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) hapusBaris();
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== AKSI TOMBOL =====
        btnHitung.addActionListener(e -> hitungGaji());

        btnSimpan.addActionListener(e -> {
            if (idTF.getText().trim().isEmpty() || namaTF.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID dan Nama harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (totalGajiTF.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Klik tombol Hitung dulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            tableModel.addRow(new Object[]{
                idTF.getText(), namaTF.getText(),
                jabatanCB.getSelectedItem(),
                gajiPokokTF.getText(), tunjanganTF.getText(),
                potonganTF.getText(), totalGajiTF.getText()
            });
            bersihkanForm();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
        });

        btnHapus.addActionListener(e -> hapusBaris());
        btnBersih.addActionListener(e -> bersihkanForm());

        tambahMenuItem.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Isi form di atas lalu klik Simpan untuk menambah data."));
        hapusMenuItem.addActionListener(e -> hapusBaris());

        // Inisialisasi gaji pokok awal
        autoIsiGajiPokok();
        setVisible(true);
    }

    // ===== AUTO ISI GAJI POKOK BERDASARKAN JABATAN =====
    void autoIsiGajiPokok() {
        String jabatan = (String) jabatanCB.getSelectedItem();
        long gaji = switch (jabatan) {
            case "Direktur"   -> 25000000;
            case "Manager"    -> 15000000;
            case "Supervisor" -> 8000000;
            case "Staff"      -> 5000000;
            case "Security"   -> 3500000;
            case "OB"         -> 3000000;
            default           -> 0;
        };
        gajiPokokTF.setText(String.valueOf(gaji));
        totalGajiTF.setText("");
    }

    // ===== HITUNG TOTAL GAJI =====
    void hitungGaji() {
        try {
            long pokok     = Long.parseLong(gajiPokokTF.getText().trim());
            long tunjangan = Long.parseLong(tunjanganTF.getText().trim());
            long potongan  = Long.parseLong(potonganTF.getText().trim());
            long total     = pokok + tunjangan - potongan;
            totalGajiTF.setText(String.valueOf(total));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Tunjangan dan Potongan harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===== HAPUS BARIS TERPILIH =====
    void hapusBaris() {
        int baris = table.getSelectedRow();
        if (baris < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data di tabel dulu!");
            return;
        }
        int konfirmasi = JOptionPane.showConfirmDialog(this,
            "Yakin mau hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            tableModel.removeRow(baris);
            bersihkanForm();
        }
    }

    // ===== BERSIHKAN FORM =====
    void bersihkanForm() {
        idTF.setText("");
        namaTF.setText("");
        jabatanCB.setSelectedIndex(0);
        tunjanganTF.setText("0");
        potonganTF.setText("0");
        totalGajiTF.setText("");
        autoIsiGajiPokok();
        idTF.requestFocus();
    }

    public static void main(String[] args) {
        new FormGaji();
    }
}
