package lt.vu.kursinis.back.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import lt.vu.kursinis.back.dto.AnswerDTO;
import lt.vu.kursinis.back.repository.ComponentRepository;
import lt.vu.kursinis.back.repository.FactRepository;
import lt.vu.kursinis.back.repository.RuleRepository;
import lt.vu.kursinis.models.Component;
import lt.vu.kursinis.models.Fact;
import lt.vu.kursinis.models.Rule;
import org.springframework.data.mongodb.core.MongoTemplate;
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

    private List<Fact> initialFacts = new ArrayList<>();
    private final List<Fact> confirmedFacts = new ArrayList<>();// maybe map key = errorname, value = component name

    public List<String> getAnswer(AnswerDTO answerDTO) {
        Fact consequent = factRepository.findByError(answerDTO.getGoal());
        assert consequent != null;

        List<Fact> initialFacts = factRepository.findByErrorIn(answerDTO.getInitialFacts());
        assert initialFacts.size() > 0;

        return chainButBackwards(initialFacts, consequent);
    }

    private List<String> chainButBackwards(List<Fact> facts, Fact goal) {
        List<Rule> rulesList = ruleRepository.findAll();

        initialFacts = new ArrayList<>(facts);

        List<Fact> currentGoals = new ArrayList<>();
        boolean isChained = chainButBackwards(rulesList, facts, goal, currentGoals);

        List<Component> komponentai = componentRepository.findByConsequents(confirmedFacts.stream().toList());

        return komponentai.stream().map(Component::toString).collect(Collectors.toList());
    }

    private boolean chainButBackwards(List<Rule> ruleList, List<Fact> facts, Fact goal, List<Fact> currentGoals) {
        if (facts.contains(goal)) {
            confirmedFacts.add(goal);
            return true;
        }
        if (currentGoals.contains(goal)) {
            return false;
        }

        for (Rule rule: ruleList) {
            if (goal.equals(rule.getConsequent())) {
                boolean allTrue = true;
                List<Fact> tempGoals = new ArrayList<>(rule.getAntecedents());
                if (tempGoals.size() == 0) {
                    break;
                }
                List<Fact> newCurrentGoals = new ArrayList<>(currentGoals);

                newCurrentGoals.add(goal);
                for (Fact subGoal: tempGoals) {
                    if (!chainButBackwards(ruleList, facts, subGoal, newCurrentGoals)) {
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
