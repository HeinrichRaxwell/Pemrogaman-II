package com.unpam.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.unpam.view.PesanDialog;

public class MataKuliah {
    private String kodeMataKuliah, namaMataKuliah;
    private int jumlahSks;
    private String pesan;
    private Object[][] list;
    private final Koneksi koneksi = new Koneksi();
    private final PesanDialog pesanDialog = new PesanDialog();

    public String getKodeMataKuliah()  { return kodeMataKuliah; }
    public void setKodeMataKuliah(String k) { this.kodeMataKuliah = k; }
    public String getNamaMataKuliah()  { return namaMataKuliah; }
    public void setNamaMataKuliah(String n) { this.namaMataKuliah = n; }
    public int getJumlahSks()          { return jumlahSks; }
    public void setJumlahSks(int s)    { this.jumlahSks = s; }
    public String getPesan()           { return pesan; }
    public Object[][] getList()        { return list; }
    public void setList(Object[][] l)  { this.list = l; }

    public boolean simpan() {
        boolean adaKesalahan = false;
        Connection connection;
        if ((connection = koneksi.getConnection()) != null) {
            try {
                String sql = "INSERT INTO tbmatakuliah VALUES (?,?,?)";
                Statement sta = connection.createStatement();
                int jml = sta.executeUpdate("INSERT INTO tbmatakuliah VALUES ('"
                    + kodeMataKuliah + "','" + namaMataKuliah + "','" + jumlahSks + "')");
                if (jml < 1) { adaKesalahan = true; pesan = "Gagal menyimpan data mata kuliah"; }
                sta.close(); connection.close();
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
                Statement sta = connection.createStatement();
                int jml = sta.executeUpdate("DELETE FROM tbmatakuliah WHERE kodeMataKuliah='" + kodeMataKuliah + "'");
                if (jml < 1) { adaKesalahan = true; pesan = "Gagal menghapus data mata kuliah"; }
                sta.close(); connection.close();
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
                Statement sta = connection.createStatement();
                ResultSet rs = sta.executeQuery("SELECT * FROM tbmatakuliah WHERE kodeMataKuliah='" + kodeMataKuliah + "'");
                if (rs.next()) {
                    kodeMataKuliah = rs.getString("kodeMataKuliah");
                    namaMataKuliah = rs.getString("namaMataKuliah");
                    jumlahSks      = rs.getInt("jumlahSks");
                }
                rs.close(); sta.close(); connection.close();
            } catch (SQLException ex) {
                pesan = "Error: " + ex;
            }
        }
    }

    public void lihat() {
        Connection connection;
        if ((connection = koneksi.getConnection()) != null) {
            try {
                Statement sta = connection.createStatement();
                ResultSet rs  = sta.executeQuery("SELECT * FROM tbmatakuliah ORDER BY kodeMataKuliah");
                java.util.ArrayList<Object[]> rows = new java.util.ArrayList<>();
                while (rs.next()) {
                    rows.add(new Object[]{
                        rs.getString("kodeMataKuliah"),
                        rs.getString("namaMataKuliah"),
                        rs.getInt("jumlahSks")
                    });
                }
                list = rows.toArray(new Object[0][]);
                rs.close(); sta.close(); connection.close();
            } catch (SQLException ex) {
                pesan = "Error: " + ex;
            }
        }
    }
}
