package com.github.rauldionisiodev.controllers;

import com.github.rauldionisiodev.exception.UnsupportedMathOperationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {

    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception {

        this.validateNumbers(numberOne, numberTwo);
        return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }

    @RequestMapping("/subtraction/{numberOne}/{numberTwo}")
    public Double subtraction(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception {

        this.validateNumbers(numberOne, numberTwo);
        return convertToDouble(numberOne) - convertToDouble(numberTwo);
    }

    @RequestMapping("/multiplication/{numberOne}/{numberTwo}")
    public Double multiplication(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception {

        this.validateNumbers(numberOne, numberTwo);
        return convertToDouble(numberOne) * convertToDouble(numberTwo);
    }

    @RequestMapping("/mean/{numberOne}/{numberTwo}")
    public Double mean(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception {

        this.validateNumbers(numberOne, numberTwo);
        return (convertToDouble(numberOne) + convertToDouble(numberTwo))/2;
    }

    @RequestMapping("/division/{numberOne}/{numberTwo}")
    public Double division(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception {

        this.validateNumbers(numberOne, numberTwo);
        return convertToDouble(numberOne) / convertToDouble(numberTwo);
    }

    @RequestMapping("/squareRoot/{numberOne}")
    public Double squareRoot(
            @PathVariable(value = "numberOne") String numberOne
    ) throws Exception {

        if (isNotNumeric(numberOne)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return Math.sqrt( convertToDouble(numberOne) );
    }

    private Double convertToDouble(String strNumber) {
        if (strNumber == null || strNumber.isEmpty()) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }

        String number = strNumber.replace(",", ".");
        return Double.parseDouble(number);
    }

    private boolean isNotNumeric(String strNumber) {
        if (strNumber == null || strNumber.isBlank()) {
            return true;
        }

        String number = strNumber.trim().replace(",", ".");

        return !number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

    private void validateNumbers(String numberOne, String numberTwo) {
        if (isNotNumeric(numberOne) || isNotNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
    }
}
