package edu.hw3.tasks.task6;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.PriorityQueue;

public class PriorityQueueStockMarket implements StockMarket {
    private final PriorityQueue<Stock> market;

    public PriorityQueueStockMarket() {
        this.market = new PriorityQueue<>();
    }

    public PriorityQueueStockMarket(final Stock[] stocks) {
        this.market = new PriorityQueue<>(Arrays.asList(stocks));
    }

    @Override
    public void add(final Stock stock) {
        market.add(stock);
    }

    @Override
    public void remove(final Stock stock) {
        market.remove(stock);
    }

    @Override
    public Stock mostValuableStock() {
        return market.peek();
    }

    @Override
    public String toString() {
        return market.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PriorityQueueStockMarket that = (PriorityQueueStockMarket) o;
        if (market.isEmpty() && that.market.isEmpty()) {
            return true;
        }
        boolean ans = (market.size() == that.market.size());
        final Iterator<Stock> thisIterator = market.iterator();
        final Iterator<Stock> thatIterator = that.market.iterator();
        for (Stock a = thisIterator.next(), b = thatIterator.next();
             ans && thisIterator.hasNext() && thatIterator.hasNext();
             ans = (a.equals(b)), a = thisIterator.next(), b = thatIterator.next()) {
        }
        return ans;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(market);
    }
}
