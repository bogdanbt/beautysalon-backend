package com.beautysalon.repository;
// Указываем, что файл лежит в папке repository

import com.beautysalon.model.Service;
import org.springframework.data.mongodb.repository.MongoRepository;
// Импортируем интерфейс, который даёт доступ к базе данных MongoDB

public interface ServiceRepository extends MongoRepository<Service, String> {
    // Это интерфейс, который говорит Spring:
    // "Создай мне всё сам — и методы findAll(), save(), findById() и т.д."

    // <Service, String> — говорим:
    // работаем с сущностью Service, а ID у неё — строка (String)

    // Благодаря этому мы можем в контроллере писать:
    // serviceRepository.findAll();
    // serviceRepository.save(service);
}
