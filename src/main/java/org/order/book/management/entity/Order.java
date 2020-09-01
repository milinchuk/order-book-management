package org.order.book.management.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

import static java.util.Optional.ofNullable;

public class Order {
    private Map<Integer, Integer> priceLevels = new HashMap<>();
    private Integer bestPrice = null;
    private BiPredicate<Integer, Integer> comparingFunction;

    public Order() {
        this.comparingFunction = (e1, e2) -> e1 < e2;
    }

    public Order(BiPredicate<Integer, Integer> comparingFunction) {
        this.comparingFunction = comparingFunction;
    }

    public void put(int price, int size) {
        Integer bestPriceSize = priceLevels.get(bestPrice);

        if (bestPrice == null || isNewPriceBetterThanBestPrice(price, size, bestPriceSize)) {
            bestPrice = price;
        }

        priceLevels.put(price, size);

        if (bestPrice == price && size == 0) {
            updateBestPrice();
        }
    }

    public boolean isExistsSizeByPrice(int price) {
        return ofNullable(priceLevels.get(price)).isPresent();
    }

    public int getSizeByPrice(int price) {
        return ofNullable(priceLevels.get(price)).orElse(0);
    }

    public Integer getBestPrice() {
        return bestPrice;
    }

    public void setAsUnwilling(int price) {
        priceLevels.put(price, 0);

        updateBestPrice();
    }

    public void remove(int price) {
        priceLevels.remove(price);

        updateBestPrice();
    }

    private void updateBestPrice() {
        priceLevels.keySet().stream()
                   .reduce(this::comparePricesWithPriority)
                   .ifPresent(price -> bestPrice = price);
    }

    private int comparePricesWithPriority(int x, int y) {
        boolean yBetterThanX = comparingFunction.test(x, y);

        if (priceLevels.get(x) == 0 && priceLevels.get(y) == 0) {
            return yBetterThanX ? y : x;
        }

        if (yBetterThanX && priceLevels.get(y) == 0) {
            return x;
        }

        if (!yBetterThanX && priceLevels.get(x) == 0) {
            return y;
        }

        return yBetterThanX ? y : x;
    }

    private boolean isNewPriceBetterThanBestPrice(int price, int size, int bestPriceSize) {
        return size > 0 && (bestPriceSize == 0 || (comparingFunction.test(bestPrice, price)));
    }
}
