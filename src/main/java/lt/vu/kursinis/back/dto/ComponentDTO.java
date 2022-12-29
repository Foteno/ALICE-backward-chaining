package lt.vu.kursinis.back.dto;

import lombok.Builder;
import lombok.Data;
import lt.vu.kursinis.models.Component;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ComponentDTO {
    private String id;
    private String name;
    private List<RuleDTO> ruleList;
    private List<ComponentDTO> subcomponents;

    public static ComponentDTO from(Component component) {
        return ComponentDTO.builder()
                .id(component.getId())
                .name(component.getName())
                .ruleList(component.getRuleList().stream().map(RuleDTO::from).collect(Collectors.toList()))
                .subcomponents(component.getSubcomponents().stream().map(ComponentDTO::from).collect(Collectors.toList()))
                .build();
    }
}
