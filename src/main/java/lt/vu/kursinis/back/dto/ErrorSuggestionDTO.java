package lt.vu.kursinis.back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorSuggestionDTO {
    List<ErrorSuggestion> errorsSuggestions;
    List<String> components;

    @Data
    @AllArgsConstructor
    public static class ErrorSuggestion {
        String id;
        String error;
        String suggestion;
    }
}
