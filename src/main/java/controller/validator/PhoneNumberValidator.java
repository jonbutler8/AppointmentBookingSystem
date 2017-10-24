package controller.validator;

import java.util.regex.Pattern;

import controller.validator.protocols.Validator;

public class PhoneNumberValidator implements Validator {

	private static final Pattern PHONE_PATTERN = Pattern.compile("^0\\d{9}$");
	
	@Override
	public ValidationResult validate(String phoneNumber) {
		
        if (phoneNumber.isEmpty()) {
            return new ValidationResult(false, BLANK_ERROR);
        }
        
        if (!PHONE_PATTERN.matcher(phoneNumber).matches()) {
            return new ValidationResult(false, 
            		"Must be a two digit area code (0X) followed by 8 digits");
        }
        return new ValidationResult(true);
	}
}
