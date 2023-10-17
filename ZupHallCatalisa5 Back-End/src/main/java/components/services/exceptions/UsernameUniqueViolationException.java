package components.services.exceptions;

public class UsernameUniqueViolationException extends RuntimeException{

    public UsernameUniqueViolationException(String message, String username){
        super(message);
    }

}
