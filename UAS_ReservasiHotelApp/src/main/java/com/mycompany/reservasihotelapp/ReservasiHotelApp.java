package com.mycompany.reservasihotelapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Kelas utama (entry point) aplikasi Spring Boot Reservasi Hotel.
 * Anotasi @SpringBootApplication menggabungkan @Configuration,
 * @EnableAutoConfiguration, dan @ComponentScan sehingga Spring akan
 * otomatis mendeteksi Controller, Service, dan Repository di bawah package ini.
 */
@SpringBootApplication
public class ReservasiHotelApp {

    public static void main(String[] args) {
        SpringApplication.run(ReservasiHotelApp.class, args);
    }
}
