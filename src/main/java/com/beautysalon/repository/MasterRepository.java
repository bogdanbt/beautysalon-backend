package com.beautysalon.repository;

import com.beautysalon.model.Master;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MasterRepository extends MongoRepository<Master, String> {
    // Этот интерфейс позволяет делать базовые действия с коллекцией masters:
    // - findAll()
    // - findById()
    // - save()
    // - deleteById()

    // Дополнительно: найдём всех мастеров, кто выполняет определённую услугу
    List<Master> findByServiceIdsContains(String serviceId);

    // Add this method to find by userId
    Optional<Master> findByUserId(String userId);

}
