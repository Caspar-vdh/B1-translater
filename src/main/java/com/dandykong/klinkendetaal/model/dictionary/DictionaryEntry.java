package com.dandykong.klinkendetaal.model.dictionary;

import java.util.Arrays;
import java.util.Objects;

public class DictionaryEntry {
    private String[] key;
    private String[] value;

    public DictionaryEntry() {}

    public DictionaryEntry(String[] key, String[] value) {
        this.key = key;
        this.value = value;
    }

    public String[] getKey() {
        return key;
    }

    public String[] getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DictionaryEntry entry = (DictionaryEntry) o;
        return Objects.deepEquals(key, entry.key) && Objects.deepEquals(value, entry.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(key), Arrays.hashCode(value));
    }
}
