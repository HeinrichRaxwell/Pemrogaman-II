# UAS Pemrograman II - Aplikasi Reservasi Hotel

Aplikasi reservasi hotel berbasis **Spring Boot + Thymeleaf + Spring Data JPA + MySQL**, dibuat untuk memenuhi tugas UAS Pemrograman II (Universitas Pamulang).

## Fitur
- CRUD data reservasi (nama tamu, tipe kamar, tanggal check-in/check-out)
- Validasi tanggal check-in/check-out
- Exception handling untuk kasus kamar penuh (`KamarPenuhException`) dan reservasi tidak ditemukan
- Tampilan Bootstrap 5 dengan tema "Grand Aria Hotel"

## Cara Menjalankan
1. Jalankan MySQL (mis. via XAMPP)
2. Sesuaikan konfigurasi koneksi database di `src/main/resources/application.properties`
3. Jalankan dengan Maven: `mvn spring-boot:run`
4. Buka `http://localhost:8080/`

## Struktur
- `model/` - entity `Reservasi` dan enum `TipeKamar`
- `repository/` - `ReservasiRepository` (Spring Data JPA)
- `service/` - `ReservasiService` (logika bisnis & validasi)
- `controller/` - `ReservasiController` (Spring MVC)
- `exception/` - exception kustom & `GlobalExceptionHandler`
- `templates/` - halaman Thymeleaf (list, form, error)
