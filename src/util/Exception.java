package util;

public class Exception extends RuntimeException {

    // Forskellige exception methods, som kalder super hvilket er RuntimeException, som kalder på dens super Exception

    public Exception() {
    }

    public Exception(String message) {
        super(message);
    }

    public Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public Exception(Throwable cause) {
        super(cause);
    }
}
