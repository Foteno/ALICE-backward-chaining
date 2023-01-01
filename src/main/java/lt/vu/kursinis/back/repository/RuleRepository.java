package lt.vu.kursinis.back.repository;

import lt.vu.kursinis.models.Fact;
import lt.vu.kursinis.models.Rule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleRepository extends MongoRepository<Rule, String> {
    List<Rule> findByConsequent(Fact consequent);

    List<Rule> findByAntecedents(Fact antecedent);
}
