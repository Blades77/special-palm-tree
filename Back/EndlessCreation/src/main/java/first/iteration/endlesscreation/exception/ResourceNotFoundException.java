package first.iteration.endlesscreation.exception;

import java.util.ArrayList;
import java.util.List;

public class ResourceNotFoundException extends RuntimeException{

    private List<String> errors = new ArrayList<>();

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
