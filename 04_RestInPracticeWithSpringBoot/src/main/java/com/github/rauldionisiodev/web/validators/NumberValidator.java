package com.github.rauldionisiodev.web.validators;

import com.github.rauldionisiodev.exception.UnsupportedMathOperationException;
import org.springframework.stereotype.Component;

@Component
public class NumberValidator {

    private boolean isNotNumeric(String strNumber) {
        if (strNumber == null || strNumber.isBlank()) {
            return true;
        }

        String number = strNumber.trim().replace(",", ".");

        return !number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

    public void validateNumbers(String numberOne, String numberTwo) {
        if (isNotNumeric(numberOne) || isNotNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
    }

    public void validateNumber(String numberOne) {
        if (isNotNumeric(numberOne)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
    }
}
