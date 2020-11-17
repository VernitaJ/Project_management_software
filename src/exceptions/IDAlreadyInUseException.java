package exceptions;

public class IDAlreadyInUseException extends RuntimeException{
    public IDAlreadyInUseException(){
        super("Oops, something went wrong. ID is already in use.");
    }
}

