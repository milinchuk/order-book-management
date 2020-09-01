package org.order.book.management.type;

public enum CommandType {
    UPDATE("u"),
    QUERY("q"),
    ORDER("o");

    private String value;

    CommandType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
