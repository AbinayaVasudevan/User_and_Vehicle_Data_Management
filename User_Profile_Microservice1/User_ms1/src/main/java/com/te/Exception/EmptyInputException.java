package com.te.Exception;

public class EmptyInputException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    /**
     * String errorCode.
     */
    private  String errorCode;
    /**
     * String errorMessage.
     */
    private String errorMessage;

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
     * Setter method fr ErrorMessage.
     * @param errorMessage
     */
    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Getter method for SerialVersionUID.
     * @return serialVersionUID
     */
    public static long getSerialVersionUID() {
        return  serialVersionUID;
    }

    /**
     * Constructor for EmptyInputException.
     * @param errorCode
     * @param errorMessage
     */
    public EmptyInputException(
            final String errorCode,
            final String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * Empty constructor.
     */
    public EmptyInputException() {
    	
    }
}
