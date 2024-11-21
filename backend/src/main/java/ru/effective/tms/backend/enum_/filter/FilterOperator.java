package ru.effective.tms.backend.enum_.filter;

public enum FilterOperator {

    AND,

    OR,

    DEFAULT;

    public static FilterOperator fromString(String operatorStr) {
        try {
            return FilterOperator.valueOf(operatorStr.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return AND;
        }
    }
}
