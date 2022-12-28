package lt.vu.kursinis.back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import lt.vu.kursinis.back.dto.AnswerDTO;
import lt.vu.kursinis.back.service.BackwardChainingService;
import lt.vu.kursinis.models.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
@CommonsLog
public class WebServisuController {

    private final BackwardChainingService backwardChainingService;

    @GetMapping("/answer")
    public List<String> getAnswer(@RequestBody AnswerDTO answerDTO) {
        return backwardChainingService.getAnswer(answerDTO);
    }
}
