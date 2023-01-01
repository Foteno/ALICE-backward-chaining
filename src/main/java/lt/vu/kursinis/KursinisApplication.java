package lt.vu.kursinis;

import lt.vu.kursinis.back.SpringContext;
import lt.vu.kursinis.back.service.forwardChaining.ForwardChainingService;
import lt.vu.kursinis.back.service.jsonParseInitialization.JsonParseInitialization;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KursinisApplication {

    public static void main(String[] args) {
        SpringApplication.run(KursinisApplication.class, args);
        JsonParseInitialization jsonParseInitialization = SpringContext.getBean(JsonParseInitialization.class);
        jsonParseInitialization.instantiateComponentFromJson();

        ForwardChainingService forwardChainingService = SpringContext.getBean(ForwardChainingService.class);
        String[] flaggedFacts = {"DCS1"};
        forwardChainingService.setFlaggedFacts(flaggedFacts);
    }
}
