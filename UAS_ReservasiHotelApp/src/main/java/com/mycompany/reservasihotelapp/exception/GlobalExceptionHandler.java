package com.mycompany.reservasihotelapp.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Penangan exception global. KamarPenuhException dan validasi input
 * sudah ditangani langsung di ReservasiController (supaya user
 * langsung melihat pesan error di form yang sama). @ControllerAdvice
 * ini menjadi jaring pengaman terakhir untuk error tak terduga
 * (contoh: koneksi ke MySQL terputus) agar aplikasi tidak menampilkan
 * stack trace mentah ke pengguna, melainkan halaman error yang rapi.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String tanganiErrorTakTerduga(Exception ex, Model model) {
        model.addAttribute("pesanError", "Terjadi kesalahan pada sistem: " + ex.getMessage());
        return "error";
    }
}
