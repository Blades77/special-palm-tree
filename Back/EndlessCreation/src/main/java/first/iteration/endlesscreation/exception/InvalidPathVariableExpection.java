package first.iteration.endlesscreation.exception;

import java.util.ArrayList;
import java.util.List;

public class InvalidPathVariableExpection extends RuntimeException{

    private List<String> errors = new ArrayList<>();

    public InvalidPathVariableExpection(String message) {
        super(message);
    }

    public InvalidPathVariableExpection(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
