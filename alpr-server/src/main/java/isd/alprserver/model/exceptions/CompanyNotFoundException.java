package isd.alprserver.model.exceptions;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(String s) {
        super(s);
    }
}
