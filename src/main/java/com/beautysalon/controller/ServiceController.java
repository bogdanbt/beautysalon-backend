package com.beautysalon.controller;
// Указываем, что файл находится в папке controller

import com.beautysalon.model.Service;
// Импортируем модель "услуга", с которой будем работать

import com.beautysalon.repository.ServiceRepository;
// Импортируем репозиторий — интерфейс, через который мы обращаемся к базе данных

import org.springframework.beans.factory.annotation.Autowired;
// Аннотация @Autowired автоматически создаёт объект и вставляет его в нужное место

import org.springframework.http.ResponseEntity;
// ResponseEntity — это специальный объект-ответ. Позволяет возвращать и данные, и статус коды

import org.springframework.web.bind.annotation.*;
// Импортируем аннотации REST API: @RestController, @RequestMapping, @GetMapping и т.д.

import java.util.List;
// Импортируем List, чтобы возвращать список услуг

@RestController
// Аннотация @RestController говорит Spring: это контроллер, обрабатывающий HTTP-запросы и возвращающий JSON

@RequestMapping("/api/services")
// Все маршруты в этом классе будут начинаться с /api/services

public class ServiceController {
    // Это наш класс-контроллер, через который клиент будет взаимодействовать с услугами

    @Autowired
    private ServiceRepository serviceRepository;
    // Мы подключаем наш репозиторий, чтобы иметь доступ к базе данных.
    // Благодаря @Autowired Spring сам найдёт реализацию и "вставит" её сюда

    @GetMapping
    public List<Service> getAllServices() {
        // Метод обрабатывает GET-запрос на /api/services
        // Возвращает список всех услуг из базы
        return serviceRepository.findAll();
        // .findAll() — это метод, который возвращает все записи из коллекции "services"
    }

    @PostMapping
    public ResponseEntity<Service> addService(@RequestBody Service service) {
        // Метод обрабатывает POST-запрос на /api/services
        // @RequestBody говорит Spring: "возьми JSON из запроса и преврати в объект Service"
        // Например, если в Postman отправят:
        // {
        //   "name": "Маникюр", "description": "Классика", "duration": 60, "price": 30, "category": "Ногти"
        // }
        // — этот JSON превратится в объект `service` внутри метода

        Service saved = serviceRepository.save(service);
        // Сохраняем услугу в MongoDB

        return ResponseEntity.ok(saved);
        // Возвращаем сохранённую услугу + статус 200 OK
    }
}
