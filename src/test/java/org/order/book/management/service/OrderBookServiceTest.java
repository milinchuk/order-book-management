package org.order.book.management.service;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.order.book.management.service.impl.OrderBookService;
import org.order.book.management.util.StringPrinter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(JUnitParamsRunner.class)
public class OrderBookServiceTest {

    private OrderBookService orderBookService = new OrderBookService(new StringPrinter());

    @Test
    @Parameters({
        "100, 1, 99, 0, 100",
        "100, 0, 99, 1, 99",
        "100, 0, 99, 0, 100",
        "99, 1, 100, 0, 99",
        "100, 50, 50, 200, 50",
        "20, 100, 100, 5, 20"
    })
    public void shouldReturnCorrectBestAskPrice(int firstPrice, int firstSize, int secondPrice,
                                                int secondSize, int expected) {
        orderBookService.putAsk(firstPrice, firstSize);
        orderBookService.putAsk(secondPrice, secondSize);

        assertThat(orderBookService.getBestAskPrice(), is(expected));
    }

    @Test
    @Parameters({
        "100, 1, 99, 0, 100",
        "100, 0, 99, 1, 99",
        "100, 0, 99, 0, 100",
        "99, 1, 100, 0, 99",
        "100, 50, 50, 200, 100",
        "20, 100, 100, 5, 100"
    })
    public void shouldReturnCorrectBestBidPrice(int firstPrice, int firstSize, int secondPrice,
                                                int secondSize, int expected) {
        orderBookService.putBid(firstPrice, firstSize);
        orderBookService.putBid(secondPrice, secondSize);

        assertThat(orderBookService.getBestBidPrice(), is(expected));
    }

    @Test
    @Parameters({
        "100, 0, 75, 10, 76, 1, 10, 76",
        "100, 0, 75, 10, 76, 1, 10, 76",
        "100, 1, 75, 10, 76, 1, 11, 100",
        "100, 1, 75, 10, 76, 1, 12, 75",
        "100, 0, 75, 10, 76, 1, 5, 75",
        "100, 1, 75, 1, 76, 10, 11, 100",
        "11, 0, 5, 1, 2, 3, 3, 5",
        "5, 1, 11, 0, 2, 3, 3, 5",
        "5, 0, 11, 0, 2, 3, 3, 2",
        "5, 0, 11, 1, 2, 3, 3, 11",
    })
    public void shouldCorrectlyRemoveAskWhileBuying(int firstPrice, int firstSize, int secondPrice, int secondSize,
                                                    int thirdPrice, int thirdSize, int buySize, int expected) {
        orderBookService.putAsk(firstPrice, firstSize);
        orderBookService.putAsk(secondPrice, secondSize);
        orderBookService.putAsk(thirdPrice, thirdSize);

        orderBookService.buy(buySize);

        assertThat(orderBookService.getBestAskPrice(), is(expected));
    }

    @Test
    @Parameters({
        "100, 0, 75, 10, 76, 1, 10, 75",
        "100, 1, 75, 10, 76, 1, 11, 75",
        "100, 1, 75, 10, 76, 1, 12, 100",
        "100, 0, 75, 10, 76, 1, 11, 100",
        "100, 10, 75, 10, 76, 1, 5, 100",
        "11, 0, 5, 1, 12, 3, 3, 5",
        "5, 1, 11, 0, 12, 3, 3, 5",
        "11, 1, 5, 0, 12, 3, 3, 11",
        "5, 0, 11, 1, 12, 3, 3, 11",
        "5, 0, 11, 0, 12, 3, 3, 12",
    })
    public void shouldCorrectlyRemoveBidWhileSelling(int firstPrice, int firstSize, int secondPrice, int secondSize,
                                                     int thirdPrice, int thirdSize, int buySize, int expected) {
        orderBookService.putBid(firstPrice, firstSize);
        orderBookService.putBid(secondPrice, secondSize);
        orderBookService.putBid(thirdPrice, thirdSize);

        orderBookService.sell(buySize);

        assertThat(orderBookService.getBestBidPrice(), is(expected));
    }


    @Test
    public void shouldCorrectlyRemoveBidWhilePriceIsUnwilling() {
        orderBookService.putBid(5, 10);
        orderBookService.putBid(4, 10);
        orderBookService.putBid(5, 0);
        orderBookService.putAsk(5, 0);

        assertThat(orderBookService.getBestBidPrice(), is(4));
    }

    @Test
    @Parameters({
        "5, 10, 4, 10, 5, 0, 4",
        "5, 0, 4, 10, 5, 10, 5"
    })
    public void shouldCorrectlyRemoveBidWhilePriceIsUnwillingAndAskNotDetermine(int firstPrice, int firstSize,
                                                                                int secondPrice, int secondSize,
                                                                                int thirdPrice, int thirdSize,
                                                                                int expected ) {
        orderBookService.putBid(firstPrice, firstSize);
        orderBookService.putBid(secondPrice, secondSize);
        orderBookService.putBid(thirdPrice, thirdSize);

        assertThat(orderBookService.getBestBidPrice(), is(expected));
    }

    @Test
    @Parameters({
        "5, 10, 4, 10, 5, 0, 4",
        "5, 0, 8, 10, 5, 10, 5"
    })
    public void shouldCorrectlyRemoveAskAndAskWhilePriceIsUnwillingAndBidNotDetermine(int firstPrice, int firstSize,
                                                                                      int secondPrice, int secondSize,
                                                                                      int thirdPrice, int thirdSize,
                                                                                      int expected ) {
        orderBookService.putAsk(firstPrice, firstSize);
        orderBookService.putAsk(secondPrice, secondSize);
        orderBookService.putAsk(thirdPrice, thirdSize);

        assertThat(orderBookService.getBestAskPrice(), is(expected));
    }

    @Test
    public void shouldCorrectlyRemoveAskAndBidWhilePriceIsUnwilling() {
        orderBookService.putAsk(5, 10);
        orderBookService.putAsk(4, 10);
        orderBookService.putAsk(5, 0);
        orderBookService.putBid(4, 10);
        orderBookService.putBid(5, 0);


        assertThat(orderBookService.getBestAskPrice(), is(4));
        assertThat(orderBookService.getBestBidPrice(), is(4));
    }
}