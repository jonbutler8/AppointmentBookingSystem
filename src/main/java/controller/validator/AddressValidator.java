package controller.validator;

import controller.validator.protocols.Validator;

public class AddressValidator implements Validator {

	@Override
	public ValidationResult validate(String address) {
		
		if (address.isEmpty()){
			return new ValidationResult(false, BLANK_ERROR);
		}
		return new ValidationResult(true);
	}

}
