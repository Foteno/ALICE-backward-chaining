package lt.vu.kursinis.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "Components")
public class Component {

    @Id
    String id;
    String name;
    List<Fact> componentAttributes = new ArrayList<>();

    public Component(String name) {
        this.name = name;
    }
}
