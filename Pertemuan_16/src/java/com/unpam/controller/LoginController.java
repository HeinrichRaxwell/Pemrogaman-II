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
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String aksi = "";
        try { 
            if (request.getParameter("aksi") != null) {
                aksi = request.getParameter("aksi").toString(); 
            }
        } catch (Exception ex) {}

        String konten = "", pesan = "";

        if (aksi.equals("login") || "POST".equalsIgnoreCase(request.getMethod())) {
            try {
                String nim = request.getParameter("nim");
                String pwd = request.getParameter("password");
                Enkripsi enkripsi = new Enkripsi();
                Mahasiswa mahasiswa = new Mahasiswa();
                mahasiswa.setNim(nim);
                mahasiswa.setPassword(enkripsi.hashMD5(pwd));

                if (mahasiswa.login()) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("userName", mahasiswa.getNama());

                    String menu = "<br><b>Master Data</b><br>"
                            + "<a href=MahasiswaController>Mahasiswa</a><br>"
                            + "<a href=MataKuliahController>Mata Kuliah</a><br><br>"
                            + "<b>Transaksi</b><br>"
                            + "<a href=NilaiController>Nilai</a><br><br>"
                            + "<b>Laporan</b><br>"
                            + "<a href=LaporanNilaiController>Nilai</a><br><br>"
                            + "<a href=LogoutController>Logout</a><br><br>";
                    String topMenu = "<nav><ul>"
                            + "<li><a href=MainForm>Home</a></li>"
                            + "<li><a href=#>Master Data</a><ul>"
                            + "<li><a href=MahasiswaController>Mahasiswa</a></li>"
                            + "<li><a href=MataKuliahController>Mata Kuliah</a></li>"
                            + "</ul></li>"
                            + "<li><a href=#>Transaksi</a><ul>"
                            + "<li><a href=NilaiController>Nilai</a></li>"
                            + "</ul></li>"
                            + "<li><a href=#>Laporan</a><ul>"
                            + "<li><a href=LaporanNilaiController>Nilai</a></li>"
                            + "</ul></li>"
                            + "<li><a href=LogoutController>Logout</a></li>"
                            + "</ul></nav>";

                    session.setAttribute("menu", menu);
                    session.setAttribute("topMenu", topMenu);
                    konten = "<br><h1>Selamat Datang</h1><h2>" + mahasiswa.getNama() + "</h2>";
                } else {
                    pesan = "<font color=red>NIM atau Password salah!</font>";
                }
            } catch (Exception ex) {
                pesan = "<font color=red>Error: " + ex.getMessage() + "</font>";
            }
        }

        if (konten.equals("")) {
            konten = "<h2>Login</h2>" + pesan
                   + "<form method=\"POST\" action=\"LoginController\">"
                   + "<input type=\"hidden\" name=\"aksi\" value=\"login\"/>"
                   + "<table>"
                   + "<tr><td>NIM</td><td><input type=\"text\" name=\"nim\"/></td></tr>"
                   + "<tr><td>Password</td><td><input type=\"password\" name=\"password\"/></td></tr>"
                   + "<tr><td colspan=\"2\"><input type=\"submit\" value=\"Login\"/></td></tr>"
                   + "</table></form>";
        }

        new MainForm().tampilkan(request, response, konten);
    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { processRequest(req, res); }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { processRequest(req, res); }
}
