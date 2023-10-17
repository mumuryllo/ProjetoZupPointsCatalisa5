package components.services.exceptions;

public class CredencialUniqueViolationException extends RuntimeException{
    public CredencialUniqueViolationException(String message) {
        super(message);
    }
}
