package com.beautysalon.controller;
import com.beautysalon.service.AppointmentService;
import com.beautysalon.repository.UserRepository;
import com.beautysalon.model.Master;
import java.util.Optional;

import com.beautysalon.config.JwtUtil;
import com.beautysalon.model.Appointment;
import com.beautysalon.repository.AppointmentRepository;
import com.beautysalon.repository.ServiceRepository;
import com.beautysalon.repository.MasterRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.beautysalon.dto.AppointmentInfoDTO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {


    @Autowired
    private ServiceRepository serviceRepository;


    @Autowired
    private MasterRepository masterRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;


    // 📅 Создать запись (только если мастер свободен)
    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment,
                                               @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String userId = jwtUtil.extractUserId(token);

        // Проверка занятости
        boolean isTaken = appointmentRepository.existsByMasterIdAndDateAndTime(
                appointment.getMasterId(),
                appointment.getDate(),
                appointment.getTime()
        );

        if (isTaken) {
            return ResponseEntity.status(409).body("Мастер уже занят в это время.");
        }

        appointment.setUserId(userId); // Привязываем запись к пользователю
        Appointment saved = appointmentRepository.save(appointment);

        return ResponseEntity.ok(saved);
    }

    // 👤 Получить записи клиента (по userId)
//    @GetMapping("/my")
//    public List<Appointment> getMyAppointments(@RequestHeader("Authorization") String authHeader) {
//        String token = authHeader.substring(7);
//        String userId = jwtUtil.extractUserId(token);
//        return appointmentRepository.findByUserId(userId);
//    }
    @GetMapping("/my")
    public ResponseEntity<?> getMyAppointments(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String userId = jwtUtil.extractUserId(token);

        List<Appointment> list = appointmentRepository.findByUserId(userId);

        // Подгружаем названия
        List<AppointmentInfoDTO> result = list.stream().map(app -> {
            String serviceName = serviceRepository.findById(app.getServiceId())
                    .map(s -> s.getName()).orElse("неизвестно");

            String masterName = masterRepository.findById(app.getMasterId())
                    .map(m -> m.getName()).orElse("неизвестно");

            String clientEmail = "неизвестно";
            if (app.getUserId() != null) {
                clientEmail = userRepository.findById(app.getUserId())
                        .map(u -> u.getEmail()).orElse("неизвестно");
            }

            return new AppointmentInfoDTO(
                    app.getId(),
                    serviceName,
                    masterName,
                    app.getDate().toString(),
                    app.getTime().toString(),
                    clientEmail
            );
        }).toList();


        return ResponseEntity.ok(result);
    }

    // ✂️ Получить записи мастера (по его userId)

    // ✂️ Получить записи мастера (по его userId)
    @GetMapping("/master")
    public ResponseEntity<?> getMasterAppointments(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String userId = jwtUtil.extractUserId(token);

        Master master = masterRepository.findByUserId(userId).orElse(null);
        if (master == null) {
            return ResponseEntity.status(404).body("Мастер не найден");
        }

        return ResponseEntity.ok(appointmentService.getAppointmentsForMaster(master.getId()));
    }

    // (по желанию) 🗑 Удаление записи
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable String id) {
        appointmentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin/all")
    public List<AppointmentInfoDTO> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

}
