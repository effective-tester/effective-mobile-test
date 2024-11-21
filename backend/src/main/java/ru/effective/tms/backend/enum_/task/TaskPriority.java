package ru.effective.tms.backend.enum_.task;

public enum TaskPriority {

    HIGH("Высокий"),

    MEDIUM("Средний"),

    LOW("Низкий");

    private final String translation;

    TaskPriority(String translation) {
        this.translation = translation;
    }

    public String translation() {
        return translation;
    }
}
