package com.beautysalon.repository;
// Указываем, что файл лежит в папке repository

import com.beautysalon.model.Appointment;
// Импортируем модель записи, которую мы сделали до этого

import org.springframework.data.mongodb.repository.MongoRepository;
// Импортируем интерфейс доступа к MongoDB

public interface AppointmentRepository extends MongoRepository<Appointment, String> {
    // Это интерфейс, который даёт Spring понимание:
    // "Я хочу работать с объектами Appointment, у которых ID — строка"

    // Мы ничего тут не пишем руками — Spring создаёт методы сам:
    // - findAll()
    // - save()
    // - findById()
    // - deleteById()
    // и т.д.
}
