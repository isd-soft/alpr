package isd.alprserver.model.exceptions;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(String s) {
        super(s);
    }
}
