package goeuro.testdev.exceptions;

import lombok.Getter;

/**
 * Class managing the exceptions for the application
 * <p>
 * Created by Benoit on 21/05/2016.
 */
public class ApplicationException extends Exception {

    @Getter
    private ErrorCode errorCode;
    @Getter
    private String reason;

    /**
     * Constructor.
     *
     * @param errorCode Error code of the exception
     * @param reason Message about the exception
     */
    public ApplicationException(ErrorCode errorCode, String reason) {
        this.errorCode = errorCode;
        this.reason = reason;
    }
}
