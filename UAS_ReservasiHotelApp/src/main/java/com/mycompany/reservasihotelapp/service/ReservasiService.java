package com.mycompany.reservasihotelapp.service;

import com.mycompany.reservasihotelapp.exception.KamarPenuhException;
import com.mycompany.reservasihotelapp.exception.ReservasiNotFoundException;
import com.mycompany.reservasihotelapp.model.Reservasi;
import com.mycompany.reservasihotelapp.repository.ReservasiRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Lapisan Service berisi seluruh aturan bisnis aplikasi. Controller
 * sengaja tidak langsung bicara ke Repository supaya logika seperti
 * "cek kamar penuh" bisa dipakai ulang & dites terpisah dari lapisan web.
 */
@Service
public class ReservasiService {

    private final ReservasiRepository reservasiRepository;

    public ReservasiService(ReservasiRepository reservasiRepository) {
        this.reservasiRepository = reservasiRepository;
    }

    public List<Reservasi> semuaReservasi() {
        return reservasiRepository.findAllByOrderByTanggalCheckInAsc();
    }

    /**
     * Menyimpan reservasi baru setelah lolos dua pengecekan bisnis:
     * 1. Tanggal check-out harus setelah tanggal check-in.
     * 2. Kuota tipe kamar untuk rentang tanggal itu belum penuh.
     */
    public Reservasi simpanReservasi(Reservasi reservasi) {
        if (!reservasi.getTanggalCheckOut().isAfter(reservasi.getTanggalCheckIn())) {
            throw new IllegalArgumentException("Tanggal check-out harus setelah tanggal check-in");
        }

        List<Reservasi> reservasiOverlap = reservasiRepository.cariReservasiOverlap(
                reservasi.getTipeKamar(),
                reservasi.getTanggalCheckIn(),
                reservasi.getTanggalCheckOut());

        int kuota = reservasi.getTipeKamar().getKuota();
        if (reservasiOverlap.size() >= kuota) {
            throw new KamarPenuhException("Kamar tipe " + reservasi.getTipeKamar().getLabel()
                    + " sudah penuh (kuota " + kuota + " kamar) untuk rentang tanggal yang dipilih. "
                    + "Silakan pilih tipe kamar lain atau ubah tanggal.");
        }

        return reservasiRepository.save(reservasi);
    }

    public void hapusReservasi(Long id) {
        if (!reservasiRepository.existsById(id)) {
            throw new ReservasiNotFoundException("Reservasi dengan id " + id + " tidak ditemukan");
        }
        reservasiRepository.deleteById(id);
    }
}
