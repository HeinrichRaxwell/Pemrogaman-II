<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html><html>
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="style.css" rel="stylesheet" type="text/css"/>
<title>Data Nilai</title></head>
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
<h2>Data Nilai Mahasiswa</h2>
<table border="1" cellpadding="7" cellspacing="0" style="border-collapse:collapse;width:100%;font-size:88%;">
<tr bgcolor="#c4c0f1"><th>NIM</th><th>Nama</th><th>Mata Kuliah</th><th>Hadir</th><th>UTS</th><th>UAS</th><th>Tugas</th><th>Nilai Akhir</th><th>Grade</th></tr>
<tr bgcolor="#fff"><td>231011400547</td><td>Haidar Reyhan</td><td>Pemrograman II</td><td>14</td><td>80</td><td>85</td><td>88</td><td><b>85.30</b></td><td><b style="color:green">A</b></td></tr>
<tr bgcolor="#f0f0ff"><td>231011400547</td><td>Haidar Reyhan</td><td>Basis Data II</td><td>14</td><td>75</td><td>78</td><td>80</td><td><b>78.10</b></td><td><b style="color:blue">B</b></td></tr>
<tr bgcolor="#fff"><td>231011400547</td><td>Haidar Reyhan</td><td>Sistem Pendukung Keputusan</td><td>16</td><td>70</td><td>72</td><td>75</td><td><b>72.75</b></td><td><b style="color:blue">B</b></td></tr>
<tr bgcolor="#f0f0ff"><td>231011400501</td><td>Budi Santoso</td><td>Pemrograman II</td><td>12</td><td>65</td><td>70</td><td>72</td><td><b>69.75</b></td><td><b style="color:orange">C</b></td></tr>
<tr bgcolor="#fff"><td>231011400502</td><td>Siti Rahayu</td><td>Pemrograman II</td><td>16</td><td>90</td><td>92</td><td>95</td><td><b>92.75</b></td><td><b style="color:green">A</b></td></tr>
</table>
<br><a href="index.jsp">&laquo; Kembali ke Home</a>
</td></tr>
<tr><td colspan="2" align="center" bgcolor="#eeeeff">
<small>Copyright &copy; 2016 Universitas Pamulang<br>Jl. Surya Kencana No. 1 Pamulang, Tangerang Selatan, Banten</small>
</td></tr></table></center></body></html>