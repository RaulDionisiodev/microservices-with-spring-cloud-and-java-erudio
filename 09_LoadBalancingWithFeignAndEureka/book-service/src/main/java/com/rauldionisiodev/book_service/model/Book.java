package com.rauldionisiodev.book_service.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    public Book(Long id, String author, String title, LocalDate lauchDate, Double price, String currency, String enviroment) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.lauchDate = lauchDate;
        this.price = price;
        this.currency = currency;
        this.enviroment = enviroment;
    }

    public Book() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 180)
    private String author;
    @Column(nullable = false, length = 250)
    private String title;
    @Column(nullable = false, name = "launch_date")
    private LocalDate lauchDate;
    @Column(nullable = false)
    private Double price;
    @Transient
    private String currency;
    @Transient
    private String enviroment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getLauchDate() {
        return lauchDate;
    }

    public void setLauchDate(LocalDate lauchDate) {
        this.lauchDate = lauchDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getEnviroment() {
        return enviroment;
    }

    public void setEnviroment(String enviroment) {
        this.enviroment = enviroment;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(author, book.author) && Objects.equals(title, book.title) && Objects.equals(lauchDate, book.lauchDate) && Objects.equals(price, book.price) && Objects.equals(currency, book.currency) && Objects.equals(enviroment, book.enviroment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, title, lauchDate, price, currency, enviroment);
    }
}
