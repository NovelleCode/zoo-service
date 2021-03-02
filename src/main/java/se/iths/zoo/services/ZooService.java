package se.iths.zoo.services;

import com.iths.labbspringboot.dtos.CatDto;
import org.springframework.stereotype.Service;
import se.iths.bird.dtos.BirdDto;
import se.iths.zoo.entities.AnimalDto;
import se.iths.zoo.mappers.AnimalMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class ZooService {
    private final AnimalMapper animalMapper;

    public ZooService(AnimalMapper animalMapper) {
        this.animalMapper = animalMapper;
    }

    public List<AnimalDto>  convertToAnimals(CatDto[] cats, BirdDto[] birds) {
        List<AnimalDto> animals = new ArrayList<>();
        for(CatDto catDto : cats) {
            animals.add(animalMapper.mapp(catDto));
        }
        for(BirdDto birdDto : birds) {
            animals.add(animalMapper.mapp(birdDto));
        }
        return animals;
    }
}
