package lt.vu.kursinis.back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import lt.vu.kursinis.back.dto.ErrorSuggestionDTO;
import lt.vu.kursinis.back.dto.FactDTO;
import lt.vu.kursinis.back.dto.FrontDTO;
import lt.vu.kursinis.back.service.backwardChaining.BackwardChainingService;
import lt.vu.kursinis.back.service.componentService.ComponentService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
@CommonsLog
@CrossOrigin
public class WebServiceController {

    private final BackwardChainingService backwardChainingService;
    private final ComponentService componentService;

    @PostMapping("/analysis")
    public ErrorSuggestionDTO analyseProblems(@RequestBody List<String> initialFacts) {
        return backwardChainingService.analyseProblems(initialFacts);
    }

    @GetMapping("/factsConnected")
    public FrontDTO getFactsConnected() {
        return componentService.getComponentsTree();
    }

    @GetMapping("/facts")
    public List<FactDTO> getFactsCo() {
        return componentService.getFacts();
    }
}
