package lt.vu.kursinis.back.service.backwardChaining;

import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import lt.vu.kursinis.back.dto.ErrorSuggestionDTO;
import lt.vu.kursinis.back.repository.ComponentRepository;
import lt.vu.kursinis.back.repository.FactRepository;
import lt.vu.kursinis.back.repository.RuleRepository;
import lt.vu.kursinis.models.Component;
import lt.vu.kursinis.models.Fact;
import lt.vu.kursinis.models.Rule;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CommonsLog
@RequiredArgsConstructor
public class BackwardChainingServiceImpl implements BackwardChainingService {

    private final RuleRepository ruleRepository;
    private final FactRepository factRepository;
    private final ComponentRepository componentRepository;
    private final List<Fact> trackedErrors = new ArrayList<>();

    public ErrorSuggestionDTO analyseProblems(List<String> initialFacts) {
        Fact consequent = factRepository.findById("ALICE1").orElseThrow();
        List<Fact> facts = factRepository.findAllById(initialFacts);

        return backwardChaining(facts, consequent);
    }

    private ErrorSuggestionDTO backwardChaining(List<Fact> facts, Fact goal) {
        List<Rule> rulesList = ruleRepository.findAll();

        trackedErrors.clear();
        backwardChaining(rulesList, facts, goal, false);
        if (trackedErrors.size() == 0) {
            return new ErrorSuggestionDTO();
        }
        List<Fact> trackedErrorsUnique = trackedErrors.stream()
                .distinct()
                .collect(Collectors.toList());

        List<Component> componentsFoundByError = componentRepository.findByFactAttributes(trackedErrorsUnique);

        List<ErrorSuggestionDTO.ErrorSuggestion> errorSuggestions = trackedErrorsUnique.stream()
                .map(error -> new ErrorSuggestionDTO.ErrorSuggestion(error.getId(), error.getError(), error.getSuggestion()))
                .collect(Collectors.toList());

        List<String> componentsForAnswer = componentsFoundByError.stream()
                .map(Component::getName)
                .collect(Collectors.toList());

        return new ErrorSuggestionDTO(errorSuggestions, componentsForAnswer);
    }

    private void backwardChaining(List<Rule> ruleList, List<Fact> facts, Fact goal, boolean shouldAdd) {
        for (Rule rule : ruleList) {
            if (rule.getConsequent().equals(goal)) {
                List<Fact> tempGoals = new ArrayList<>(rule.getAntecedents());

                for (Fact subGoal : tempGoals) {
                    if (facts.contains(subGoal) || shouldAdd) {
                        trackedErrors.add(subGoal);
                        backwardChaining(ruleList, facts, subGoal, true);
                        continue;
                    }
                    backwardChaining(ruleList, facts, subGoal, false);
                }
            }
        }
    }
}
