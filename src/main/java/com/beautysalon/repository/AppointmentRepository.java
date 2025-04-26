package com.beautysalon.repository;

import com.beautysalon.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {

    // Получить все записи на конкретную дату
    List<Appointment> findByMasterIdAndDate(String masterId, LocalDate date);

    // Проверка: есть ли запись у мастера на эту дату и время
    boolean existsByMasterIdAndDateAndTime(String masterId, LocalDate date, LocalTime time);

    // Получить все записи по мастеру
    List<Appointment> findByMasterId(String masterId);

    // Получить все записи по пользователю
    List<Appointment> findByUserId(String userId);
}
