package goeuro.testdev.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Error codes for the application.
 * <p>
 * Created by Benoit on 21/05/2016.
 */
@AllArgsConstructor
public enum ErrorCode {

    /**
     * If an invalid number of parameter has been detected for the application.
     */
    INVALID_NUMBER_PARAMETER(100),
    /**
     * If the provided parameter is invalid.
     */
    INVALID_PARAMETER(101),
    /**
     * If an error occurs when contacting the endpoint api.
     */
    COMMUNICATION_ERROR(200),
    /**
     * If a file system error occurs when writing a file.
     */
    FILE_SYSTEM_ERROR(300);

    @Getter
    private int value;
}
