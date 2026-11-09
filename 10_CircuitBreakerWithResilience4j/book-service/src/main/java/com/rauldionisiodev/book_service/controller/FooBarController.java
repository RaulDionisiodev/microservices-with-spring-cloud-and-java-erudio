package com.rauldionisiodev.book_service.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("book-service")
public class FooBarController {

    private Logger logger = LoggerFactory.getLogger(FooBarController.class);

    @GetMapping("/foo-bar")
   // @Retry(name = "foo-bar", fallbackMethod = "fallbackMethod") // retry customizado com fallbackMethod
    //@Retry(name = "foo-bar") // retry customizado no application.yml
    //@Retry(name = "default") //retry padrão
    //@CircuitBreaker(name = "foo-bar", fallbackMethod = "fallbackMethod")
    //@RateLimiter(name = "default") // limita a quantidade de requisições que a aplicação pode receber
    @Bulkhead(name = "default") // Limita a quantidade de chamadas CONCORRENTES  que a aplicação pode receber
    public String fooBar() {
        logger.info("Request to foo bar is recieved");
//        var response = new RestTemplate().getForEntity(
//                "http://localhost:8080/foo-bar", String.class
//        );
//        return response.getBody();
        return "Foo Bar!";
    }

    public String fallbackMethod(Exception ex) {
        return "fallbackMethod FOO BAR";
    }
}