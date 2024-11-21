package ru.effective.tms.backend.enum_.filter;

public enum SortDir {

    ASC("asc"),

    DESC("desc");

    private final String value;

    SortDir(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
