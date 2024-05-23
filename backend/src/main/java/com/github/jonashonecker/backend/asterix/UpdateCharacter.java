package com.github.jonashonecker.backend.asterix;

public record UpdateCharacter(
        String name,
        int age,
        String profession
) {
}
