package com.github.jonashonecker.backend.asterix;

import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@With
@Document("characters")
public record Character(
        @Id
        String id,
        String name,
        int age,
        String profession
) {
}
