package lt.vu.kursinis.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Data
@Document(collection = "Facts")
@NoArgsConstructor
public class Fact {

    @Id
    String id;
    String error;

    public Fact(String fact) {
        this.error = fact;
    }

    @Override
    public String toString() {
        return this.error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fact fact = (Fact) o;
        return error.equals(fact.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, error);
    }
}
