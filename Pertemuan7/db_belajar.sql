CREATE DATABASE IF NOT EXISTS db_belajar;
USE db_belajar;

CREATE TABLE IF NOT EXISTS sembako (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama_barang VARCHAR(100) NOT NULL,
    satuan VARCHAR(20) NOT NULL,
    harga DOUBLE NOT NULL,
    stok INT NOT NULL
);

INSERT INTO sembako (nama_barang, satuan, harga, stok) VALUES
('Beras', 'Kg', 12000, 100),
('Gula Pasir', 'Kg', 14000, 80),
('Minyak Goreng', 'Liter', 18000, 60),
('Tepung Terigu', 'Kg', 10000, 50),
('Garam', 'Bungkus', 3000, 200),
('Telur Ayam', 'Kg', 28000, 40),
('Mie Instan', 'Bungkus', 3500, 150),
('Kecap Manis', 'Botol', 12000, 70);
