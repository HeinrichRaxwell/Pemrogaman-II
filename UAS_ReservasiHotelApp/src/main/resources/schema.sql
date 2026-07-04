-- Skema database Aplikasi Reservasi Hotel.
-- File ini dijalankan OTOMATIS oleh Spring Boot setiap kali aplikasi start
-- (lihat spring.sql.init.mode=always di application.properties),
-- jadi tabel akan selalu ada tanpa perlu membuatnya manual di phpMyAdmin/MySQL Workbench.

CREATE TABLE IF NOT EXISTS reservasi (
    id               BIGINT NOT NULL AUTO_INCREMENT,
    nama_tamu        VARCHAR(100) NOT NULL,
    tipe_kamar       ENUM('STANDARD', 'DELUXE', 'SUITE') NOT NULL,
    tanggal_checkin  DATE NOT NULL,
    tanggal_checkout DATE NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
