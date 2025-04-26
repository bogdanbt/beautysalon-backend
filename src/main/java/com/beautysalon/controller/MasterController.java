package com.beautysalon.controller;


import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.beautysalon.repository.AppointmentRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import com.beautysalon.dto.PublicMasterDTO;
import com.beautysalon.model.Master;
import com.beautysalon.model.User;
import com.beautysalon.repository.MasterRepository;
import com.beautysalon.repository.UserRepository;
import com.beautysalon.config.JwtUtil;
import com.beautysalon.dto.CreateMasterWithUserRequest;
import com.beautysalon.dto.MasterWithSlotsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/masters")
public class MasterController {

    @Autowired
    private MasterRepository masterRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<Master> getAllMasters() {
        return masterRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createMaster(@RequestBody Master master,
                                          @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String role = jwtUtil.extractRole(token);
        if (!role.equals("admin")) {
            return ResponseEntity.status(403).body("Only admin can create master");
        }

        Master saved = masterRepository.save(master);
        return ResponseEntity.ok(saved);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getMasterById(@PathVariable String id,
                                           @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String role = jwtUtil.extractRole(token);

        if (!role.equals("admin")) {
            return ResponseEntity.status(403).body("Access denied");
        }

        return masterRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔥 Новый эндпоинт: создать User + Master вместе
    @PostMapping("/full-create")
    public ResponseEntity<?> createFullMaster(@RequestBody CreateMasterWithUserRequest request,
                                              @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String role = jwtUtil.extractRole(token);

        if (!role.equals("admin")) {
            return ResponseEntity.status(403).body("Only admin can do this");
        }

        if (userRepository.existsByEmail(request.email)) {
            return ResponseEntity.status(409).body("User with this email already exists");
        }

        // 1. Создаём User
        User user = new User();
        user.setName(request.name);
        user.setEmail(request.email);
        user.setPassword(passwordEncoder.encode(request.password));
        user.setRole("master");

        userRepository.save(user);

        // 2. Создаём Master
        Master master = new Master();
        master.setName(request.name);
        master.setUserId(user.getId());
        master.setServiceIds(request.serviceIds);
        master.setActive(request.active);
        master.setOnVacation(request.onVacation);
        master.setSchedule(request.schedule);

        Master saved = masterRepository.save(master);

        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMaster(@PathVariable String id,
                                          @RequestBody Master updated,
                                          @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String role = jwtUtil.extractRole(token);
        if (!role.equals("admin")) {
            return ResponseEntity.status(403).body("Only admin can update master");
        }

        return masterRepository.findById(id).map(master -> {
            master.setName(updated.getName());
            master.setServiceIds(updated.getServiceIds());
            master.setSchedule(updated.getSchedule());
            master.setActive(updated.isActive());
            master.setOnVacation(updated.isOnVacation());
            master.setUserId(updated.getUserId());
            return ResponseEntity.ok(masterRepository.save(master));
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-service/{serviceId}")
    public List<Master> getMastersByService(@PathVariable String serviceId) {
        return masterRepository.findByServiceIdsContains(serviceId);
    }

    @GetMapping("/with-slots")
    public ResponseEntity<?> getMastersWithSlots(@RequestParam String serviceId) {
        List<Master> masters = masterRepository.findByServiceIdsContains(serviceId)
                .stream()
                .filter(Master::isActive)
                .filter(m -> !m.isOnVacation())
                .toList();

        LocalDate today = LocalDate.now();
        int daysAhead = 14;

        List<MasterWithSlotsDTO> result = new ArrayList<>();

        for (Master master : masters) {
            Map<String, List<String>> availableSlots = new LinkedHashMap<>();

            for (int i = 0; i < daysAhead; i++) {
                LocalDate date = today.plusDays(i);
                String weekDay = getDayKey(date);
                List<String> times = master.getSchedule().getOrDefault(weekDay, List.of());

                for (String time : times) {
                    boolean isTaken = appointmentRepository.existsByMasterIdAndDateAndTime(
                            master.getId(), date, LocalTime.parse(time)
                    );
                    if (!isTaken) {
                        String dateKey = date.toString();
                        availableSlots.computeIfAbsent(dateKey, k -> new ArrayList<>()).add(time);
                    }
                }
            }

            result.add(new MasterWithSlotsDTO(master.getId(), master.getName(), availableSlots));
        }

        return ResponseEntity.ok(result);
    }

    private String getDayKey(LocalDate date) {
        return switch (date.getDayOfWeek()) {
            case MONDAY -> "Mon";
            case TUESDAY -> "Tue";
            case WEDNESDAY -> "Wed";
            case THURSDAY -> "Thu";
            case FRIDAY -> "Fri";
            case SATURDAY -> "Sat";
            case SUNDAY -> "Sun";
        };
    }

    @GetMapping("/available-slots")
    public ResponseEntity<?> getAvailableSlots(@RequestParam String serviceId,
                                               @RequestParam(defaultValue = "14") int daysAhead) {
        List<Master> masters = masterRepository.findByServiceIdsContains(serviceId);
        LocalDate today = LocalDate.now();

        Map<String, Map<String, List<PublicMasterDTO>>> result = new LinkedHashMap<>();

        for (int i = 0; i < daysAhead; i++) {
            LocalDate date = today.plusDays(i);
            String dayKey = date.toString();
            String weekDay = getDayKey(date);

            Map<String, List<PublicMasterDTO>> slots = new LinkedHashMap<>();

            for (String time : getStandardTimes()) {
                List<PublicMasterDTO> availableMasters = masters.stream()
                        .filter(m -> m.isActive() && !m.isOnVacation())
                        .filter(m -> m.getSchedule().getOrDefault(weekDay, List.of()).contains(time))
                        .filter(m -> !appointmentRepository.existsByMasterIdAndDateAndTime(
                                m.getId(), date, LocalTime.parse(time)))
                        .map(PublicMasterDTO::new)
                        .toList();

                if (!availableMasters.isEmpty()) {
                    slots.put(time, availableMasters);
                }
            }

            if (!slots.isEmpty()) {
                result.put(dayKey, slots);
            }
        }

        return ResponseEntity.ok(result);
    }

    private List<String> getStandardTimes() {
        return List.of("09:00", "10:00", "11:00", "12:00", "13:00",
                "14:00", "15:00", "16:00", "17:00");
    }


    @GetMapping("/available")
    public List<Master> getAvailableMasters(
            @RequestParam String serviceId,
            @RequestParam String date,
            @RequestParam String time
    ) {
        List<Master> all = masterRepository.findByServiceIdsContains(serviceId);

        // Убираем тех, кто:
        // 1. В отпуске
        // 2. Не активен
        // 3. Не работает в это время
        // 4. Уже занят по записи

        return all.stream().filter(master -> {
            if (!master.isActive() || master.isOnVacation()) return false;
            List<String> times = master.getSchedule().getOrDefault(getDayKey(date), List.of());
            if (!times.contains(time)) return false;

            // Проверка по AppointmentRepository
            boolean isTaken = appointmentRepository.existsByMasterIdAndDateAndTime(
                    master.getId(), LocalDate.parse(date), LocalTime.parse(time)
            );

            return !isTaken;
        }).toList();
    }

    private String getDayKey(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);
        return switch (date.getDayOfWeek()) {
            case MONDAY -> "Mon";
            case TUESDAY -> "Tue";
            case WEDNESDAY -> "Wed";
            case THURSDAY -> "Thu";
            case FRIDAY -> "Fri";
            case SATURDAY -> "Sat";
            case SUNDAY -> "Sun";
        };
    }

    @GetMapping("/by-user")
    public ResponseEntity<Master> getMasterByUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String userId = jwtUtil.extractUserId(token);

        Optional<Master> master = masterRepository.findAll()
                .stream()
                .filter(m -> m.getUserId().equals(userId))
                .findFirst();

        return master.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }
}
