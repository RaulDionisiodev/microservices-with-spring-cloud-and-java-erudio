package com.github.rauldionisiodev.service;

import org.springframework.stereotype.Service;

@Service
public class MathService {

    public Double sum(Double numberOne, Double numberTwo) {
        return numberOne + numberTwo;
    }

    public Double subtraction(Double numberOne, Double numberTwo) {
        return numberOne - numberTwo;
    }

    public Double multiplication(Double numberOne, Double numberTwo) {
        return numberOne * numberTwo;
    }

    public Double division(Double numberOne, Double numberTwo) {
        if (numberTwo == 0) {
            throw new ArithmeticException("Impossível dividir por zero.");
        }
        return numberOne / numberTwo;
    }

    public Double mean(Double numberOne, Double numberTwo) {
        return (numberOne + numberTwo)/2;
    }

    public Double squareRoot(Double number) {
        return Math.sqrt(number);
    }
}