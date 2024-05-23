package com.github.jonashonecker.backend.asterix;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsterixService {
    private final AsterixRepository asterixRepository;
    private final IdService idService;

    public AsterixService(AsterixRepository asterixRepository, IdService idService) {
        this.asterixRepository = asterixRepository;
        this.idService = idService;
    }

    public List<Character> getAllCharacters(Integer age) {
        if (age != null ) {
            return asterixRepository.findByAgeLessThanEqual(age);
        }
        return asterixRepository.findAll();
    }

    public List<Character> getCharacterByProfession(String profession) {
        return asterixRepository.findByProfessionAndReturnAgeOnly(profession);
    }

    public Character postNewCharacter(Character newCharacter) {
        Character newCharacterWithId = newCharacter.withId(idService.generateId());
        return asterixRepository.insert(newCharacterWithId);
    }

    public Character putCharacter(Character characterToUpdate) {
        return asterixRepository.save(characterToUpdate);
    }

    public void deleteCharacterById(String id) {
        asterixRepository.deleteById(id);
    }

    public Character getCharacterById(String id) {
        return asterixRepository.findById(id)
                .orElseThrow();
    }
}
