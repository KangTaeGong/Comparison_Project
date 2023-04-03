package project.reviews.exception;

/*
* 2023-01-12
* 회원가입 실패시 예외
* */
public class JoinFailException extends RuntimeException{

    public JoinFailException() {
        super();
    }

    public JoinFailException(String message) {
        super(message);
    }

    public JoinFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public JoinFailException(Throwable cause) {
        super(cause);
    }

    protected JoinFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
