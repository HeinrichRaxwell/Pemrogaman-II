package com.unpam.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.unpam.view.PesanDialog;

public class Nilai {
    private String nim, nama, kelas, kodeMataKuliah, namaMataKuliah;
    private int semester, jumlahHadir, jumlahSks;
    private double nilaiTugas, nilaiUTS, nilaiUAS, nilaiAkhir;
    private String grade, pesan;
    private Object[][] list;
    private final Koneksi koneksi = new Koneksi();
    private final PesanDialog pesanDialog = new PesanDialog();

    public String getNim()              { return nim; }
    public void setNim(String nim)      { this.nim = nim; }
    public String getNama()             { return nama; }
    public String getKelas()            { return kelas; }
    public int getSemester()            { return semester; }
    public String getKodeMataKuliah()   { return kodeMataKuliah; }
    public void setKodeMataKuliah(String k) { this.kodeMataKuliah = k; }
    public String getNamaMataKuliah()   { return namaMataKuliah; }
    public int getJumlahSks()           { return jumlahSks; }
    public int getJumlahHadir()         { return jumlahHadir; }
    public void setJumlahHadir(int j)   { this.jumlahHadir = j; }
    public double getNilaiTugas()       { return nilaiTugas; }
    public void setNilaiTugas(double n) { this.nilaiTugas = n; }
    public double getNilaiUTS()         { return nilaiUTS; }
    public void setNilaiUTS(double n)   { this.nilaiUTS = n; }
    public double getNilaiUAS()         { return nilaiUAS; }
    public void setNilaiUAS(double n)   { this.nilaiUAS = n; }
    public double getNilaiAkhir()       { return nilaiAkhir; }
    public String getGrade()            { return grade; }
    public String getPesan()            { return pesan; }
    public Object[][] getList()         { return list; }

    public void cariMahasiswa() {
        Connection connection;
        if ((connection = koneksi.getConnection()) != null) {
            try {
                PreparedStatement ps = connection.prepareStatement(
                    "SELECT nim, nama, semester, kelas FROM tbmahasiswa WHERE nim=?");
                ps.setString(1, nim);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    nim      = rs.getString("nim");
                    nama     = rs.getString("nama");
                    semester = rs.getInt("semester");
                    kelas    = rs.getString("kelas");
                }
                rs.close(); ps.close(); connection.close();
            } catch (SQLException ex) { pesan = "Error: " + ex; }
        }
    }

    public void cariMataKuliah() {
        Connection connection;
        if ((connection = koneksi.getConnection()) != null) {
            try {
                Statement sta = connection.createStatement();
                ResultSet rs  = sta.executeQuery(
                    "SELECT * FROM tbmatakuliah WHERE kodeMataKuliah='" + kodeMataKuliah + "'");
                if (rs.next()) {
                    kodeMataKuliah = rs.getString("kodeMataKuliah");
                    namaMataKuliah = rs.getString("namaMataKuliah");
                    jumlahSks      = rs.getInt("jumlahSks");
                }
                rs.close(); sta.close(); connection.close();
            } catch (SQLException ex) { pesan = "Error: " + ex; }
        }
    }

    public boolean simpan() {
        // hitung nilai akhir
        double kehadiran = (jumlahHadir / 16.0) * 100 * 0.1;
        nilaiAkhir = kehadiran + (nilaiTugas * 0.2) + (nilaiUTS * 0.3) + (nilaiUAS * 0.4);
        if      (nilaiAkhir >= 80) grade = "A";
        else if (nilaiAkhir >= 70) grade = "B";
        else if (nilaiAkhir >= 60) grade = "C";
        else if (nilaiAkhir >= 50) grade = "D";
        else                       grade = "E";

        boolean adaKesalahan = false;
        Connection connection;
        if ((connection = koneksi.getConnection()) != null) {
            try {
                String sql = "INSERT INTO tbnilai(nim, kodeMataKuliah, jumlahHadir, nilaiTugas, nilaiUTS, nilaiUAS, nilaiAkhir, grade) "
                           + "VALUES (?,?,?,?,?,?,?,?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, nim);
                ps.setString(2, kodeMataKuliah);
                ps.setInt(3, jumlahHadir);
                ps.setDouble(4, nilaiTugas);
                ps.setDouble(5, nilaiUTS);
                ps.setDouble(6, nilaiUAS);
                ps.setDouble(7, nilaiAkhir);
                ps.setString(8, grade);
                int jml = ps.executeUpdate();
                if (jml < 1) { adaKesalahan = true; pesan = "Gagal menyimpan nilai"; }
                ps.close(); connection.close();
            } catch (SQLException ex) { adaKesalahan = true; pesan = "Error: " + ex; }
        } else { adaKesalahan = true; pesan = "Koneksi gagal: " + koneksi.getPesanKesalahan(); }
        return !adaKesalahan;
    }

    public boolean hapus() {
        boolean adaKesalahan = false;
        Connection connection;
        if ((connection = koneksi.getConnection()) != null) {
            try {
                PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM tbnilai WHERE nim=? AND kodeMataKuliah=?");
                ps.setString(1, nim);
                ps.setString(2, kodeMataKuliah);
                int jml = ps.executeUpdate();
                if (jml < 1) { adaKesalahan = true; pesan = "Gagal menghapus nilai"; }
                ps.close(); connection.close();
            } catch (SQLException ex) { adaKesalahan = true; pesan = "Error: " + ex; }
        } else { adaKesalahan = true; pesan = "Koneksi gagal: " + koneksi.getPesanKesalahan(); }
        return !adaKesalahan;
    }

    public void lihat() {
        Connection connection;
        if ((connection = koneksi.getConnection()) != null) {
            try {
                String sql = "SELECT n.nim, m.nama, m.kelas, k.namaMataKuliah, "
                           + "n.jumlahHadir, n.nilaiTugas, n.nilaiUTS, n.nilaiUAS, n.nilaiAkhir, n.grade "
                           + "FROM tbnilai n "
                           + "JOIN tbmahasiswa m ON n.nim = m.nim "
                           + "JOIN tbmatakuliah k ON n.kodeMataKuliah = k.kodeMataKuliah "
                           + "ORDER BY n.nim";
                Statement sta = connection.createStatement();
                ResultSet rs  = sta.executeQuery(sql);
                java.util.ArrayList<Object[]> rows = new java.util.ArrayList<>();
                while (rs.next()) {
                    rows.add(new Object[]{
                        rs.getString("nim"), rs.getString("nama"), rs.getString("kelas"),
                        rs.getString("namaMataKuliah"), rs.getInt("jumlahHadir"),
                        rs.getDouble("nilaiTugas"), rs.getDouble("nilaiUTS"),
                        rs.getDouble("nilaiUAS"), rs.getDouble("nilaiAkhir"), rs.getString("grade")
                    });
                }
                list = rows.toArray(new Object[0][]);
                rs.close(); sta.close(); connection.close();
            } catch (SQLException ex) { pesan = "Error: " + ex; }
        }
    }
}
