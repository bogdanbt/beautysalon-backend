package com.beautysalon.controller;
// Файл находится в папке controller

import com.beautysalon.model.Appointment;
// Импортируем модель записи

import com.beautysalon.repository.AppointmentRepository;
// Импортируем интерфейс доступа к базе

import org.springframework.beans.factory.annotation.Autowired;
// Автоматически внедряем нужные зависимости

import org.springframework.http.ResponseEntity;
// Для возврата HTTP-ответов со статусами

import org.springframework.web.bind.annotation.*;
// Импортируем аннотации @GetMapping, @PostMapping и т.д.

import java.util.List;
// Для возвращения списка записей

@RestController
@RequestMapping("/api/appointments")
// Все маршруты в этом классе будут начинаться с /api/appointments

public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;
    // Инжектим репозиторий для работы с MongoDB

    @GetMapping
    public List<Appointment> getAllAppointments() {
        // Метод обрабатывает GET /api/appointments
        // Возвращает список всех записей
        return appointmentRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        // Метод обрабатывает POST /api/appointments
        // Принимает JSON с данными записи и сохраняет её в MongoDB

        Appointment saved = appointmentRepository.save(appointment);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable String id) {
        // Метод обрабатывает DELETE /api/appointments/{id}
        // Удаляет запись по ID

        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
