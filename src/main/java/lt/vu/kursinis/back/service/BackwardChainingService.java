package lt.vu.kursinis.back.service;

import lt.vu.kursinis.back.dto.AnswerDTO;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface BackwardChainingService {
    List<String> getAnswer(AnswerDTO answerDTO);
}
