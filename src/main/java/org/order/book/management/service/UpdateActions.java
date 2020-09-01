package org.order.book.management.service;

public interface UpdateActions {
    void putBid(int price, int size);

    void putAsk(int price, int size);
}
