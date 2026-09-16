package com.github.rauldionisiodev.controllers;

import com.github.rauldionisiodev.exception.UnsupportedMathOperationException;
import com.github.rauldionisiodev.service.MathService;
import com.github.rauldionisiodev.web.parser.NumberParser;
import com.github.rauldionisiodev.web.validators.NumberValidator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {

    private final MathService mathService;
    private final NumberParser parser;
    private final NumberValidator validator;

    public MathController(MathService mathService, NumberParser parser, NumberValidator validator, NumberParser parser1, NumberValidator validator1) {
        this.mathService = mathService;
        this.parser = parser;
        this.validator = validator;
    }

    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) {
        validator.validateNumbers(numberOne, numberTwo);
        return mathService.sum(parser.convertToDouble(numberOne), parser.convertToDouble(numberTwo));
    }

    @RequestMapping("/subtraction/{numberOne}/{numberTwo}")
    public Double subtraction(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) {

        validator.validateNumbers(numberOne, numberTwo);
        return mathService.subtraction(parser.convertToDouble(numberOne), parser.convertToDouble(numberTwo));
    }

    @RequestMapping("/multiplication/{numberOne}/{numberTwo}")
    public Double multiplication(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) {

        validator.validateNumbers(numberOne, numberTwo);
        return mathService.multiplication(parser.convertToDouble(numberOne), parser.convertToDouble(numberTwo));
    }

    @RequestMapping("/mean/{numberOne}/{numberTwo}")
    public Double mean(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) {

        validator.validateNumbers(numberOne, numberTwo);
        return mathService.mean(parser.convertToDouble(numberOne), parser.convertToDouble(numberTwo));
    }

    @RequestMapping("/division/{numberOne}/{numberTwo}")
    public Double division(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) {

        validator.validateNumbers(numberOne, numberTwo);
        return mathService.division(parser.convertToDouble(numberOne), parser.convertToDouble(numberTwo));
    }

    @RequestMapping("/squareRoot/{numberOne}")
    public Double squareRoot(
            @PathVariable(value = "numberOne") String numberOne
    ) {

        validator.validateNumber(numberOne);
        return mathService.squareRoot(parser.convertToDouble(numberOne));
    }
}
