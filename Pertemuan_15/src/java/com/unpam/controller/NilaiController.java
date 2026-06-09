package com.unpam.controller;

import com.unpam.model.Nilai;
import com.unpam.view.MainForm;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "NilaiController", urlPatterns = {"/NilaiController"})
public class NilaiController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String aksi = "";
        try { aksi = request.getParameter("aksi").toString(); } catch (Exception ex) {}

        Nilai nilai = new Nilai();
        String konten = "", pesan = "";

        if (aksi.equals("cariMahasiswa")) {
            nilai.setNim(request.getParameter("nim"));
            nilai.cariMahasiswa();
        } else if (aksi.equals("cariMataKuliah")) {
            nilai.setNim(request.getParameter("nim"));
            nilai.cariMahasiswa();
            nilai.setKodeMataKuliah(request.getParameter("kodeMataKuliah"));
            nilai.cariMataKuliah();
        } else if (aksi.equals("simpan")) {
            try {
                nilai.setNim(request.getParameter("nim"));
                nilai.setKodeMataKuliah(request.getParameter("kodeMataKuliah"));
                nilai.setJumlahHadir(Integer.parseInt(request.getParameter("jumlahHadir")));
                nilai.setNilaiTugas(Double.parseDouble(request.getParameter("nilaiTugas")));
                nilai.setNilaiUTS(Double.parseDouble(request.getParameter("nilaiUTS")));
                nilai.setNilaiUAS(Double.parseDouble(request.getParameter("nilaiUAS")));
                pesan = nilai.simpan()
                    ? "<font color=green>Data berhasil disimpan</font>"
                    : "<font color=red>" + nilai.getPesan() + "</font>";
            } catch (Exception ex) {
                pesan = "<font color=red>Error: " + ex.getMessage() + "</font>";
            }
        } else if (aksi.equals("hapus")) {
            nilai.setNim(request.getParameter("nim"));
            nilai.setKodeMataKuliah(request.getParameter("kodeMataKuliah"));
            pesan = nilai.hapus()
                ? "<font color=green>Data berhasil dihapus</font>"
                : "<font color=red>" + nilai.getPesan() + "</font>";
        } else if (aksi.equals("laporan")) {
            nilai.lihat();
            String tabel = "";
            Object[][] list = nilai.getList();
            if (list != null && list.length > 0) {
                tabel = "<table border=1 cellpadding=4>"
                      + "<tr bgcolor=#cccccc><th>NIM</th><th>Nama</th><th>Kelas</th>"
                      + "<th>Mata Kuliah</th><th>Hadir</th><th>Tugas</th>"
                      + "<th>UTS</th><th>UAS</th><th>Nilai Akhir</th><th>Grade</th></tr>";
                for (Object[] row : list) {
                    tabel += "<tr><td>" + row[0] + "</td><td>" + row[1] + "</td><td>" + row[2] + "</td>"
                           + "<td>" + row[3] + "</td><td>" + row[4] + "</td><td>" + row[5] + "</td>"
                           + "<td>" + row[6] + "</td><td>" + row[7] + "</td><td>" + row[8] + "</td>"
                           + "<td>" + row[9] + "</td></tr>";
                }
                tabel += "</table>";
            } else {
                tabel = "<p>Belum ada data nilai.</p>";
            }
            konten = "<h2>Laporan Nilai Mahasiswa</h2>" + tabel;
            new MainForm().tampilkan(request, response, konten);
            return;
        }

        String nim             = request.getParameter("nim") != null ? request.getParameter("nim") : (nilai.getNim() != null ? nilai.getNim() : "");
        String nama            = nilai.getNama()            != null ? nilai.getNama()            : "";
        String kelas           = nilai.getKelas()           != null ? nilai.getKelas()           : "";
        String kodeMK          = request.getParameter("kodeMataKuliah") != null ? request.getParameter("kodeMataKuliah") : (nilai.getKodeMataKuliah() != null ? nilai.getKodeMataKuliah() : "");
        String namaMK          = nilai.getNamaMataKuliah()  != null ? nilai.getNamaMataKuliah()  : "";
        int    semester        = nilai.getSemester();
        int    sks             = nilai.getJumlahSks();
        
        String hadirStr        = request.getParameter("jumlahHadir") != null ? request.getParameter("jumlahHadir") : String.valueOf(nilai.getJumlahHadir());
        String tugasStr        = request.getParameter("nilaiTugas") != null ? request.getParameter("nilaiTugas") : String.valueOf(nilai.getNilaiTugas());
        String utsStr          = request.getParameter("nilaiUTS") != null ? request.getParameter("nilaiUTS") : String.valueOf(nilai.getNilaiUTS());
        String uasStr          = request.getParameter("nilaiUAS") != null ? request.getParameter("nilaiUAS") : String.valueOf(nilai.getNilaiUAS());

        konten = "<h2>Input Nilai Mahasiswa</h2>" + pesan
               + "<form method=\"POST\" action=\"NilaiController\">"
               + "<table>"
               + "<tr><td>NIM</td><td><input type=\"text\" name=\"nim\" value=\"" + nim + "\"/>"
               + " <input type=\"submit\" name=\"aksi\" value=\"cariMahasiswa\"/>"
               + "</td></tr>"
               + "<tr><td>Nama</td><td><input type=\"text\" name=\"nama\" value=\"" + nama + "\" readonly/></td></tr>"
               + "<tr><td>Semester</td><td><input type=\"text\" name=\"semester\" value=\"" + semester + "\" size=\"3\" readonly/></td></tr>"
               + "<tr><td>Kelas</td><td><input type=\"text\" name=\"kelas\" value=\"" + kelas + "\" size=\"3\" readonly/></td></tr>"
               + "<tr><td>Kode Mata Kuliah</td><td><input type=\"text\" name=\"kodeMataKuliah\" value=\"" + kodeMK + "\"/>"
               + " <input type=\"submit\" name=\"aksi\" value=\"cariMataKuliah\"/>"
               + "</td></tr>"
               + "<tr><td>Nama Mata Kuliah</td><td><input type=\"text\" name=\"namaMataKuliah\" value=\"" + namaMK + "\" readonly/></td></tr>"
               + "<tr><td>Jumlah SKS</td><td><input type=\"text\" name=\"jumlahSks\" value=\"" + sks + "\" size=\"3\" readonly/></td></tr>"
               + "<tr><td>Jumlah Hadir</td><td><input type=\"text\" name=\"jumlahHadir\" value=\"" + hadirStr + "\" size=\"3\"/></td></tr>"
               + "<tr><td>Nilai Tugas</td><td><input type=\"text\" name=\"nilaiTugas\" value=\"" + tugasStr + "\" size=\"5\"/></td></tr>"
               + "<tr><td>Nilai UTS</td><td><input type=\"text\" name=\"nilaiUTS\" value=\"" + utsStr + "\" size=\"5\"/></td></tr>"
               + "<tr><td>Nilai UAS</td><td><input type=\"text\" name=\"nilaiUAS\" value=\"" + uasStr + "\" size=\"5\"/></td></tr>"
               + "<tr><td colspan=\"2\">"
               + "<input type=\"submit\" name=\"aksi\" value=\"simpan\"/> "
               + "<input type=\"submit\" name=\"aksi\" value=\"hapus\"/>"
               + "</td></tr></table></form>";

        new MainForm().tampilkan(request, response, konten);
    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { processRequest(req, res); }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { processRequest(req, res); }
}
