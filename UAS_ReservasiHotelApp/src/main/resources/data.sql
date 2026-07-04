-- Data contoh (seed data), otomatis dijalankan Spring Boot setelah schema.sql,
-- setiap kali aplikasi start (spring.sql.init.mode=always).
-- DELETE dulu supaya tidak terjadi duplikasi data setiap kali aplikasi di-restart.

DELETE FROM reservasi;

INSERT INTO reservasi (nama_tamu, tipe_kamar, tanggal_checkin, tanggal_checkout) VALUES
    ('Budi Santoso', 'STANDARD', '2026-08-01', '2026-08-03'),
    ('Siti Aminah',  'DELUXE',   '2026-08-05', '2026-08-07');
