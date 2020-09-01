package org.order.book.management.type;

public enum OrderType {
    BUY("buy"),
    SELL("sell");

    private String value;

    OrderType(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
