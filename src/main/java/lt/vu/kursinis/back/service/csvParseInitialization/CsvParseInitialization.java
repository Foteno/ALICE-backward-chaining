package lt.vu.kursinis.back.service.csvParseInitialization;

import org.springframework.validation.annotation.Validated;

@Validated
public interface CsvParseInitialization {
    void instantiateComponentFromCsv();
}
