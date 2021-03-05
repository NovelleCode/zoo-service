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
        CatDto[] cats;
        BirdDto[] birds;
        FishDto[] fish;
        SnakeDto[] snakes;

        try{
           cats = this.restTemplate.getForObject("http://cats-service/cats/", CatDto[].class);
        }catch (Exception ex){
            cats = new CatDto[0];
        }

        try{
            birds = this.restTemplate.getForObject("http://birds-service/birds/", BirdDto[].class);
        }catch (Exception ex){
            birds = new BirdDto[0];
        }

        try{
            fish = this.restTemplate.getForObject("http://fish-service/fish/", FishDto[].class);
        }catch (Exception ex){
            fish = new FishDto[0];
        }

        try{
            snakes = this.restTemplate.getForObject("http://snake-service/snakes/", SnakeDto[].class);
        }catch (Exception ex){
            snakes = new SnakeDto[0];
        }

        return zooService.convertToAnimals(cats, birds, fish, snakes);
    }

    @GetMapping(value = "/zoo/search", params = "gender")
    public List<AnimalDto> getAnimalsByGender(@RequestParam String gender) {
        CatDto[] cats;
        BirdDto[] birds;
        FishDto[] fish;
        SnakeDto[] snakes;

        try{
            cats = this.restTemplate.getForObject("http://cats-service/cats/search?gender=" + gender, CatDto[].class);
        }catch (Exception ex){
            cats = new CatDto[0];
        }

        try{
            birds = this.restTemplate.getForObject("http://birds-service/birds/search?gender=" + gender, BirdDto[].class);
        }catch (Exception ex){
            birds = new BirdDto[0];
        }

        try{
            fish = this.restTemplate.getForObject("http://fish-service/fish/search?gender=" + gender, FishDto[].class);
        }catch (Exception ex){
            fish = new FishDto[0];
        }

        try{
            snakes = this.restTemplate.getForObject("http://snake-service/snakes/search?gender=" + gender, SnakeDto[].class);
        }catch (Exception ex){
            snakes = new SnakeDto[0];
        }

        return zooService.convertToAnimals(cats, birds, fish, snakes);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
