package site.easy.to.build.crm.csv;

import java.util.Collections;
import java.util.List;

public class CSVProcessingException extends RuntimeException {
    private final List<String> errors;

    public CSVProcessingException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}