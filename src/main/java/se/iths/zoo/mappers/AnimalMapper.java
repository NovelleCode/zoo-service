package se.iths.zoo.mappers;

import com.iths.labbspringboot.dtos.CatDto;
import se.iths.bird.dtos.BirdDto;

import se.iths.zoo.entities.AnimalDto;



public class AnimalMapper {
    public AnimalMapper() {
    }

    public AnimalDto mapp(BirdDto bird) {
        return new AnimalDto(bird.getId(), bird.getName(), bird.getType(), bird.getGender(), bird.getWeight());
    }

    public AnimalDto mapp(CatDto cat) {
        return new AnimalDto(cat.getId(), cat.getName(), cat.getType(), cat.getGender(), cat.getWeight());
    }

}
