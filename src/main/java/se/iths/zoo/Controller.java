package se.iths.zoo;

import com.iths.labbspringboot.dtos.CatDto;
import com.iths.labbspringboot.entities.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import se.iths.bird.dtos.BirdDto;
import se.iths.bird.entities.Bird;
import se.iths.zoo.entities.AnimalDto;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    RestTemplate restTemplate;




//    Detta kan användas om man inte vill ha default på enable-retry, där man kan sätta
//      hur många försök och delay.
//    @Retryable(value = ResourceAccessException.class,
//            maxAttempts = 2, backoff = @Backoff(delay = 100))

    // Skapa ett objekt som redan innehåller både Cat och Bird
    @GetMapping("/getzoo")
    public List<AnimalDto> zoo(){
        var cat = this.restTemplate.getForObject("http://cats-service/cats/", CatDto[].class);
        var bird = this.restTemplate.getForObject("http://birds-service/birds/", BirdDto[].class);

        // Mappa cat & bird till en Animal, så att vi kan returnera ett AnimalDto objekt som Json

        List<AnimalDto> animals = new ArrayList<>();

        return animals;



//        return cat.toString() + bird.toString();
//        return this.restTemplate.getForObject("http://cats-service/cats/", String.class);
    }
    //behöver inte skriva ip-adress eller port pga att den går via registreringen (consul)


    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
