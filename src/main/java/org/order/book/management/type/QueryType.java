package org.order.book.management.type;

public enum QueryType {
    BEST_BID("best_bid"),
    BEST_ASK("best_ask"),
    SIZE("size");

    private String value;

    QueryType(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
