package com.unpam.view;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "MainForm", urlPatterns = {"/MainForm"})
public class MainForm extends HttpServlet {

    public void tampilkan(HttpServletRequest request, HttpServletResponse response, String konten)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);

        String menu = "<br><b>Master Data</b><br>"
                + "<a href=MahasiswaController>Mahasiswa</a><br>"
                + "<a href=MataKuliahController>Mata Kuliah</a><br><br>"
                + "<b>Transaksi</b><br>"
                + "<a href=NilaiController>Nilai</a><br><br>"
                + "<b>Laporan</b><br>"
                + "<a href=LaporanNilaiController>Nilai</a><br><br>"
                + "<a href=LoginController>Login</a><br><br>";

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
                + "<li><a href=LoginController>Login</a></li>"
                + "</ul></nav>";

        String userName = "";
        if (!session.isNew()) {
            try { userName = session.getAttribute("userName").toString(); } catch (Exception ex) {}
            if (!((userName == null) || userName.equals(""))) {
                if (konten.equals("")) {
                    konten = "<br><h1>Selamat Datang</h1><h2>" + userName + "</h2>";
                }
                try { menu    = session.getAttribute("menu").toString();    } catch (Exception ex) {}
                try { topMenu = session.getAttribute("topMenu").toString(); } catch (Exception ex) {}
            }
        }

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<link href='style.css' rel='stylesheet' type='text/css' />");
            out.println("<title>Informasi Nilai Mahasiswa</title>");
            out.println("</head><body bgcolor=\"#808080\">");
            out.println("<center><table width=\"80%\" bgcolor=\"#eeeeee\">");
            out.println("<tr><td colspan=\"2\" align=\"center\">");
            out.println("<br><h2 style=\"margin-bottom:0px;margin-top:0px;\">Informasi Nilai Mahasiswa</h2>");
            out.println("<h1 style=\"margin-bottom:0px;margin-top:0px;\">UNIVERSITAS PAMULANG</h1>");
            out.println("<h4 style=\"margin-bottom:0px;margin-top:0px;\">Jl. Surya Kencana No. 1 Pamulang, Tangerang Selatan, Banten</h4>");
            out.println("<br></td></tr>");
            out.println("<tr height=\"400\">");
            out.println("<td width=\"200\" align=\"center\" valign=\"top\" bgcolor=\"#eeffee\">");
            out.println("<div id='menu'>" + menu + "</div>");
            out.println("</td>");
            out.println("<td align=\"center\" valign=\"top\" bgcolor=\"#ffffff\">");
            out.println(topMenu + "<br>" + konten);
            out.println("</td></tr>");
            out.println("<tr><td colspan=\"2\" align=\"center\" bgcolor=\"#eeeeff\">");
            out.println("<small>Copyright &copy; 2014 Universitas Pamulang<br>");
            out.println("Jl. Surya Kencana No. 1 Pamulang, Tangerang Selatan, Banten<br></small>");
            out.println("</td></tr></table></center></body></html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { tampilkan(request, response, ""); }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { tampilkan(request, response, ""); }
}
