package ru.effective.tms.backend.enum_.security;

public enum UserRole {

    USER,

    ADMIN;

    public static UserRole fromString(String operatorStr) {
        try {
            return UserRole.valueOf(operatorStr.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return USER;
        }
    }
}
