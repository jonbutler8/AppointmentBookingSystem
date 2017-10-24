package controller.validator;

import controller.validator.protocols.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceNameValidator implements Validator {

    private static final Logger logger =
      LogManager.getLogger(ServiceNameValidator.class);

    private static final String NUMBERS = ".*[0-9].*";
    private static final Pattern PATTERN = Pattern.compile("[^a-z 0-9]", Pattern
      .CASE_INSENSITIVE);

    @Override
    public ValidationResult validate(String name) {

        Matcher match = PATTERN.matcher(name);
        boolean b = match.find();

        if (name.isEmpty()){
            return new ValidationResult(false, BLANK_ERROR);
        }
        else if (name.matches(NUMBERS)){
            return new ValidationResult(false, "Cannot contain numbers!");
        }
        else if (b){
            return new ValidationResult(false, "Cannot contain special characters!");
        }
        else if(name.length() > 50) {
            return new ValidationResult(false, "Service name too long!");
        }
        else {
            return new ValidationResult(true);
        }

    }
}
