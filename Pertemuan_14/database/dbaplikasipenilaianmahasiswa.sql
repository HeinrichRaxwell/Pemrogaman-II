-- Database: dbaplikasipenilaianmahasiswa
-- Nama   : Haidar Reyhan
-- NIM    : 231011400547
-- Kelas  : 06TPLE016
-- Dosen  : SONASA RINUSANTORO S.Kom.,M.A.

CREATE DATABASE IF NOT EXISTS dbaplikasipenilaianmahasiswa
    CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE dbaplikasipenilaianmahasiswa;

CREATE TABLE IF NOT EXISTS tbmahasiswa (
    nim        VARCHAR(20)  NOT NULL PRIMARY KEY,
    nama       VARCHAR(100) NOT NULL,
    semester   INT          NOT NULL DEFAULT 1,
    kelas      VARCHAR(20)  NOT NULL,
    password   VARCHAR(32)  NOT NULL COMMENT 'MD5 hash'
);

CREATE TABLE IF NOT EXISTS tbmatakuliah (
    kodeMataKuliah  VARCHAR(10)  NOT NULL PRIMARY KEY,
    namaMataKuliah  VARCHAR(100) NOT NULL,
    jumlahSks       INT          NOT NULL DEFAULT 2
);

CREATE TABLE IF NOT EXISTS tbnilai (
    id             INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nim            VARCHAR(20)  NOT NULL,
    kodeMataKuliah VARCHAR(10)  NOT NULL,
    jumlahHadir    INT          NOT NULL DEFAULT 0,
    nilaiUTS       FLOAT        NOT NULL DEFAULT 0,
    nilaiUAS       FLOAT        NOT NULL DEFAULT 0,
    nilaiTugas     FLOAT        NOT NULL DEFAULT 0,
    nilaiAkhir     FLOAT        NOT NULL DEFAULT 0,
    grade          VARCHAR(2)   NOT NULL DEFAULT '-',
    FOREIGN KEY (nim)            REFERENCES tbmahasiswa(nim),
    FOREIGN KEY (kodeMataKuliah) REFERENCES tbmatakuliah(kodeMataKuliah)
);

-- Data awal mahasiswa (password = MD5 dari 'haidar')
INSERT INTO tbmahasiswa VALUES
('231011400547', 'Haidar Reyhan', 6, '06TPLE016', '8a39d2abd3999ab73c34db2476849653');

-- Data awal mata kuliah
INSERT INTO tbmatakuliah VALUES
('MK001', 'Pemrograman II',              3),
('MK002', 'Basis Data II',               3),
('MK003', 'Sistem Pendukung Keputusan',  3),
('MK004', 'Pemrograman Mobile',          3),
('MK005', 'Kecerdasan Buatan',           3);
