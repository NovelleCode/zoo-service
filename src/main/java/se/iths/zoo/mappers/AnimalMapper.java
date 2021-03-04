package se.iths.zoo.mappers;

import org.springframework.stereotype.Component;

import se.iths.zoo.dtos.*;


@Component
public class AnimalMapper {
    public AnimalMapper() {
    }

    public AnimalDto mapp(BirdDto bird) {
        return new AnimalDto("Bird", bird.getId(), bird.getName(), bird.getType(), bird.getGender(), bird.getWeight());
    }

    public AnimalDto mapp(CatDto cat) {
        return new AnimalDto("Cat", cat.getId(), cat.getName(), cat.getType(), cat.getGender(), cat.getWeight());
    }

    public AnimalDto mapp(FishDto fish) {
        return new AnimalDto("Fish", fish.getId(), fish.getName(), fish.getType(), fish.getGender(), fish.getWeight());
    }

    public AnimalDto mapp(SnakeDto snake) {
        return new AnimalDto("Snake", snake.getId(), snake.getName(), snake.getType(), snake.getGender(), snake.getWeight());
    }

}
