package online.regme.fms.loader.exception;

public class FmsNotFoundException extends  RuntimeException {
    public FmsNotFoundException(Throwable cause){
        super(cause);
    }

    public FmsNotFoundException(String message){
        super(message);
    }
}
