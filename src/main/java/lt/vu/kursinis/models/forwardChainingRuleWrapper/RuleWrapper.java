package lt.vu.kursinis.models.forwardChainingRuleWrapper;

import lombok.Data;
import lt.vu.kursinis.models.Rule;

@Data
public class RuleWrapper {
    private Rule rule;
    private boolean flagged = false;

    public RuleWrapper(Rule rule) {
        this.rule = rule;
    }
}
