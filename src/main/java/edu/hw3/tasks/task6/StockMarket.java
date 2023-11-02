package edu.hw3.tasks.task6;

public interface StockMarket {
    /** Добавить акцию */
    void add(Stock stock);

    /** Удалить акцию */
    void remove(Stock stock);

    /** Самая дорогая акция */
    Stock mostValuableStock();
}
