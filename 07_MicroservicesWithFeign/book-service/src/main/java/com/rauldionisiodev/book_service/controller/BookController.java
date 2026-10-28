package com.rauldionisiodev.book_service.controller;

import com.rauldionisiodev.book_service.dto.Exchange;
import com.rauldionisiodev.book_service.enviroment.InstanceInformationService;
import com.rauldionisiodev.book_service.model.Book;
import com.rauldionisiodev.book_service.proxy.ExchangeProxy;
import com.rauldionisiodev.book_service.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping(value = "book-service")
public class BookController {

    @Autowired
    private InstanceInformationService informationService;
    @Autowired
    private BookRepository repository;
    @Autowired
    private ExchangeProxy proxy;


    @GetMapping(value = "/{id}/{currency}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book findBook(
            @PathVariable("id") Long id,
            @PathVariable("currency") String currency
    ){
        String port = informationService.retrieveServerPort();
        Book book = repository.findById(id).orElseThrow();

        /* exemplo de implementação com restTemplate
        HashMap<String, String> params = new HashMap<>();
        params.put("amount", book.getPrice().toString());
        params.put("from", "USD");
        params.put("to", currency);


        var response = new RestTemplate()
                .getForEntity("http://localhost:8001/exchange-service/{amount}/{from}/{to}",
                        Exchange.class,  params);
        */

        /* implementação com feign*/
        Exchange exchange = proxy.getExchange(book.getPrice(), "USD", currency);

        book.setEnviroment(port + " FEIGN");
        book.setCurrency(currency);
        book.setPrice(exchange.getConvertedValue());
        return book;
    }
}
