package lt.vu.kursinis.back.service.jsonParseInitialization;

import org.springframework.validation.annotation.Validated;

@Validated
public interface JsonParseInitialization {
    void instantiateComponentFromJson();
}
