package lt.vu.kursinis.back.service;

import lt.vu.kursinis.back.dto.ComponentDTO;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ComponentService {
    ComponentDTO getComponentsTree();
}
