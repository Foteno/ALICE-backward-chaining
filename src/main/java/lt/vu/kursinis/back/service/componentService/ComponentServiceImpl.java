package lt.vu.kursinis.back.service.componentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import lt.vu.kursinis.back.dto.FactDTO;
import lt.vu.kursinis.back.dto.FrontDTO;
import lt.vu.kursinis.back.dto.RuleDTO;
import lt.vu.kursinis.back.repository.ComponentRepository;
import lt.vu.kursinis.back.repository.FactRepository;
import lt.vu.kursinis.back.repository.RuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@CommonsLog
@RequiredArgsConstructor
public class ComponentServiceImpl implements ComponentService {

    private final ComponentRepository componentRepository;
    private final RuleRepository ruleRepository;
    private final FactRepository factRepository;

    public FrontDTO getComponentsTree() {
        List<RuleDTO> rules = ruleRepository.findAll().stream()
                .map(RuleDTO::from)
                .collect(Collectors.toList());

        List<FactDTO> facts = factRepository.findAll().stream()
                .map(FactDTO::from)
                .collect(Collectors.toList());

        return new FrontDTO(rules, facts);
    }
}
