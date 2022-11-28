package lt.vu.kursinis.back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import lt.vu.kursinis.back.service.IBackwardChainingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CommonsLog
public class WebServisuController {

    private final IBackwardChainingService backwardChainingService;

    @GetMapping("/")
    public void getAnswer() {

    }
}
