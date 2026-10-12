package br.com.rauldionisiodev.controller;

import br.com.rauldionisiodev.config.GreetingConfiguration;
import br.com.rauldionisiodev.model.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "%s, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private GreetingConfiguration greetingConfiguration;

    // http://localhost:8080/greeting?name=Leandro
    @RequestMapping("/greeting")
    public Greeting greeting(
            @RequestParam(value = "name", defaultValue = "")
            String name){
        if (name.isEmpty()) name = greetingConfiguration.getDefaultValue();

        return  new Greeting(counter.incrementAndGet(),
                String.format(template, greetingConfiguration.getGreeting() ,name));
    }
}
