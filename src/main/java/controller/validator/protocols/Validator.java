package controller.validator.protocols;

import controller.validator.ValidationResult;

public interface Validator {
	public static final String BLANK_ERROR = "This field is required";
    ValidationResult validate(String input);
}
