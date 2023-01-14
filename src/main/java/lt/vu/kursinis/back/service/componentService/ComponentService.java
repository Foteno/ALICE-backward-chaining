package lt.vu.kursinis.back.service.componentService;

import lt.vu.kursinis.back.dto.FactDTO;
import lt.vu.kursinis.back.dto.FrontDTO;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface ComponentService {
    FrontDTO getComponentsTree();

    List<FactDTO> getFacts();
}
