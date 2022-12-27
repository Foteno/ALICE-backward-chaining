package lt.vu.kursinis.back.service;

import org.springframework.validation.annotation.Validated;

@Validated
public interface JsonParseInitialization {
    void instantiateComponentFromJson();
}
