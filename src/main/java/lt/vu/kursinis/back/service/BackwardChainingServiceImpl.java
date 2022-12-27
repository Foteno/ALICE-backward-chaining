package lt.vu.kursinis.back.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import lt.vu.kursinis.back.repository.ComponentRepository;
import lt.vu.kursinis.back.repository.FactRepository;
import lt.vu.kursinis.back.repository.RuleRepository;
import lt.vu.kursinis.models.Fact;
import lt.vu.kursinis.models.Rule;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@CommonsLog
@RequiredArgsConstructor
public class BackwardChainingServiceImpl implements BackwardChainingService {

    private final RuleRepository ruleRepository;
    private final FactRepository factRepository;
    private final ComponentRepository componentRepository;
    private final JsonParseInitialization jsonParseInitialization;

    private List<Fact> initialFacts = new ArrayList<>();

    public void createDocuments() {
        jsonParseInitialization.instantiateComponentFromJson();
    }

    public void getAnswer() {
        Fact consequent = factRepository.findById("639f382f45bda7688aa973c5").orElseThrow();

        chainButBackwards(new ArrayList<>(List.of(factRepository.findById("639f383245bda7688aa973c7").orElseThrow())), consequent);
    }

    private void chainButBackwards(List<Fact> facts, Fact goal) {
        List<Rule> rulesList = ruleRepository.findAll();

        initialFacts = new ArrayList<>(facts);

        List<Fact> currentGaols = new ArrayList<>();
        System.out.println(chainButBackwards(rulesList, facts, goal, currentGaols));
    }

    private boolean chainButBackwards (List<Rule> ruleList, List<Fact> facts, Fact goal, List<Fact> currentGoals) {
        if (facts.contains(goal)) {
            return true;
        }
        if (currentGoals.contains(goal)) {
            return false;
        }

        for (Rule rule: ruleList) {
            if (goal.equals(rule.getConsequent())) {
                boolean allTrue = true;
                List<Fact> tempGoals = new ArrayList<>(rule.getAntecedents());
                List<Fact> newCurrentGoals = new ArrayList<>(currentGoals);

                newCurrentGoals.add(goal);
                for (Fact subGoal: tempGoals) {
                    if (!chainButBackwards(ruleList, facts, subGoal, newCurrentGoals)) {
                        allTrue = false;
                    }
                }
                if (allTrue) {
                    facts.add(goal);
                    return true;
                } else {
                    facts.removeIf(f -> !initialFacts.contains(f));
                }
            }
        }
        return false;
    }
}
