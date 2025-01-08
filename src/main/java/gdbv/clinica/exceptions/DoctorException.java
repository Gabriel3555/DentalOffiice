package gdbv.clinica.exceptions;

public class DoctorException extends Exception {
    public DoctorException(String message) {
        super(message);
    }

    public DoctorException(String message, Throwable cause) {
        super(message, cause);
    }
}