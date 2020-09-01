package org.order.book.management.util;

public class StringPrinter implements Printer {

    @Override
    public void print(int price, int size) {
        System.out.println(price + "," + size);
    }

    @Override
    public void print(int i) {
        System.out.println(i);
    }

    @Override
    public void close() {
        // not supported
    }
}
