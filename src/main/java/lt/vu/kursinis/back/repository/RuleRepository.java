package lt.vu.kursinis.back.repository;

import lt.vu.kursinis.models.Rule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends MongoRepository<Rule, String> {
}
