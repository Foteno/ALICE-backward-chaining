package lt.vu.kursinis.back.service;

import org.springframework.validation.annotation.Validated;

@Validated
public interface BackwardChainingService {
    void createDocuments();
    void getAnswer();
}
