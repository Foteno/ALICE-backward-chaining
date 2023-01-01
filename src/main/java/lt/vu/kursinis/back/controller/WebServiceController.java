package lt.vu.kursinis.back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import lt.vu.kursinis.back.dto.AnswerDTO;
import lt.vu.kursinis.back.dto.FrontDTO;
import lt.vu.kursinis.back.service.backwardChaining.BackwardChainingService;
import lt.vu.kursinis.back.service.componentService.ComponentService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/answer")
    public List<String> analyseProblems(@RequestBody AnswerDTO answerDTO) {
        return backwardChainingService.analyseProblems(answerDTO);
    }

    @GetMapping("/components")
    public FrontDTO getComponents() {
        return componentService.getComponentsTree();
    }
}
