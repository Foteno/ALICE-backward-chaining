package lt.vu.kursinis.back.repository;

import lt.vu.kursinis.models.Component;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentRepository extends MongoRepository<Component, String> {
    Component findByName(String name);
}
