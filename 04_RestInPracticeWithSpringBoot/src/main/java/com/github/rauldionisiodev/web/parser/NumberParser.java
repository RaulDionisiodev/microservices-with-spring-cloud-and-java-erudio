package com.github.rauldionisiodev.web.parser;

import com.github.rauldionisiodev.exception.UnsupportedMathOperationException;
import org.springframework.stereotype.Component;

@Component
public class NumberParser {

    public Double convertToDouble(String strNumber) {
        if (strNumber == null || strNumber.isEmpty()) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }

        String number = strNumber.replace(",", ".");
        return Double.parseDouble(number);
    }
}
