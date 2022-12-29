package lt.vu.kursinis.back.dto;

import lombok.Builder;
import lombok.Data;
import lt.vu.kursinis.models.Fact;
import lt.vu.kursinis.models.Rule;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class RuleDTO {
    private String id;
    private String consequent;
    private List<String> antecedents;

    public static RuleDTO from(Rule rule) {
        return RuleDTO.builder()
                .id(rule.getId())
                .consequent(rule.getConsequent().getError())
                .antecedents(rule.getAntecedents().stream()
                        .map(Fact::getError)
                        .collect(Collectors.toList()))
                .build();
    }
}
