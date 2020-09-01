package org.order.book.management;

import org.order.book.management.service.impl.OrderBookService;
import org.order.book.management.type.CommandType;
import org.order.book.management.type.OrderType;
import org.order.book.management.type.QueryType;
import org.order.book.management.type.UpdateType;

public class Controller {

    private OrderBookService orderBookService;

    public Controller(OrderBookService orderBookService) {
        this.orderBookService = orderBookService;
    }

    public void splitCommand(String command) {
        String[] splitCommand = command.split(",");
        String commandType = splitCommand[0];

        if (CommandType.UPDATE.value().equals(commandType)) {
            delegateUpdateAction(Integer.valueOf(splitCommand[1]), Integer.valueOf(splitCommand[2]), splitCommand[3]);
        }

        if (CommandType.QUERY.value().equals(commandType)) {
            if (splitCommand.length == 3) {
                delegateQueryActions(splitCommand[1], Integer.valueOf(splitCommand[2]));
            } else {
                delegateQueryActions(splitCommand[1], null);
            }
        }

        if (CommandType.ORDER.value().equals(commandType)) {
            delegateOrderActions(splitCommand[1], Integer.valueOf(splitCommand[2]));
        }
    }

    private void delegateUpdateAction(Integer price, Integer size, String type) {
        if (UpdateType.BID.value().equals(type)) {
            orderBookService.putBid(price, size);
        }

        if (UpdateType.ASK.value().equals(type)) {
            orderBookService.putAsk(price, size);
        }
    }

    private void delegateQueryActions(String type, Integer price) {
        if (QueryType.BEST_BID.value().equals(type)) {
            orderBookService.printBestBid();
        }

        if (QueryType.BEST_ASK.value().equals(type)) {
            orderBookService.printBestAsk();
        }

        if (QueryType.SIZE.value().equals(type)) {
            orderBookService.printSizeByPrice(price);
        }
    }

    private void delegateOrderActions(String type, Integer size) {
        if (OrderType.BUY.value().equals(type)) {
            orderBookService.buy(size);
        }

        if (OrderType.SELL.value().equals(type)) {
            orderBookService.sell(size);
        }
    }
}
