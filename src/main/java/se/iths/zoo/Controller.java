package se.iths.zoo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import se.iths.zoo.dtos.*;
import se.iths.zoo.services.ZooService;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    RestTemplate restTemplate;

    private final ZooService zooService;


    public Controller(ZooService zooService) {
        this.zooService = zooService;
    }


    @GetMapping("/zoo")
    public List<AnimalDto> zoo() {

        var cats = this.restTemplate.getForObject("http://cats-consul/cats/", CatDto[].class);
        var birds = this.restTemplate.getForObject("http://birds-service/birds/", BirdDto[].class);
        var fishes = this.restTemplate.getForObject("http://fish-service/fish/", FishDto[].class);
        var snakes = this.restTemplate.getForObject("http://snake-service/snake/", SnakeDto[].class);

        return zooService.convertToAnimals(cats, birds, fishes, snakes);

    }

    @GetMapping(value = "/zoo/search", params = "gender")
    public List<AnimalDto> getAnimalsByGender(@RequestParam String gender) {

        var cats = this.restTemplate.getForObject("http://cats-consul/cats/search?gender=" + gender, CatDto[].class);
        var birds = this.restTemplate.getForObject("http://birds-service/birds/search?gender=" + gender, BirdDto[].class);
        var fish = this.restTemplate.getForObject("http://fish-service/fish/search?gender=" + gender, FishDto[].class);
        var snakes = this.restTemplate.getForObject("http://snake-service/snake/search?gender=" + gender, SnakeDto[].class);

        return zooService.convertToAnimals(cats, birds, fish, snakes);
    }


    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
