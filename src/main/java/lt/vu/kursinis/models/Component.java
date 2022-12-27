package lt.vu.kursinis.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "Components")
public class Component {

    @Id
    String id;
    String name;

    @DBRef(lazy = true)
    List<Component> componentsPartOf = new ArrayList<>();

    @DBRef(lazy = true)
    List<Component> subcomponents = new ArrayList<>();

    @DBRef(lazy = true)
    List<Rule> ruleList = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder("ID: " + id + ", Name: " + name + ", ComponentsPartOf: [");

        for (int i = 0; i < componentsPartOf.size(); i++) {
            temp.append(componentsPartOf.get(i).getName());
            if (i != componentsPartOf.size() - 1) {
                temp.append(", ");
            }
        }
        temp.append("], Subcomponents: [");

        for (int i = 0; i < subcomponents.size(); i++) {
            temp.append(subcomponents.get(i).getName());
            if (i != subcomponents.size() - 1) {
                temp.append(", ");
            }
        }
        temp.append("], Rules: [");

        for (int i = 0; i < ruleList.size(); i++) {
            temp.append(ruleList.get(i).getId());
            if (i != ruleList.size() - 1) {
                temp.append(", ");
            }
        }
        temp.append("]");
        return temp.toString();
    }
}
