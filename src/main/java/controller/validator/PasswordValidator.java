package controller.validator;

import java.util.regex.Pattern;

import controller.validator.protocols.Validator;

public class PasswordValidator implements Validator{

    private static final Pattern BOTH_LETTER_CASE =
      Pattern.compile(".*(([A-Z].*[a-z])|([a-z].*[A-Z])).*");
    private static final Pattern HAS_DIGIT = Pattern.compile(".*\\d.*");
    private static final Pattern HAS_SPECIAL =
      Pattern.compile(".*[!@#\\$%\\^&\\*].*");
	
	private static final String SPECIALS = "!@#$%^&*";
	
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_PASSWORD_LENGTH = 32;
	
	@Override
	public ValidationResult validate(String password) {
        boolean valid = true;

        if (password.isEmpty()) {
        	return new ValidationResult(false, BLANK_ERROR);
        }

        StringBuilder error = new StringBuilder("Must ");

        if (password.length() < MIN_PASSWORD_LENGTH ||
                password.length() > MAX_PASSWORD_LENGTH) {
            error.append(
                    "be " + MIN_PASSWORD_LENGTH +
                    "-" + MAX_PASSWORD_LENGTH + " characters");
            valid = false;
        }
        if (!BOTH_LETTER_CASE.matcher(password).matches() ||
            !HAS_DIGIT.matcher(password).matches() ||
            !HAS_SPECIAL.matcher(password).matches()) {
            if (!valid) {
                error.append(" and ");
            }
            valid = false;
            error.append(
                "contain an uppercase letter, a lowercase letter, a digit, and one of ");
            error.append(SPECIALS);
            
        }
        if (!valid) {
        	return new ValidationResult(false, error.toString());
        }
        return new ValidationResult(true);
	}
}
