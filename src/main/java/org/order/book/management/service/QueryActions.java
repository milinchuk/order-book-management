package org.order.book.management.service;

public interface QueryActions {
    void printBestBid();

    void printBestAsk();

    void printSizeByPrice(int price);
}
