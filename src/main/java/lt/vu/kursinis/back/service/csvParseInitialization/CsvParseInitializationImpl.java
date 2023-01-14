package lt.vu.kursinis.back.service.csvParseInitialization;

import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import lt.vu.kursinis.back.repository.ComponentRepository;
import lt.vu.kursinis.back.repository.FactRepository;
import lt.vu.kursinis.back.repository.RuleRepository;
import lt.vu.kursinis.models.Component;
import lt.vu.kursinis.models.Fact;
import lt.vu.kursinis.models.Rule;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Service
@CommonsLog
@RequiredArgsConstructor
public class CsvParseInitializationImpl implements CsvParseInitialization {

    private final RuleRepository ruleRepository;
    private final FactRepository factRepository;
    private final ComponentRepository componentRepository;

    public void instantiateComponentFromCsv() {
        ruleRepository.deleteAll();
        factRepository.deleteAll();
        componentRepository.deleteAll();
        readComponents();
        readRules();
    }

    private void readRules() {
        try {
            File myObj = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("initial/Rules.csv")).toURI().getPath());
            Scanner myReader = new Scanner(myObj);

            myReader.nextLine();//ignore first line

            List<Fact> antecedents;
            Fact consequent;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] eilute = data.split(",");
                if (eilute[4].equals("")) {
                    continue;
                }
                consequent = factRepository.findById(eilute[4]).orElseThrow();
                antecedents = new ArrayList<>();
                if (!eilute[1].equals("")) {
                    antecedents.add(factRepository.findById(eilute[1]).orElseThrow());
                }
                if (!eilute[2].equals("")) {
                    antecedents.add(factRepository.findById(eilute[2]).orElseThrow());
                }
                if (!eilute[3].equals("")) {
                    antecedents.add(factRepository.findById(eilute[3]).orElseThrow());
                }

                Rule rule = new Rule();
                rule.setConsequent(consequent);
                rule.setAntecedents(antecedents);
                ruleRepository.save(rule);
            }
            myReader.close();
        } catch (FileNotFoundException | URISyntaxException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void readComponents() {
        try {
            File myObj = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("initial/Component_facts.csv")).toURI().getPath());
            Scanner myReader = new Scanner(myObj);

            myReader.nextLine();//ignore first line

            Component component = null;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] eilute = data.split(",");
                if (!eilute[0].equals("")) {
                    if (component != null) {
                        componentRepository.save(component);
                    }
                    component = new Component(eilute[0]);
                }
                Fact newFact = Fact.builder()
                        .id(eilute[1])
                        .attribute(eilute[2])
                        .error(eilute[3])
                        .suggestion(eilute[4])
                        .build();
                assert component != null;
                factRepository.save(newFact);
                component.getComponentAttributes().add(newFact);

            }
            myReader.close();
        } catch (FileNotFoundException | URISyntaxException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
