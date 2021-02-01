package kr.co.gigatera.exception;

public class AlreadyExistingEmailException extends RuntimeException {
	public AlreadyExistingEmailException(String message) {
        super(message);
    }
}
