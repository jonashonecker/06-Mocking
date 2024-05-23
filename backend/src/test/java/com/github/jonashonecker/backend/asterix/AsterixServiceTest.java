package com.github.jonashonecker.backend.asterix;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class AsterixServiceTest {

    private final AsterixRepository asterixRepository = mock(AsterixRepository.class);
    private final IdService idService = mock(IdService.class);
    private final AsterixService asterixService = new AsterixService(asterixRepository, idService);

    @Test
    void getAllCharacters_returnsAll_whenAgeIsNull() {
        //GIVEN
        List<Character> expectedCharacters = List.of(new Character("1", "a", 3, "b"));
        when(asterixRepository.findAll()).thenReturn(expectedCharacters);

        //WHEN
        List<Character> actualCharacters = asterixService.getAllCharacters(null);

        //THEN
        verify(asterixRepository).findAll();
        verifyNoMoreInteractions(asterixRepository);
        assertEquals(expectedCharacters, actualCharacters);
    }

    @Test
    void getAllCharacters_returnsFiltered_whenAgeIsNotNull() {
        //GIVEN
        List<Character> expectedCharacters = List.of(
                new Character("1", "a", 3, "b"),
                new Character("1", "a", 4, "b"),
                new Character("1", "a", 5, "b")
        );

        when(asterixRepository.findByAgeLessThanEqual(anyInt())).thenReturn(expectedCharacters);

        //WHEN
        List<Character> actualCharacters = asterixService.getAllCharacters(5);

        //THEN
        verify(asterixRepository).findByAgeLessThanEqual(5);
        verifyNoMoreInteractions(asterixRepository);
        assertEquals(expectedCharacters, actualCharacters);
    }

    @Test
    void getCharacterById_returnsCharacter_whenIdExists() {
        //GIVEN
        String id = "1";
        Character expectedCharacter = new Character("1", "a", 3, "b");

        when(asterixRepository.findById(id)).thenReturn(Optional.of(expectedCharacter));

        //WHEN
        Character actualCharacter = asterixService.getCharacterById(id);

        //THEN
        verify(asterixRepository).findById(id);
        verifyNoMoreInteractions(asterixRepository);
        assertEquals(expectedCharacter, actualCharacter);
    }

    @Test
    void getCharacterById_throwsException_whenIdDoesNotExist() {
        //GIVEN
        String id = "1";

        when(asterixRepository.findById(id)).thenReturn(Optional.empty());

        //WHEN, THEN
        assertThrows(NoSuchElementException.class, () -> asterixService.getCharacterById(id));
    }

    @Test
    void postNewCharacter_returnsCharacterWithId_whenNewCharacterIsPosted() {
        //GIVEN
        Character newCharacter = new Character(null,"a", 3, "b");
        Character expectedCharacter = new Character("1", "a", 3, "b");

        when(idService.generateId()).thenReturn("1");
        when(asterixRepository.insert(expectedCharacter)).thenReturn(expectedCharacter);

        //WHEN
        Character actualCharacter = asterixService.postNewCharacter(newCharacter);

        //THEN
        verify(idService).generateId();
        verify(asterixRepository).insert(expectedCharacter);
        verifyNoMoreInteractions(idService, asterixRepository);
        assertEquals(expectedCharacter, actualCharacter);
    }

    @Test
    void putCharacter_returnsUpdatedCharacter_whenCharacterExists() {
        //GIVEN
        Character characterToUpdate = new Character("1", "a", 3, "b");

        when(asterixRepository.save(characterToUpdate)).thenReturn(characterToUpdate);

        //WHEN
        Character actualCharacter = asterixService.putCharacter(characterToUpdate);

        //THEN
        verify(asterixRepository).save(characterToUpdate);
        verifyNoMoreInteractions(asterixRepository);
        verifyNoInteractions(idService);
        assertEquals(characterToUpdate, actualCharacter);
    }


    @Test
    void deleteCharacterById_removesCharacter_whenIdExists() {
        //GIVEN
        String id = "1";
        doNothing().when(asterixRepository).deleteById(id);

        //WHEN
        asterixService.deleteCharacterById(id);

        //THEN
        verify(asterixRepository).deleteById(id);
        verifyNoMoreInteractions(asterixRepository);
        verifyNoInteractions(idService);
    }
}