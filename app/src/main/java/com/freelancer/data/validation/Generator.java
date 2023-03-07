package com.freelancer.data.validation;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

public class Generator {
    public static final RandomStringGenerator generator = new RandomStringGenerator
            .Builder()
            .withinRange('0', 'z')
            .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
            .build();

    /**
     * Generates a 20-character random string of letters and numbers.
     * Used for creating mock data.
     *
     * @return the random String.
     */
    public static String generateRandomId() {
        return generator.generate(20);
    }
}
