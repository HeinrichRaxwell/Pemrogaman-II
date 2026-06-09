package com.unpam.controller;

import com.unpam.model.Enkripsi;
import com.unpam.model.Mahasiswa;
import com.unpam.view.MainForm;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "MahasiswaController", urlPatterns = {"/MahasiswaController"})
public class MahasiswaController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String aksi = "";
        try { aksi = request.getParameter("aksi").toString(); } catch (Exception ex) {}

        Mahasiswa mahasiswa = new Mahasiswa();
        String konten = "", pesan = "";

        if (aksi.equals("simpan")) {
            try {
                Enkripsi enkripsi = new Enkripsi();
                mahasiswa.setNim(request.getParameter("nim"));
                mahasiswa.setNama(request.getParameter("nama"));
                mahasiswa.setSemester(Integer.parseInt(request.getParameter("semester")));
                mahasiswa.setKelas(request.getParameter("kelas"));
                mahasiswa.setPassword(enkripsi.hashMD5("haidar")); // default password
                pesan = mahasiswa.simpan()
                    ? "<font color=green>Data berhasil disimpan</font>"
                    : "<font color=red>" + mahasiswa.getPesan() + "</font>";
            } catch (Exception ex) {
                pesan = "<font color=red>Error: " + ex.getMessage() + "</font>";
            }
        } else if (aksi.equals("hapus")) {
            mahasiswa.setNim(request.getParameter("nim"));
            pesan = mahasiswa.hapus()
                ? "<font color=green>Data berhasil dihapus</font>"
                : "<font color=red>" + mahasiswa.getPesan() + "</font>";
        } else if (aksi.equals("cari")) {
            mahasiswa.setNim(request.getParameter("nim"));
            mahasiswa.cari();
        }
        
        // Selalu load list tabel agar otomatis muncul
        mahasiswa.lihat();

        String tabel = "";
        Object[][] list = mahasiswa.getList();
        if (list != null) {
            tabel = "<table border=1 cellpadding=4>"
                  + "<tr bgcolor=#cccccc><th>NIM</th><th>Nama</th><th>Semester</th><th>Kelas</th></tr>";
            for (Object[] row : list) {
                tabel += "<tr><td><a href='MahasiswaController?aksi=cari&nim=" + row[0] + "'>" + row[0] + "</a></td>"
                       + "<td>" + row[1] + "</td><td>" + row[2] + "</td><td>" + row[3] + "</td></tr>";
            }
            tabel += "</table>";
        }

        String nim   = mahasiswa.getNim()   != null ? mahasiswa.getNim()   : "";
        String nama  = mahasiswa.getNama()  != null ? mahasiswa.getNama()  : "";
        String kelas = mahasiswa.getKelas() != null ? mahasiswa.getKelas() : "";
        int    sem   = mahasiswa.getSemester();

        konten = "<h2>Master Data Mahasiswa</h2>" + pesan
               + "<form method=\"POST\" action=\"MahasiswaController\">"
               + "<table>"
               + "<tr><td>NIM</td><td><input type=\"text\" name=\"nim\" value=\"" + nim + "\"/>"
               + " <input type=\"submit\" name=\"aksi\" value=\"cari\"/>"
               + " <input type=\"submit\" name=\"aksi\" value=\"lihat\"/></td></tr>"
               + "<tr><td>Nama</td><td><input type=\"text\" name=\"nama\" value=\"" + nama + "\"/></td></tr>"
               + "<tr><td>Semester</td><td><input type=\"text\" name=\"semester\" value=\"" + sem + "\" size=\"3\"/></td></tr>"
               + "<tr><td>Kelas</td><td><input type=\"text\" name=\"kelas\" value=\"" + kelas + "\" size=\"3\"/></td></tr>"
               + "<tr><td colspan=\"2\">"
               + "<input type=\"submit\" name=\"aksi\" value=\"simpan\"/> "
               + "<input type=\"submit\" name=\"aksi\" value=\"hapus\"/>"
               + "</td></tr></table></form><br>" + tabel;

        new MainForm().tampilkan(request, response, konten);
    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { processRequest(req, res); }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { processRequest(req, res); }
}
