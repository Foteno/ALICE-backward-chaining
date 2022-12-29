package lt.vu.kursinis.back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import lt.vu.kursinis.back.dto.AnswerDTO;
import lt.vu.kursinis.back.dto.ComponentDTO;
import lt.vu.kursinis.back.service.BackwardChainingService;
import lt.vu.kursinis.back.service.ComponentService;
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
public class WebServisuController {

    private final BackwardChainingService backwardChainingService;
    private final ComponentService componentService;

    @GetMapping("/answer")
    public List<String> getAnswer(@RequestBody AnswerDTO answerDTO) {
        return backwardChainingService.getAnswer(answerDTO);
    }

    @GetMapping("/components")
    public ComponentDTO getComponents() {
        return componentService.getComponentsTree();
    }
}
