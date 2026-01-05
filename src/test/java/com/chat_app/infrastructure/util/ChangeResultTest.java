package com.chat_app.infrastructure.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChangeResultTest {

    @Test
    public void constructor_WithArguments_CreatesInitializedChangeResult() {
        List<String> additions = Arrays.asList("A", "B", "C");
        List<String> deletions = Arrays.asList("D", "E", "F");
        ChangeResult<String> changeResult = new ChangeResult<>(
                additions,
                deletions
        );

        assertTrue(changeResult.getAdditions().containsAll(additions));
        assertTrue(changeResult.getDeletions().containsAll(deletions));
    }

    @Test
    public void constructor_WithNoArguments_CreatesEmptyChangeResult() {
        ChangeResult<?> changeResult = new ChangeResult<>();

        assertFalse(changeResult.hasDeletions());
        assertFalse(changeResult.hasAdditions());
    }
}
