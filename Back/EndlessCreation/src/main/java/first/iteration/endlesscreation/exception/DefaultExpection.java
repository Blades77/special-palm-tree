package first.iteration.endlesscreation.exception;

public class DefaultExpection extends  RuntimeException{

    public DefaultExpection(String message){
        super(message);
    }

    public DefaultExpection(String message, Throwable cause){
        super(message, cause);
    }
}
