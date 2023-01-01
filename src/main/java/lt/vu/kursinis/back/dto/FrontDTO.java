package lt.vu.kursinis.back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class FrontDTO {
    private List<RuleDTO> rules;
    private List<FactDTO> facts;
}
