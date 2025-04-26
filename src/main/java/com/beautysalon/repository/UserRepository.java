package com.beautysalon.repository;
// Указываем, что файл находится в пакете repository (работа с базой)

import com.beautysalon.model.User;
// Импортируем нашу модель User

import org.springframework.data.mongodb.repository.MongoRepository;
// Импортируем интерфейс, который позволяет Spring работать с MongoDB

import java.util.Optional;
// Optional — тип, который может содержать объект или быть пустым (например, если пользователь не найден)

public interface UserRepository extends MongoRepository<User, String> {
    // Мы говорим Spring:
    // "Я хочу использовать MongoRepository для модели User, у которой ID — строка (String)"
    // И получаю готовые методы: findAll(), save(), findById(), deleteById() и т.д.

    Optional<User> findByEmail(String email);
    // Дополнительный метод: найти пользователя по email (для логина)

    boolean existsByEmail(String email);
    // Метод для проверки: существует ли пользователь с таким email (для регистрации)
}
