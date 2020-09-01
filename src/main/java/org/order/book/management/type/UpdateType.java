package org.order.book.management.type;

public enum UpdateType {
    BID("bid"),
    ASK("ask"),
    SPREAD("spread");

    private String value;

    UpdateType(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
