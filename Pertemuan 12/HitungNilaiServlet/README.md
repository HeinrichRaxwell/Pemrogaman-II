# Pertemuan 12 - Pemrograman Dasar Web (Java Servlet)

Nama: Haidar Reyhan
NIM: 231011400547
Mata Kuliah: Pemrograman II  
Universitas Pamulang - Teknik Informatika

## Struktur Project
```
HitungNilaiServlet/
├── src/java/com/unpam/servlet/
│   └── HitungNilai.java        <- Servlet utama
├── web/
│   ├── WEB-INF/
│   │   └── web.xml             <- Deployment Descriptor
│   ├── index.html              <- Halaman form input
│   └── TestJSP.jsp             <- File JSP test
└── .gitignore
```

## Cara Menjalankan
1. Buka project di NetBeans
2. Pastikan menggunakan **Tomcat 10+** (pakai `jakarta.servlet`)
3. Run project (Shift+F6)
4. Akses: `localhost:8080/HitungNilaiServlet/MenghitungNilai`

## Catatan
- Tomcat 10+ menggunakan `jakarta.servlet` (bukan `javax.servlet`)
- URL Pattern bisa diubah di `web.xml`: `/MenghitungNilai`, `/*`, atau `/`
