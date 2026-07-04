package com.mycompany.reservasihotelapp.exception;

/**
 * Dilempar saat kuota kamar untuk tipe & rentang tanggal tertentu
 * sudah habis (jumlah reservasi overlap >= kuota tipe kamar tersebut).
 * Ini adalah unchecked exception (extends RuntimeException) karena
 * merupakan aturan bisnis, bukan error pemrograman.
 */
public class KamarPenuhException extends RuntimeException {

    public KamarPenuhException(String message) {
        super(message);
    }
}
