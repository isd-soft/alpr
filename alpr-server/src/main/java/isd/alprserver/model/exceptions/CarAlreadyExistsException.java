package isd.alprserver.model.exceptions;

public class CarAlreadyExistsException extends Exception {

    public CarAlreadyExistsException() {
    }

    public CarAlreadyExistsException(String message) {
        super(message);
    }
}
