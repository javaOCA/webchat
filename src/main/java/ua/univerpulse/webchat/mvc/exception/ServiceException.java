package ua.univerpulse.webchat.mvc.exception;

public class ServiceException extends RuntimeException{

    public ServiceException(String message) {
        super(message);
    }

}