package edu.hw3.tasks.task6;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public record Stock(String stockName, int price) implements Comparable<Stock> {
    @Override
    public int compareTo(@NotNull final Stock o) {
        int priceCompare = Integer.compare(o.price, price);
        int nameCompare = stockName.compareTo(o.stockName);
        return priceCompare == 0 ? nameCompare : priceCompare;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stock stock = (Stock) o;
        return price == stock.price && Objects.equals(stockName, stock.stockName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockName, price);
    }
}
