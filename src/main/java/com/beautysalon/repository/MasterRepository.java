package com.beautysalon.repository;
import java.util.Optional;

import com.beautysalon.model.Master;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

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
