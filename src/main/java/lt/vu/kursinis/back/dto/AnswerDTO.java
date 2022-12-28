package lt.vu.kursinis.back.dto;

import lombok.Data;

import java.util.List;

@Data
public class AnswerDTO {
    private String goal;
    private List<String> initialFacts;
}
