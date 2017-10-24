package controller;

import controller.validator.NameValidator;
import controller.validator.protocols.Validator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NameValidatorTest {

    private static final String NO_NUMBERS_MSG = "Cannot contain numbers!";
    private static final String NO_SPECIALS_MSG = "Cannot contain special characters!";

    String input;
    Validator validator;

    @Before
    public void beforeClass() {
        validator = new NameValidator();
    }

    @Test
    public void testEmpty() {
        input = "";
        assertFalse(validator.validate(input).valid());
    }
    @Test
    public void testNotEmpty() {
        input = "abc";
        assertTrue(validator.validate(input).valid());
    }

    @Test
    public void testContainsNumbers() {
        input = "00000";
        assertFalse(validator.validate(input).valid());
        assertTrue(validator.validate(input).message().equals(NO_NUMBERS_MSG));
    }
    @Test
    public void testContainsNoNumbers() {
        input = "abcABC!!!";
        assertFalse(validator.validate(input).valid());
        assertFalse(validator.validate(input).message().equals(NO_NUMBERS_MSG));
    }

    @Test
    public void testContainsSpecial() {
        input = "*/%&";
        assertFalse(validator.validate(input).valid());
        assertTrue(validator.validate(input).message().equals(NO_SPECIALS_MSG));
    }
    @Test
    public void testContainsNoSpecial() {
        input = "abc123ABC";
        assertFalse(validator.validate(input).valid());
        assertFalse(validator.validate(input).message().equals(NO_SPECIALS_MSG));
    }
}