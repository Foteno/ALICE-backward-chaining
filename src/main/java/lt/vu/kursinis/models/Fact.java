package lt.vu.kursinis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Data
@Document(collection = "Facts")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fact {

    @Id
    private String id;
    private String attribute;
    private String error;
    private String suggestion;
    private boolean flagged;
/*
    @Override
    public String toString() {
        return this.attribute;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fact fact = (Fact) o;
        return id.equals(fact.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attribute, flagged);
    }
}
