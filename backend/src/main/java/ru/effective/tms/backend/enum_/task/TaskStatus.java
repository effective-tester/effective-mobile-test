package ru.effective.tms.backend.enum_.task;

public enum TaskStatus {

    PENDING("В ожидании"),

    IN_PROGRESS("В процессе"),

    COMPLETED("Завершено");

    private final String translation;

    TaskStatus(String translation) {
        this.translation = translation;
    }

    public String translation() {
        return translation;
    }
}
