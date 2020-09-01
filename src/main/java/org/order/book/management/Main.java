package org.order.book.management;

import org.order.book.management.service.impl.OrderBookService;
import org.order.book.management.util.FilePrinter;
import org.order.book.management.util.Printer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String INPUT = "input.txt";

    private static final String OUTPUT = "output.txt";

    public static void main(String[] args) throws IOException {
        Printer printer = new FilePrinter(OUTPUT);
        OrderBookService orderBookService = new OrderBookService(printer);
        Controller controller = new Controller(orderBookService);

        List<String> commands = getCommandSequence();

        commands.forEach(controller::splitCommand);

        printer.close();
    }

    private static List<String> getCommandSequence() throws FileNotFoundException {
        File file = new File(INPUT);
        Scanner reader = new Scanner(file);

        List<String> commands = new ArrayList<>();

        while (reader.hasNextLine()) {
            commands.add(reader.nextLine());
        }
        reader.close();

        return commands;
    }
}
