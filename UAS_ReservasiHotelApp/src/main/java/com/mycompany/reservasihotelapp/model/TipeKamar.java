package com.mycompany.reservasihotelapp.model;

/**
 * Enum tipe kamar hotel. Setiap tipe kamar punya kuota (jumlah kamar
 * fisik yang tersedia) dan harga per malam. Kuota inilah yang dipakai
 * ReservasiService untuk menentukan kapan sebuah tipe kamar dianggap "penuh".
 */
public enum TipeKamar {

    STANDARD("Standard", 5, 350_000),
    DELUXE("Deluxe", 3, 600_000),
    SUITE("Suite", 2, 1_200_000);

    private final String label;
    private final int kuota;
    private final long hargaPerMalam;

    TipeKamar(String label, int kuota, long hargaPerMalam) {
        this.label = label;
        this.kuota = kuota;
        this.hargaPerMalam = hargaPerMalam;
    }

    public String getLabel() {
        return label;
    }

    public int getKuota() {
        return kuota;
    }

    public long getHargaPerMalam() {
        return hargaPerMalam;
    }
}
