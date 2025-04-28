package com.beautysalon.controller;
// Указываем, что файл относится к контроллерам (API)

import com.beautysalon.config.JwtUtil;
import com.beautysalon.model.Service;
import com.beautysalon.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
// Все маршруты в этом классе начинаются с /api/services
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private JwtUtil jwtUtil;
    // Для извлечения роли из токена

    @GetMapping
    public List<Service> getAllServices() {
        // Этот метод доступен всем авторизованным пользователям
        return serviceRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> addService(@RequestBody Service service,
                                        @RequestHeader("Authorization") String authHeader) {
        // Извлекаем токен
        String token = authHeader.substring(7);
        String role = jwtUtil.extractRole(token);

        if (!role.equals("admin")) {
            return ResponseEntity.status(403).body("Access denied: only admin can add services");
        }

        Service saved = serviceRepository.save(service);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateService(@PathVariable String id,
                                           @RequestBody Service updatedService,
                                           @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String role = jwtUtil.extractRole(token);

        if (!role.equals("admin")) {
            return ResponseEntity.status(403).body("Access denied: only admin can update services");
        }

        return serviceRepository.findById(id).map(service -> {
            service.setName(updatedService.getName());
            service.setDescription(updatedService.getDescription());
            service.setDuration(updatedService.getDuration());
            service.setPrice(updatedService.getPrice());
            service.setCategory(updatedService.getCategory());
            service.setPhotoUrl(updatedService.getPhotoUrl());
            Service saved = serviceRepository.save(service);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getServiceById(@PathVariable String id) {
        return serviceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteService(@PathVariable String id,
                                           @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String role = jwtUtil.extractRole(token);

        if (!role.equals("admin")) {
            return ResponseEntity.status(403).body("Access denied: only admin can delete services");
        }

        if (!serviceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        serviceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
