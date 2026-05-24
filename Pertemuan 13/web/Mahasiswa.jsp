<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html><html>
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="style.css" rel="stylesheet" type="text/css"/>
<title>Data Mahasiswa</title></head>
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
<td align="left" valign="top" bgcolor="#ffffff" style="padding:20px;">
<nav><ul>
<li><a href="index.jsp">Home</a></li>
<li><a href="#">Master Data</a><ul><li><a href="Mahasiswa">Mahasiswa</a></li><li><a href="MataKuliah">Mata Kuliah</a></li></ul></li>
<li><a href="#">Transaksi</a><ul><li><a href="Nilai">Nilai</a></li></ul></li>
<li><a href="#">Laporan</a><ul><li><a href="LaporanNilai">Laporan Nilai</a></li></ul></li>
<li><a href="LoginController">Login</a></li>
</ul></nav><br>
<h2>Data Mahasiswa</h2>
<table border="1" cellpadding="8" cellspacing="0" style="border-collapse:collapse;width:90%;">
<tr bgcolor="#c4c0f1"><th>NIM</th><th>Nama</th><th>Kelas</th><th>Program Studi</th></tr>
<tr bgcolor="#ffffff"><td>231011400547</td><td><b>Haidar Reyhan</b></td><td>06TPLE016</td><td>Teknik Informatika</td></tr>
<tr bgcolor="#f0f0ff"><td>231011400501</td><td>Budi Santoso</td><td>06TPLE016</td><td>Teknik Informatika</td></tr>
<tr bgcolor="#ffffff"><td>231011400502</td><td>Siti Rahayu</td><td>06TPLE016</td><td>Teknik Informatika</td></tr>
<tr bgcolor="#f0f0ff"><td>231011400503</td><td>Ahmad Fauzi</td><td>06TPLE016</td><td>Teknik Informatika</td></tr>
<tr bgcolor="#ffffff"><td>231011400504</td><td>Dewi Lestari</td><td>06TPLE016</td><td>Teknik Informatika</td></tr>
</table>
<br><a href="index.jsp">&laquo; Kembali ke Home</a>
</td></tr>
<tr><td colspan="2" align="center" bgcolor="#eeeeff">
<small>Copyright &copy; 2016 Universitas Pamulang<br>Jl. Surya Kencana No. 1 Pamulang, Tangerang Selatan, Banten</small>
</td></tr></table></center></body></html>