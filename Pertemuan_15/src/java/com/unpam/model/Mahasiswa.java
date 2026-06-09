package com.unpam.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.unpam.view.PesanDialog;

public class Mahasiswa {
    private String nim, nama, kelas, password;
    private int semester;
    private String pesan;
    private Object[][] list;
    private final Koneksi koneksi = new Koneksi();
    private final PesanDialog pesanDialog = new PesanDialog();

    public String getNim()      { return nim; }
    public void setNim(String nim) { this.nim = nim; }
    public String getNama()     { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getKelas()    { return kelas; }
    public void setKelas(String kelas) { this.kelas = kelas; }
    public int getSemester()    { return semester; }
    public void setSemester(int semester) { this.semester = semester; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPesan()    { return pesan; }
    public Object[][] getList() { return list; }
    public void setList(Object[][] list) { this.list = list; }

    public boolean simpan() {
        boolean adaKesalahan = false;
        Connection connection;
        if ((connection = koneksi.getConnection()) != null) {
            try {
                String sql = "INSERT INTO tbmahasiswa(nim, nama, semester, kelas, password) VALUES (?,?,?,?,?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, nim);
                ps.setString(2, nama);
                ps.setInt(3, semester);
                ps.setString(4, kelas);
                ps.setString(5, password);
                int jml = ps.executeUpdate();
                if (jml < 1) { adaKesalahan = true; pesan = "Gagal menyimpan data mahasiswa"; }
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Error: " + ex;
            }
        } else {
            adaKesalahan = true;
            pesan = "Koneksi gagal: " + koneksi.getPesanKesalahan();
        }
        return !adaKesalahan;
    }

    public boolean hapus() {
        boolean adaKesalahan = false;
        Connection connection;
        if ((connection = koneksi.getConnection()) != null) {
            try {
                String sql = "DELETE FROM tbmahasiswa WHERE nim=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, nim);
                int jml = ps.executeUpdate();
                if (jml < 1) { adaKesalahan = true; pesan = "Gagal menghapus data mahasiswa"; }
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Error: " + ex;
            }
        } else {
            adaKesalahan = true;
            pesan = "Koneksi gagal: " + koneksi.getPesanKesalahan();
        }
        return !adaKesalahan;
    }

    public void cari() {
        Connection connection;
        if ((connection = koneksi.getConnection()) != null) {
            try {
                String sql = "SELECT nim, nama, semester, kelas FROM tbmahasiswa WHERE nim=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, nim);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    nim      = rs.getString("nim");
                    nama     = rs.getString("nama");
                    semester = rs.getInt("semester");
                    kelas    = rs.getString("kelas");
                }
                rs.close(); ps.close(); connection.close();
            } catch (SQLException ex) {
                pesan = "Error: " + ex;
            }
        }
    }

    public void lihat() {
        Connection connection;
        if ((connection = koneksi.getConnection()) != null) {
            try {
                String sql = "SELECT nim, nama, semester, kelas FROM tbmahasiswa ORDER BY nim";
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(sql);
                java.util.ArrayList<Object[]> rows = new java.util.ArrayList<>();
                while (rs.next()) {
                    rows.add(new Object[]{
                        rs.getString("nim"),
                        rs.getString("nama"),
                        rs.getInt("semester"),
                        rs.getString("kelas")
                    });
                }
                list = rows.toArray(new Object[0][]);
                rs.close(); st.close(); connection.close();
            } catch (SQLException ex) {
                pesan = "Error: " + ex;
            }
        }
    }

    public boolean login() {
        boolean berhasil = false;
        Connection connection;
        if ((connection = koneksi.getConnection()) != null) {
            try {
                String sql = "SELECT nim, nama FROM tbmahasiswa WHERE nim=? AND password=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, nim);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    berhasil = true;
                    nim  = rs.getString("nim");
                    nama = rs.getString("nama");
                }
                rs.close(); ps.close(); connection.close();
            } catch (SQLException ex) {
                pesan = "Error: " + ex;
            }
        }
        return berhasil;
    }
}
