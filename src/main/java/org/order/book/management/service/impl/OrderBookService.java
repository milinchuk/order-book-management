package org.order.book.management.service.impl;

import org.order.book.management.entity.Order;
import org.order.book.management.service.OrderActions;
import org.order.book.management.service.QueryActions;
import org.order.book.management.service.UpdateActions;
import org.order.book.management.util.Printer;

public class OrderBookService implements UpdateActions, QueryActions, OrderActions {

    private final Order askOrder = new Order((e1, e2) -> e1 > e2);

    private final Order bidOrder = new Order((e1, e2) -> e1 < e2);

    private final Order spreadOrder = new Order();

    private final Printer printer;

    public OrderBookService(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void buy(int size) {
        performMarketOperation(askOrder, size);
    }

    @Override
    public void sell(int size) {
        performMarketOperation(bidOrder, size);
    }

    @Override
    public void printBestBid() {
        int bestBidPrice = bidOrder.getBestPrice();

        printer.print(bestBidPrice, bidOrder.getSizeByPrice(bestBidPrice));
    }

    @Override
    public void printBestAsk() {
        int bestAskPrice = askOrder.getBestPrice();

        printer.print(bestAskPrice, askOrder.getSizeByPrice(bestAskPrice));
    }

    @Override
    public void printSizeByPrice(int price) {
        int askSizeByPrice = askOrder.getSizeByPrice(price);
        int bidSizeByPrice = bidOrder.getSizeByPrice(price);

        printer.print(askSizeByPrice + bidSizeByPrice);
    }

    @Override
    public void putBid(int price, int size) {
        putToOrder(price, size, askOrder, bidOrder);
    }

    @Override
    public void putAsk(int price, int size) {
        putToOrder(price, size, bidOrder, askOrder);
    }

    public Integer getBestBidPrice() {
        return bidOrder.getBestPrice();
    }

    public Integer getBestAskPrice() {
        return askOrder.getBestPrice();
    }

    private void performMarketOperation(Order order, int size) {
        int bestPrice = order.getBestPrice();
        int bestSize = order.getSizeByPrice(bestPrice);

        if (bestSize == size) {
            order.setAsUnwilling(bestPrice);
        } else {
            if (bestSize > size) {
                order.put(bestPrice, bestSize - size);
            } else {
                order.setAsUnwilling(bestPrice);
                performMarketOperation(order,size - bestSize);
            }
        }
    }

    private void putToOrder(int price, int size, Order orderForPutting, Order orderForRemoving) {
        if (size == 0 && orderForPutting.isExistsSizeByPrice(price) && orderForPutting.getSizeByPrice(price) == 0) {
            orderForRemoving.remove(price);
            orderForPutting.remove(price);
            spreadOrder.put(price, size);
        } else {
            orderForRemoving.put(price, size);
        }
    }
}
