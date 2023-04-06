package project.reviews.exception;

/*
* 2022-09-30
* About PostingException
* */
public class PostingNotFoundException extends RuntimeException{

    public PostingNotFoundException(String message) {
        super(message);
    }

    public PostingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostingNotFoundException(Throwable cause) {
        super(cause);
    }

    protected PostingNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PostingNotFoundException() {

    }
}
