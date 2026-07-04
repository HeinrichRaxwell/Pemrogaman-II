package com.mycompany.reservasihotelapp.repository;

import com.mycompany.reservasihotelapp.model.Reservasi;
import com.mycompany.reservasihotelapp.model.TipeKamar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository Spring Data JPA. Dengan meng-extends JpaRepository, kita
 * otomatis mendapat method CRUD (save, findById, findAll, deleteById, dst)
 * tanpa menulis satu baris SQL/JDBC manual pun - Spring Data JPA yang
 * menghasilkan implementasinya saat aplikasi berjalan.
 *
 * Query custom di bawah dipakai untuk mengecek "kamar penuh": mencari
 * semua reservasi dengan tipe kamar yang sama yang rentang tanggalnya
 * beririsan (overlap) dengan rentang tanggal yang baru diminta.
 */
public interface ReservasiRepository extends JpaRepository<Reservasi, Long> {

    @Query("SELECT r FROM Reservasi r WHERE r.tipeKamar = :tipeKamar "
            + "AND r.tanggalCheckIn < :checkOut AND r.tanggalCheckOut > :checkIn")
    List<Reservasi> cariReservasiOverlap(@Param("tipeKamar") TipeKamar tipeKamar,
                                          @Param("checkIn") LocalDate checkIn,
                                          @Param("checkOut") LocalDate checkOut);

    List<Reservasi> findAllByOrderByTanggalCheckInAsc();
}
