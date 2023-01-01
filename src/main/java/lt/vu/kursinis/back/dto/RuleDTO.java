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
    private String consequentId;
    private List<String> antecedentsId;

    public static RuleDTO from(Rule rule) {
        return RuleDTO.builder()
                .id(rule.getId())
                .consequentId(rule.getConsequent().getId())
                .antecedentsId(rule.getAntecedents().stream()
                        .map(Fact::getId)
                        .collect(Collectors.toList()))
                .build();
    }
}
