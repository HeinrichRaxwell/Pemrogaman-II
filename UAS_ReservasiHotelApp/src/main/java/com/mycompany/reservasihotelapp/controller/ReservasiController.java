package com.mycompany.reservasihotelapp.controller;

import com.mycompany.reservasihotelapp.exception.KamarPenuhException;
import com.mycompany.reservasihotelapp.exception.ReservasiNotFoundException;
import com.mycompany.reservasihotelapp.model.Reservasi;
import com.mycompany.reservasihotelapp.model.TipeKamar;
import com.mycompany.reservasihotelapp.service.ReservasiService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller Spring MVC: menerima request HTTP, memanggil Service,
 * lalu memilih view Thymeleaf yang akan dirender.
 *
 * Pola Post/Redirect/Get dipakai di simpanReservasi & hapusReservasi
 * (return "redirect:/") supaya refresh halaman tidak mengirim ulang form.
 */
@Controller
public class ReservasiController {

    private final ReservasiService reservasiService;

    public ReservasiController(ReservasiService reservasiService) {
        this.reservasiService = reservasiService;
    }

    @GetMapping("/")
    public String daftarReservasi(Model model) {
        model.addAttribute("daftarReservasi", reservasiService.semuaReservasi());
        return "reservasi/list";
    }

    @GetMapping("/reservasi/baru")
    public String formReservasiBaru(Model model) {
        model.addAttribute("reservasi", new Reservasi());
        model.addAttribute("semuaTipeKamar", TipeKamar.values());
        return "reservasi/form";
    }

    @PostMapping("/reservasi")
    public String simpanReservasi(@Valid @ModelAttribute("reservasi") Reservasi reservasi,
                                   BindingResult bindingResult,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {

        // 1) Validasi anotasi (@NotBlank, @NotNull, dst) pada Reservasi
        if (bindingResult.hasErrors()) {
            model.addAttribute("semuaTipeKamar", TipeKamar.values());
            return "reservasi/form";
        }

        // 2) Validasi aturan bisnis (tanggal & kuota kamar) dari ReservasiService
        try {
            reservasiService.simpanReservasi(reservasi);
            redirectAttributes.addFlashAttribute("pesanSukses",
                    "Reservasi atas nama " + reservasi.getNamaTamu() + " berhasil disimpan");
            return "redirect:/";
        } catch (KamarPenuhException | IllegalArgumentException ex) {
            model.addAttribute("semuaTipeKamar", TipeKamar.values());
            model.addAttribute("pesanError", ex.getMessage());
            return "reservasi/form";
        }
    }

    @PostMapping("/reservasi/{id}/hapus")
    public String hapusReservasi(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            reservasiService.hapusReservasi(id);
            redirectAttributes.addFlashAttribute("pesanSukses", "Reservasi berhasil dihapus");
        } catch (ReservasiNotFoundException ex) {
            redirectAttributes.addFlashAttribute("pesanError", ex.getMessage());
        }
        return "redirect:/";
    }
}
