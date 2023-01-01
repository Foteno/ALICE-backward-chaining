package lt.vu.kursinis.back.repository;

import lombok.RequiredArgsConstructor;
import lt.vu.kursinis.models.Component;
import lt.vu.kursinis.models.Fact;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComponentRepositoryImpl implements ComponentOperations {
    private final MongoTemplate mongoTemplate;

    @Override
    public List<Component> findByFactAttributes(List<Fact> attributes) {
        return mongoTemplate.find(new Query(Criteria.where("componentAttributes").in(attributes)), Component.class);
    }
}
