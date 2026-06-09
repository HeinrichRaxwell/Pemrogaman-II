package com.unpam.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.unpam.model.Koneksi;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperExportManager;

@WebServlet(name = "LaporanNilaiController", urlPatterns = {"/LaporanNilaiController"})
public class LaporanNilaiController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/pdf");
        
        Koneksi connObj = new Koneksi();
        Connection conn = connObj.getConnection();
        
        OutputStream outStream = null;
        try {
            outStream = response.getOutputStream();
            String jrxmlPath = getServletContext().getRealPath("/reports/NilaiReport.jrxml");
            
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlPath);
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
            
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
            
        } catch (Exception e) {
            response.setContentType("text/html");
            response.getWriter().println("<html><body><h3>Gagal mencetak laporan:</h3><pre>" + e.getMessage() + "</pre></body></html>");
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (Exception ex) {}
            }
            if (outStream != null) {
                try { outStream.flush(); outStream.close(); } catch (Exception ex) {}
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
