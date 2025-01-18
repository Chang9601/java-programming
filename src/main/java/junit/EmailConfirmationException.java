package junit;

public class EmailConfirmationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EmailConfirmationException(String message) {
		super(message);
	}
}