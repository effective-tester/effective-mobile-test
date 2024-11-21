package ru.effective.tms.backend.enum_.filter;

public enum FilterParameter {

    AUTHOR("author"),

    ASSIGNEE("assignee");

    private final String value;

    FilterParameter(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
