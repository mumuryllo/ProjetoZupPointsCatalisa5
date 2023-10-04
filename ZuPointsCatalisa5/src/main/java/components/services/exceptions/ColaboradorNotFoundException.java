package components.services.exceptions;

public class ColaboradorNotFoundException extends RuntimeException{
    public ColaboradorNotFoundException(String message) {
        super(message);
    }
}
