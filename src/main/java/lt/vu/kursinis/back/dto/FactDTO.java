package lt.vu.kursinis.back.dto;

import lombok.Builder;
import lombok.Data;
import lt.vu.kursinis.models.Fact;

@Data
@Builder
public class FactDTO {
    private String id;
    private String attribute;

    public static FactDTO from(Fact fact) {
        return FactDTO.builder()
                .id(fact.getId())
                .attribute(fact.getAttribute())
                .build();
    }
}
