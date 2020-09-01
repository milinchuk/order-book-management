package org.order.book.management.util;

public interface Printer {
    void close();

    void print(int price, int size);

    void print(int i);
}
