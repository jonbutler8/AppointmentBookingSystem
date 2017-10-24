package controller.validator;

import controller.validator.protocols.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidator implements Validator {

    private static final Logger logger =
      LogManager.getLogger(NameValidator.class);

    private static final String NUMBERS = ".*[0-9].*";
    private static final Pattern PATTERN = Pattern.compile("[^a-z 0-9]", Pattern.CASE_INSENSITIVE);

    @Override
    public ValidationResult validate(String name) {

        Matcher match = PATTERN.matcher(name);
        boolean b = match.find();

        if (name.isEmpty()){
            //logger.info("Field is Empty");
            return new ValidationResult(false, BLANK_ERROR);
        }
        else if (name.matches(NUMBERS)){
            //logger.info("Field has numbers");
            return new ValidationResult(false, "Cannot contain numbers!");
        }
        else if (b){
            //logger.info("Field has special char");
            return new ValidationResult(false, "Cannot contain special characters!");
        }
        else {
            return new ValidationResult(true);
        }

    }
}
