package com.unpam.controller;

import com.unpam.model.MataKuliah;
import com.unpam.view.MainForm;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "MataKuliahController", urlPatterns = {"/MataKuliahController"})
public class MataKuliahController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String aksi = "";
        try { aksi = request.getParameter("aksi").toString(); } catch (Exception ex) {}

        MataKuliah mk = new MataKuliah();
        String konten = "", pesan = "";

        if (aksi.equals("simpan")) {
            try {
                mk.setKodeMataKuliah(request.getParameter("kodeMataKuliah"));
                mk.setNamaMataKuliah(request.getParameter("namaMataKuliah"));
                mk.setJumlahSks(Integer.parseInt(request.getParameter("jumlahSks")));
                pesan = mk.simpan()
                    ? "<font color=green>Data berhasil disimpan</font>"
                    : "<font color=red>" + mk.getPesan() + "</font>";
            } catch (Exception ex) {
                pesan = "<font color=red>Error: " + ex.getMessage() + "</font>";
            }
        } else if (aksi.equals("hapus")) {
            mk.setKodeMataKuliah(request.getParameter("kodeMataKuliah"));
            pesan = mk.hapus()
                ? "<font color=green>Data berhasil dihapus</font>"
                : "<font color=red>" + mk.getPesan() + "</font>";
        } else if (aksi.equals("cari")) {
            mk.setKodeMataKuliah(request.getParameter("kodeMataKuliah"));
            mk.cari();
        }
        
        // Selalu load list tabel agar otomatis muncul
        mk.lihat();

        String tabel = "";
        Object[][] list = mk.getList();
        if (list != null) {
            tabel = "<table border=1 cellpadding=4>"
                  + "<tr bgcolor=#cccccc><th>Kode</th><th>Nama Mata Kuliah</th><th>SKS</th></tr>";
            for (Object[] row : list) {
                tabel += "<tr><td><a href='MataKuliahController?aksi=cari&kodeMataKuliah=" + row[0] + "'>" + row[0] + "</a></td>"
                       + "<td>" + row[1] + "</td><td>" + row[2] + "</td></tr>";
            }
            tabel += "</table>";
        }

        String kode = mk.getKodeMataKuliah() != null ? mk.getKodeMataKuliah() : "";
        String nama = mk.getNamaMataKuliah() != null ? mk.getNamaMataKuliah() : "";
        int    sks  = mk.getJumlahSks();

        konten = "<h2>Master Data Mata Kuliah</h2>" + pesan
               + "<form method=\"POST\" action=\"MataKuliahController\">"
               + "<table>"
               + "<tr><td>Kode Mata Kuliah</td><td><input type=\"text\" name=\"kodeMataKuliah\" value=\"" + kode + "\"/>"
               + " <input type=\"submit\" name=\"aksi\" value=\"cari\"/>"
               + " <input type=\"submit\" name=\"aksi\" value=\"lihat\"/></td></tr>"
               + "<tr><td>Nama Mata Kuliah</td><td><input type=\"text\" name=\"namaMataKuliah\" value=\"" + nama + "\"/></td></tr>"
               + "<tr><td>Jumlah SKS</td><td><input type=\"text\" name=\"jumlahSks\" value=\"" + sks + "\" size=\"3\"/></td></tr>"
               + "<tr><td colspan=\"2\">"
               + "<input type=\"submit\" name=\"aksi\" value=\"simpan\"/> "
               + "<input type=\"submit\" name=\"aksi\" value=\"hapus\"/>"
               + "</td></tr></table></form><br>" + tabel;

        new MainForm().tampilkan(request, response, konten);
    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { processRequest(req, res); }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { processRequest(req, res); }
}
