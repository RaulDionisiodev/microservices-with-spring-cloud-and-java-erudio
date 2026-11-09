package com.rauldionisiodev.book_service.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Exchange implements Serializable {


    private static final Long serialVersionUID = 1L;


    private Long id;
    private String from;
    private String to;
    private String enviroment;
    private BigDecimal conversionFactor;
    private Double convertedValue;

    public Exchange() {
    }

    public Exchange(Long id, String from, String to, String enviroment, BigDecimal conversionFactor, Double convertedValue) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.enviroment = enviroment;
        this.conversionFactor = conversionFactor;
        this.convertedValue = convertedValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getEnviroment() {
        return enviroment;
    }

    public void setEnviroment(String enviroment) {
        this.enviroment = enviroment;
    }

    public BigDecimal getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(BigDecimal conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public Double getConvertedValue() {
        return convertedValue;
    }

    public void setConvertedValue(Double convertedValue) {
        this.convertedValue = convertedValue;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Exchange exchange = (Exchange) o;
        return Objects.equals(id, exchange.id) && Objects.equals(from, exchange.from) && Objects.equals(to, exchange.to) && Objects.equals(enviroment, exchange.enviroment) && Objects.equals(conversionFactor, exchange.conversionFactor) && Objects.equals(convertedValue, exchange.convertedValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, enviroment, conversionFactor, convertedValue);
    }
}
