package lt.vu.kursinis;

import lt.vu.kursinis.back.SpringContext;
import lt.vu.kursinis.back.service.csvParseInitialization.CsvParseInitialization;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KursinisApplication {

    public static void main(String[] args) {
        SpringApplication.run(KursinisApplication.class, args);
        CsvParseInitialization csvParseInitialization = SpringContext.getBean(CsvParseInitialization.class);
        csvParseInitialization.instantiateComponentFromCsv();

    }
}
