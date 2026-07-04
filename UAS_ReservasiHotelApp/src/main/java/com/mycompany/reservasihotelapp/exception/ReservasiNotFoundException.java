package com.mycompany.reservasihotelapp.exception;

/**
 * Dilempar saat reservasi dengan id tertentu tidak ditemukan
 * di database (misalnya saat proses hapus).
 */
public class ReservasiNotFoundException extends RuntimeException {

    public ReservasiNotFoundException(String message) {
        super(message);
    }
}
