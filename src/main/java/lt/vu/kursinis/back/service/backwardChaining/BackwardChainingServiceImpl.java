package lt.vu.kursinis.back.service.backwardChaining;

import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import lt.vu.kursinis.back.dto.AnswerDTO;
import lt.vu.kursinis.back.repository.ComponentRepository;
import lt.vu.kursinis.back.repository.FactRepository;
import lt.vu.kursinis.back.repository.RuleRepository;
import lt.vu.kursinis.models.Component;
import lt.vu.kursinis.models.Fact;
import lt.vu.kursinis.models.Rule;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@CommonsLog
@RequiredArgsConstructor
public class BackwardChainingServiceImpl implements BackwardChainingService {

    private final RuleRepository ruleRepository;
    private final FactRepository factRepository;
    private final ComponentRepository componentRepository;
    private final Set<Fact> confirmedFacts = new HashSet<>();// maybe map key = errorname, value = component name
    private List<Fact> initialFacts = new ArrayList<>();

    public List<String> analyseProblems(AnswerDTO answerDTO) {
        Fact consequent = factRepository.findById(answerDTO.getGoal()).orElseThrow();
        List<Fact> initialFacts = factRepository.findAllById(answerDTO.getInitialFacts());

        return backwardChaining(initialFacts, consequent);
    }

    private List<String> backwardChaining(List<Fact> facts, Fact goal) {
        List<Rule> rulesList = ruleRepository.findAll();

        initialFacts = new ArrayList<>(facts);

        List<Fact> currentGoals = new ArrayList<>();
        boolean isChained = backwardChaining(rulesList, facts, goal, currentGoals);

        List<Component> komponentai = componentRepository.findByFactAttributes(confirmedFacts.stream().toList());

        return komponentai.stream().map(Component::toString).collect(Collectors.toList());
    }

    private boolean backwardChaining(List<Rule> ruleList, List<Fact> facts, Fact goal, List<Fact> currentGoals) {
        if (facts.contains(goal)) {
            confirmedFacts.add(goal);
            return true;
        }
        if (currentGoals.contains(goal)) {
            return false;
        }

        for (Rule rule : ruleList) {
            if (goal.equals(rule.getConsequent())) {
                boolean allTrue = true;
                List<Fact> tempGoals = new ArrayList<>(rule.getAntecedents());
                if (tempGoals.size() == 0) {
                    break;
                }
                List<Fact> newCurrentGoals = new ArrayList<>(currentGoals);

                newCurrentGoals.add(goal);
                for (Fact subGoal : tempGoals) {
                    if (!backwardChaining(ruleList, facts, subGoal, newCurrentGoals)) {
                        allTrue = false;
                    }
                }


                if (allTrue) {
                    facts.add(goal);
                    confirmedFacts.add(goal);
                    return true;
                } else {
                    facts.removeIf(f -> !initialFacts.contains(f));
                }
            }
        }
        return false;
    }
}
