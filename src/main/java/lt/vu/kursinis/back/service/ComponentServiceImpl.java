package lt.vu.kursinis.back.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import lt.vu.kursinis.back.dto.ComponentDTO;
import lt.vu.kursinis.back.repository.ComponentRepository;
import lt.vu.kursinis.models.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CommonsLog
@RequiredArgsConstructor
public class ComponentServiceImpl implements ComponentService {

    private final ComponentRepository componentRepository;

    public ComponentDTO getComponentsTree() {
        List<Component> allComponents = componentRepository.findAll();
        Component rootAssembly = allComponents.stream()
                .filter(component -> component.getComponentsPartOf().size() == 0)
                .findFirst()
                .orElseThrow();

        return ComponentDTO.from(rootAssembly);
    }
}
