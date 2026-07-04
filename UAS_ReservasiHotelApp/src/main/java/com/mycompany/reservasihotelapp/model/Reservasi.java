package com.mycompany.reservasihotelapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Entity JPA yang merepresentasikan satu baris data reservasi.
 * Atribut sesuai permintaan soal: id, nama tamu, tipe kamar,
 * tanggal check-in, tanggal check-out.
 *
 * Anotasi @Entity + @Table membuat Hibernate memetakan class ini ke
 * tabel "reservasi" di MySQL (ddl-auto=update akan membuatkan tabelnya
 * otomatis saat aplikasi pertama kali jalan).
 */
@Entity
@Table(name = "reservasi")
public class Reservasi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nama tamu wajib diisi")
    @Column(name = "nama_tamu", nullable = false, length = 100)
    private String namaTamu;

    @NotNull(message = "Tipe kamar wajib dipilih")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipe_kamar", nullable = false, length = 20)
    private TipeKamar tipeKamar;

    @NotNull(message = "Tanggal check-in wajib diisi")
    @FutureOrPresent(message = "Tanggal check-in tidak boleh tanggal yang sudah lewat")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggal_checkin", nullable = false)
    private LocalDate tanggalCheckIn;

    @NotNull(message = "Tanggal check-out wajib diisi")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggal_checkout", nullable = false)
    private LocalDate tanggalCheckOut;

    public Reservasi() {
    }

    public Reservasi(String namaTamu, TipeKamar tipeKamar, LocalDate tanggalCheckIn, LocalDate tanggalCheckOut) {
        this.namaTamu = namaTamu;
        this.tipeKamar = tipeKamar;
        this.tanggalCheckIn = tanggalCheckIn;
        this.tanggalCheckOut = tanggalCheckOut;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaTamu() {
        return namaTamu;
    }

    public void setNamaTamu(String namaTamu) {
        this.namaTamu = namaTamu;
    }

    public TipeKamar getTipeKamar() {
        return tipeKamar;
    }

    public void setTipeKamar(TipeKamar tipeKamar) {
        this.tipeKamar = tipeKamar;
    }

    public LocalDate getTanggalCheckIn() {
        return tanggalCheckIn;
    }

    public void setTanggalCheckIn(LocalDate tanggalCheckIn) {
        this.tanggalCheckIn = tanggalCheckIn;
    }

    public LocalDate getTanggalCheckOut() {
        return tanggalCheckOut;
    }

    public void setTanggalCheckOut(LocalDate tanggalCheckOut) {
        this.tanggalCheckOut = tanggalCheckOut;
    }

    /**
     * Method turunan (bukan disimpan di DB): jumlah malam menginap,
     * dihitung dari selisih tanggal check-out dan check-in.
     */
    public long getJumlahMalam() {
        if (tanggalCheckIn == null || tanggalCheckOut == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(tanggalCheckIn, tanggalCheckOut);
    }

    /**
     * Method turunan: total harga = jumlah malam x harga per malam
     * tipe kamar yang dipilih.
     */
    public long getTotalHarga() {
        if (tipeKamar == null) {
            return 0;
        }
        return getJumlahMalam() * tipeKamar.getHargaPerMalam();
    }
}
