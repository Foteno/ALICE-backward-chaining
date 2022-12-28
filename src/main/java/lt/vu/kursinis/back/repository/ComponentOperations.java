package lt.vu.kursinis.back.repository;

import lt.vu.kursinis.models.Component;
import lt.vu.kursinis.models.Fact;

import java.util.List;

public interface ComponentOperations {
    List<Component> findByConsequents(List<Fact> consequents);
}
