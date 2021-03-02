package se.iths.zoo;

import com.iths.labbspringboot.dtos.CatDto;
import se.iths.weblab2.FishDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import se.iths.bird.dtos.BirdDto;

import se.iths.zoo.entities.AnimalDto;
import se.iths.zoo.mappers.AnimalMapper;
import se.iths.zoo.services.ZooService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    RestTemplate restTemplate;
    private final ZooService zooService;

    public Controller(RestTemplate restTemplate, ZooService zooService) {
        this.restTemplate = restTemplate;
        this.zooService = zooService;
    }

    @GetMapping("/zoo")
    public List<AnimalDto> zoo(){
        var cats = this.restTemplate.getForObject("http://cats-service/cats/", CatDto[].class);
        var birds = this.restTemplate.getForObject("http://birds-service/birds/", BirdDto[].class);
        var fish = this.restTemplate.getForObject("http://fish-service/fish/", FishDto[].class);
        return zooService.convertToAnimals(cats, birds, fish);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
