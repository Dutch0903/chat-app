package com.chat_app.infrastructure.util;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChangeResult<T> {
    private final List<T> additions;
    private final List<T> deletions;

    public ChangeResult(List<T> additions, List<T> deletions) {
        this.additions = additions;
        this.deletions = deletions;
    }

    public ChangeResult() {
        this.additions = new ArrayList<>();
        this.deletions = new ArrayList<>();
    }

    public boolean hasAdditions() {
        return !additions.isEmpty();
    }

    public boolean hasDeletions() {
        return !deletions.isEmpty();
    }
}
