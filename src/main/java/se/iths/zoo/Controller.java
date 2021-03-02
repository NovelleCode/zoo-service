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
import se.iths.bird.mappers.BirdMapper;
import se.iths.bird.repositories.BirdRepository;
import se.iths.zoo.entities.AnimalDto;
import se.iths.zoo.mappers.AnimalMapper;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    RestTemplate restTemplate;

    private AnimalMapper animalMapper;

    public Controller(AnimalMapper animalMapper) {
        this.animalMapper = animalMapper;
    }


    @GetMapping("/getzoo")
    public List<AnimalDto> zoo(){
        List<AnimalDto> animals = new ArrayList<>();

        var cats = this.restTemplate.getForObject("http://cats-service/cats/", CatDto[].class);
        var birds = this.restTemplate.getForObject("http://birds-service/birds/", BirdDto[].class);

        for(CatDto catDto : cats) {
            animals.add(animalMapper.mapp(catDto));
        }
        for(BirdDto birdDto : birds) {
            animals.add(animalMapper.mapp(birdDto));
        }
        return animals;
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
