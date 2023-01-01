package lt.vu.kursinis.back.service.componentService;

import lt.vu.kursinis.back.dto.FrontDTO;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ComponentService {
    FrontDTO getComponentsTree();
}
