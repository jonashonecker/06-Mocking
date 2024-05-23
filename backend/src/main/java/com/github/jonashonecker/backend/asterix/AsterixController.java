package com.github.jonashonecker.backend.asterix;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/characters")
public class AsterixController {

    private final AsterixService asterixService;

    public AsterixController(AsterixService asterixService) {
        this.asterixService = asterixService;
    }

    @GetMapping
    public List<Character> getAllCharacters(
            @RequestParam(required = false) Integer age
    ) {
        return asterixService.getAllCharacters(age);
    }

    @GetMapping("{id}")
    public Character getCharacterById (@PathVariable String id) {
        return asterixService.getCharacterById(id);
    }

    @GetMapping("/statistics")
    public BigDecimal calcMeanAgeByProfession(@RequestParam String profession) {
        List<Character> characters = asterixService.getCharacterByProfession(profession);
        int sum = characters.stream().mapToInt(Character::age).sum();
        return new BigDecimal(sum).divide(BigDecimal.valueOf(characters.size()), 2, RoundingMode.HALF_UP);
    }

    @PostMapping
    public Character insertCharacter(@RequestBody NewCharacter newCharacter) {
        return asterixService.postNewCharacter(
                Character.builder()
                        .id(null)
                        .age(newCharacter.age())
                        .name(newCharacter.name())
                        .profession(newCharacter.profession())
                        .build()
        );
    }

    @PutMapping("{id}")
    public Character updateCharacter(@PathVariable String id, @RequestBody UpdateCharacter characterToUpdate) {
        return asterixService.putCharacter(
                Character.builder()
                        .id(id)
                        .age(characterToUpdate.age())
                        .name(characterToUpdate.name())
                        .profession(characterToUpdate.profession())
                        .build()
        );
    }

    @DeleteMapping("{id}")
    public void deleteCharacter(@PathVariable String id) {
        asterixService.deleteCharacterById(id);
    }
}
