package components.services.exceptions;

public class BeneficioNotFoundException extends RuntimeException{
    public BeneficioNotFoundException(String message) {
        super(message);
    }
}
