package se.iths.zoo;

import com.iths.labbspringboot.dtos.CatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import se.iths.bird.dtos.BirdDto;
import se.iths.weblab2.dtos.FishDto;
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

        var cats = this.restTemplate.getForObject("http://cats-consul/cats/", CatDto[].class);
        var birds = this.restTemplate.getForObject("http://birds-service/birds/", BirdDto[].class);
        var fishes = this.restTemplate.getForObject("http://fish-service/fish/", FishDto[].class);

        for(CatDto catDto : cats) {
            animals.add(animalMapper.mapp(catDto));
        }
        for(BirdDto birdDto : birds) {
            animals.add(animalMapper.mapp(birdDto));
        }
        for(FishDto fishDto : fishes) {
            animals.add(animalMapper.mapp(fishDto));
        }

        return animals;
    }

    @GetMapping(value = "/getzoo/search", params = "gender")
    public List<AnimalDto> getAnimalsByGender(@RequestParam String gender){

        var cats = this.restTemplate.getForObject("http://cats-consul/cats/search?gender=" + gender, CatDto[].class);
        var birds = this.restTemplate.getForObject("http://birds-service/birds/search?gender=" + gender, BirdDto[].class);
        var fish = this.restTemplate.getForObject("http://fish-service/fish/search?gender=" + gender, FishDto[].class);

        List<AnimalDto> animals = new ArrayList<>();

        for (CatDto catDto : cats) {
            animals.add(animalMapper.mapp(catDto));
        }
        for (BirdDto birdDto : birds) {
            animals.add(animalMapper.mapp(birdDto));
        }
        for (FishDto fishDto : fish) {
            animals.add(animalMapper.mapp(fishDto));
        }
        return animals;
    }


    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
