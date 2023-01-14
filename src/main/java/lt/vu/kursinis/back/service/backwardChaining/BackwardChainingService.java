package lt.vu.kursinis.back.service.backwardChaining;

import lt.vu.kursinis.back.dto.ErrorSuggestionDTO;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface BackwardChainingService {
    ErrorSuggestionDTO analyseProblems(List<String> initialFacts);
}
