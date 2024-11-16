package com.te.Exception;

public class NoSuchElementException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    /**
     * String errorCode.
     */
    private String errorCode;
    /**
     * String errorMessage.
     */
    private String errorMessage;

    /**
     * Empty constructor.
     */
    public NoSuchElementException() {
    }

    /**
     * Getter method for ErrorCode.
     * @return errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Setter method for ErrorCode.
     * @param errorCode
     */
    public void setErrorCode(final String errorCode) {

        this.errorCode = errorCode;
    }

    /**
     * Getter method for ErrorMessage.
     * @return errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Setter method for ErrorMessage.
     * @param errorMessage
     */
    public void setErrorMessage(final String errorMessage) {

        this.errorMessage = errorMessage;
    }

    /**
     * Constructor for NoSuchElementException.
     * @param errorCode
     * @param errorMessage
     */
    public NoSuchElementException(
            final String errorCode,
            final String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
