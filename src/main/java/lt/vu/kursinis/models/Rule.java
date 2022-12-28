package lt.vu.kursinis.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "Rules")
public class Rule {

    @Id
    String id;

    List<Fact> antecedents = new ArrayList<>();
    Fact consequent;
}
