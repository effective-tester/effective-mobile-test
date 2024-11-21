package ru.effective.tms.backend.enum_.filter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum JoinField {

    ID("id");


    private final String value;

    JoinField(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static List<String> getAllParameters() {
        return Arrays.stream(JoinField.values())
                .map(JoinField::value)
                .collect(Collectors.toList());
    }
}
