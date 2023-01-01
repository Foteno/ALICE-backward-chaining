package lt.vu.kursinis.back.service.forwardChaining;

import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import lt.vu.kursinis.back.repository.FactRepository;
import lt.vu.kursinis.back.repository.RuleRepository;
import lt.vu.kursinis.models.Fact;
import lt.vu.kursinis.models.forwardChainingRuleWrapper.RuleWrapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CommonsLog
@RequiredArgsConstructor
public class ForwardChainingServiceImpl implements ForwardChainingService {

    private final RuleRepository ruleRepository;
    private final FactRepository factRepository;

    public void setFlaggedFacts(String[] flaggedFacts) {
        List<Fact> initialFlaggedFacts = factRepository.findAllById(List.of(flaggedFacts));
        initialFlaggedFacts.forEach(fact -> fact.setFlagged(true));
        factRepository.saveAll(initialFlaggedFacts);

        forwardChaining(initialFlaggedFacts);
    }

    private void forwardChaining(List<Fact> facts) {
        List<RuleWrapper> rulesList = ruleRepository.findAll().stream()
                .map(RuleWrapper::new)
                .collect(Collectors.toList());

        forwardChaining(rulesList, facts);
    }

    private void forwardChaining(List<RuleWrapper> ruleList, List<Fact> facts) {
        Fact ruleConsequent;
        for (RuleWrapper rule : ruleList) {
            if (rule.isFlagged()) {
                continue;
            }

            ruleConsequent = rule.getRule().getConsequent();
            if (facts.contains(ruleConsequent)) {
                rule.setFlagged(true);
            } else if (rule.getRule().getAntecedents().stream().anyMatch(Fact::isFlagged)) {
                //find the consequent, that is antecedent in other rules and set it to flagged as well
                ruleList.stream()
                        .map(ruleWrapper -> ruleWrapper.getRule().getAntecedents())
                        .flatMap(Collection::stream)
                        .filter(ruleConsequent::equals)
                        .forEach(fact -> fact.setFlagged(true));

                ruleConsequent.setFlagged(true);
                factRepository.save(ruleConsequent);

                facts.add(ruleConsequent);
                rule.setFlagged(true);
                forwardChaining(ruleList, facts);
            }
        }
    }
}
