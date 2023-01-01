package lt.vu.kursinis.back.service.forwardChaining;

import org.springframework.validation.annotation.Validated;

@Validated
public interface ForwardChainingService {
    void setFlaggedFacts(String[] flaggedFacts);
}
