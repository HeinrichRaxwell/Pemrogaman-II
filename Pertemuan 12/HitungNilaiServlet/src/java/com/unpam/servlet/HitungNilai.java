package com.unpam.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HitungNilai extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>Hitung Nilai (Servlet)</title></head>");
        out.println("<body>");
        out.println("<h1><b>Menghitung Nilai</b></h1>");

        // Tampilkan form input
        out.println("<form method='POST' action=''>");
        out.println("<table>");
        out.println("<tr><td>Jumlah hadir (maks 16)</td><td><input type='number' name='jumlahHadir' min='0' max='16' required/></td></tr>");
        out.println("<tr><td>Nilai UTS</td><td><input type='number' name='nilaiUTS' min='0' max='100' required/></td></tr>");
        out.println("<tr><td>Nilai UAS</td><td><input type='number' name='nilaiUAS' min='0' max='100' required/></td></tr>");
        out.println("<tr><td>Nilai Tugas</td><td><input type='number' name='nilaiTugas' min='0' max='100' required/></td></tr>");
        out.println("<tr><td colspan='2'><input type='submit' value='Hitung'/></td></tr>");
        out.println("</table>");
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        int jumlahHadir   = Integer.parseInt(request.getParameter("jumlahHadir"));
        double nilaiUTS   = Double.parseDouble(request.getParameter("nilaiUTS"));
        double nilaiUAS   = Double.parseDouble(request.getParameter("nilaiUAS"));
        double nilaiTugas = Double.parseDouble(request.getParameter("nilaiTugas"));

        // Rumus: (hadir/16*100)*0.1 + tugas*0.2 + UTS*0.3 + UAS*0.4
        double nilaiKehadiran = (jumlahHadir / 16.0) * 100 * 0.1;
        double hasil = nilaiKehadiran + (nilaiTugas * 0.2) + (nilaiUTS * 0.3) + (nilaiUAS * 0.4);

        String grade;
        if      (hasil >= 80) grade = "A";
        else if (hasil >= 70) grade = "B";
        else if (hasil >= 60) grade = "C";
        else if (hasil >= 50) grade = "D";
        else                  grade = "E";

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>Hitung Nilai (Servlet)</title></head>");
        out.println("<body>");
        out.println("<h1><b>Menghitung Nilai</b></h1>");
        out.println("<p>Jumlah Hadir  : " + jumlahHadir  + "</p>");
        out.println("<p>Nilai UTS     : " + nilaiUTS     + "</p>");
        out.println("<p>Nilai UAS     : " + nilaiUAS     + "</p>");
        out.println("<p>Nilai Tugas   : " + nilaiTugas   + "</p>");
        out.println("<hr/>");
        out.println("<p><b>Nilai Akhir : " + String.format("%.2f", hasil) + "</b></p>");
        out.println("<p><b>Grade       : " + grade + "</b></p>");
        out.println("<br/><a href=''>Hitung Lagi</a>");
        out.println("</body>");
        out.println("</html>");
    }
}
