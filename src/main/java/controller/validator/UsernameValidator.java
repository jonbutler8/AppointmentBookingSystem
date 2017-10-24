package controller.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.validator.protocols.Validator;

public class UsernameValidator implements Validator {

    private static final Pattern USERNAME_PATTERN =
    	      Pattern.compile("^[A-Za-z][\\w]*");
    private static final int MIN_USERNAME_LENGTH = 4;
    private static final int MAX_USERNAME_LENGTH = 15;
    private static final String USERNAME_ERROR = "Enter " 
								+ MIN_USERNAME_LENGTH + "-" 
								+ MAX_USERNAME_LENGTH + 
								" letters and numbers, starting with a letter";
	
	@Override
	public ValidationResult validate(String username) {
        if (username.isEmpty()) {
            return new ValidationResult(false, BLANK_ERROR);
        }
        if (username.length() < MIN_USERNAME_LENGTH || 
        		username.length() > MAX_USERNAME_LENGTH ||
        		!USERNAME_PATTERN.matcher(username).matches()){
        	return new ValidationResult(false, USERNAME_ERROR);
        }
        return new ValidationResult(true);
	}
}
