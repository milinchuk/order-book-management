package org.order.book.management.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FilePrinter implements Printer {
    private PrintWriter printWriter;

    public FilePrinter(String fileToWrite) throws IOException {
        FileWriter fileWriter = new FileWriter(fileToWrite);
        printWriter = new PrintWriter(fileWriter);
    }

    @Override
    public void close() {
        printWriter.close();
    }

    @Override
    public void print(int price, int size) {
        printWriter.println(price + "," + size);
    }

    @Override
    public void print(int size) {
        printWriter.println(size);
    }
}
