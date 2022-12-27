package lt.vu.kursinis.back.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import lt.vu.kursinis.back.repository.ComponentRepository;
import lt.vu.kursinis.back.repository.FactRepository;
import lt.vu.kursinis.back.repository.RuleRepository;
import lt.vu.kursinis.models.Component;
import lt.vu.kursinis.models.Fact;
import lt.vu.kursinis.models.Rule;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@CommonsLog
@RequiredArgsConstructor
public class JsonParseInitializationImpl implements JsonParseInitialization {

    private final RuleRepository ruleRepository;
    private final FactRepository factRepository;
    private final ComponentRepository componentRepository;

    public void instantiateComponentFromJson() {
        try {
            URL resourceUrl = getClass().getClassLoader().getResource("json/ACORDE.json");
            assert resourceUrl != null;
            byte[] jsonData = Files.readAllBytes(Paths.get(resourceUrl.toURI()));
            ObjectMapper objectMapper = new ObjectMapper();
            Component component = objectMapper.readValue(jsonData, Component.class);

            instantiateFromRoot(component);

            System.out.println("gay");
        } catch (IOException | URISyntaxException e) {
            System.out.println("File not found GG");
        }
    }

    private void instantiateFromRoot(Component component) {
        List<Fact> antecedents = new ArrayList<>();
        List<Rule> rules = new ArrayList<>();
        for (Rule rule : component.getRuleList()) {
            for (Fact antecedent : rule.getAntecedents()) {
                antecedentFindOrWriteNew(antecedents, antecedent);
            }
            rule.getAntecedents().removeIf(ant -> ant.getId() == null);
            rule.getAntecedents().addAll(antecedents);

            consequentFindOrWriteNew(rule);

            antecedents.clear();
            ruleFindOrWriteNew(rules, rule);
        }
        component.getRuleList().removeIf(rule -> rule.getId() == null);
        component.getRuleList().addAll(rules);

        for (Component subcomponent : component.getSubcomponents()) {
            instantiateFromRoot(subcomponent);
        }
        componentFindOrWriteNew(component);
    }

    private void componentFindOrWriteNew(Component component) {
        Component componentExisting = componentRepository.findByName(component.getName());
        if (componentExisting == null) {
            component = componentRepository.save(component);
            for (Component savedSubcomponent : component.getSubcomponents()) {
                savedSubcomponent.getComponentsPartOf().add(component);
                componentRepository.save(savedSubcomponent);
            }
        } else {
            componentExisting.setComponentsPartOf(component.getComponentsPartOf());
            componentExisting.setRuleList(component.getRuleList());
            componentExisting.setSubcomponents(component.getSubcomponents());
        }
    }

    private void ruleFindOrWriteNew(List<Rule> rules, Rule rule) {
        List<Rule> existingRules = ruleRepository.findByConsequent(rule.getConsequent());
        Optional<Rule> existingRuleOptional = existingRules.stream()
                .filter(tempRule -> rule.getAntecedents().equals(tempRule.getAntecedents()))
                .findFirst();
        if (existingRuleOptional.isPresent()) {
            rules.add(existingRuleOptional.get());
        } else {
            ruleRepository.save(rule);
        }
    }

    private void consequentFindOrWriteNew(Rule rule) {
        Fact existingConsequent = factRepository.findByError(rule.getConsequent().getError());
        if (existingConsequent == null) {
            factRepository.save(rule.getConsequent());
        } else {
            rule.setConsequent(existingConsequent);
        }
    }

    private void antecedentFindOrWriteNew(List<Fact> antecedents, Fact antecedent) {
        Fact existingAntecedent = factRepository.findByError(antecedent.getError());
        if (existingAntecedent == null) {
            factRepository.save(antecedent);
        } else {
            antecedents.add(existingAntecedent);
        }
    }
}
