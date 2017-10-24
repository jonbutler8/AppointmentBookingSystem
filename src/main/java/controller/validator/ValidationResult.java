package controller.validator;

public class ValidationResult {

    private boolean valid;
    private String message;

    ValidationResult(boolean valid) {
        this.valid = valid;
    }

    ValidationResult(boolean valid, String message){
        this.valid = valid;
        this.message = message;
    }

    public boolean valid() {
        return valid;
    }

    public String message() {
        return message;
    }
}
