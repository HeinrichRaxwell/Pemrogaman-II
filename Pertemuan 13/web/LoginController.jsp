<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String msg = "";
    String action = request.getParameter("action");
    if ("logout".equals(action)) { session.invalidate(); response.sendRedirect("index.jsp"); return; }
    if ("login".equals(action)) {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        if ("admin".equals(user) && "admin".equals(pass)) {
            session.setAttribute("userName", "Admin"); response.sendRedirect("index.jsp"); return;
        } else if ("231011400547".equals(user) && "haidar".equals(pass)) {
            session.setAttribute("userName", "Haidar Reyhan (231011400547)"); response.sendRedirect("index.jsp"); return;
        } else { msg = "Username atau password salah!"; }
    }
%>
<!DOCTYPE html><html>
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="style.css" rel="stylesheet" type="text/css"/><title>Login</title></head>
<body bgcolor="#808080"><center>
<table width="80%" bgcolor="#eeeeee">
<tr><td colspan="2" align="center"><br>
<h2 style="margin-bottom:0px;margin-top:0px;">Informasi Nilai Mahasiswa</h2>
<h1 style="margin-bottom:0px;margin-top:0px;">UNIVERSITAS PAMULANG</h1>
<h4 style="margin-bottom:0px;margin-top:0px;">Jl. Surya Kencana No. 1 Pamulang, Tangerang Selatan, Banten</h4>
<br></td></tr>
<tr height="400">
<td width="200" align="center" valign="top" bgcolor="#eeffee"><br>
<div id="menu"><br><b>Master Data</b><br>
<a href="Mahasiswa">Mahasiswa</a><br><a href="MataKuliah">Mata Kuliah</a><br><br>
<b>Transaksi</b><br><a href="Nilai">Nilai</a><br><br>
<b>Laporan</b><br><a href="LaporanNilai">Laporan Nilai</a><br><br>
<a href="LoginController">Login</a><br></div></td>
<td align="center" valign="top" bgcolor="#ffffff" style="padding:30px;">
<nav><ul>
<li><a href="index.jsp">Home</a></li>
<li><a href="#">Master Data</a><ul><li><a href="Mahasiswa">Mahasiswa</a></li><li><a href="MataKuliah">Mata Kuliah</a></li></ul></li>
<li><a href="#">Transaksi</a><ul><li><a href="Nilai">Nilai</a></li></ul></li>
<li><a href="#">Laporan</a><ul><li><a href="LaporanNilai">Laporan Nilai</a></li></ul></li>
<li><a href="LoginController">Login</a></li>
</ul></nav><br>
<h2>Login</h2>
<% if (!msg.isEmpty()) { %><p style="color:red;"><b><%=msg%></b></p><% } %>
<form method="POST" action="LoginController">
<input type="hidden" name="action" value="login">
<table cellpadding="8">
<tr><td>Username</td><td><input type="text" name="username" size="20" placeholder="admin atau NIM"/></td></tr>
<tr><td>Password</td><td><input type="password" name="password" size="20"/></td></tr>
<tr><td colspan="2" align="center">
<input type="submit" value="Login" style="background:#577927;color:white;padding:5px 20px;border:none;border-radius:4px;cursor:pointer;"/>
</td></tr></table></form><br>
<small style="color:gray;">Hint: <b>admin/admin</b> &nbsp;|&nbsp; NIM: <b>231011400547</b> / pass: <b>haidar</b></small>
<br><br><a href="index.jsp">&laquo; Kembali ke Home</a>
</td></tr>
<tr><td colspan="2" align="center" bgcolor="#eeeeff">
<small>Copyright &copy; 2016 Universitas Pamulang<br>Jl. Surya Kencana No. 1 Pamulang, Tangerang Selatan, Banten</small>
</td></tr></table></center></body></html>