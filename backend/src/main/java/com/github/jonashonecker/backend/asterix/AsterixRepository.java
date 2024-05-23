package com.github.jonashonecker.backend.asterix;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AsterixRepository extends MongoRepository<Character, String> {
    @Query(value = "{'profession': ?0}", fields = "{'age': 1}")
    List<Character> findByProfessionAndReturnAgeOnly(String profession);

    List<Character> findByAgeLessThanEqual(int age);
}
