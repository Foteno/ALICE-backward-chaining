package lt.vu.kursinis.back.repository;

import lt.vu.kursinis.models.Fact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactRepository extends MongoRepository<Fact, String> {
    Fact findByError(String error);
    List<Fact> findByErrorIn(List<String> errors);
}
