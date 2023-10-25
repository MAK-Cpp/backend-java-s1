package edu.hw3.tasks.task6;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static edu.hw3.tasks.task6.StockMarketOperations.*;

public class StockMarketTest {
    @ParameterizedTest
    @MethodSource("stockOperations")
    public void testStockMarketOperations(
        final List<SimpleEntry<StockMarketOperations, Stock>> operations,
        final PriorityQueueStockMarket resultMarket,
        final Stock[] mostValuableStockResults
    ) {
        PriorityQueueStockMarket market = new PriorityQueueStockMarket();
        ArrayList<Stock> mostValuableStock = new ArrayList<>(mostValuableStockResults.length);
        for (SimpleEntry<StockMarketOperations, Stock> operation : operations) {
            switch (operation.getKey()) {
                case ADD -> market.add(operation.getValue());
                case REMOVE -> market.remove(operation.getValue());
                case MOST_VALUABLE_STOCK -> mostValuableStock.add(market.mostValuableStock());
            }
        }
        assertThat(market).isEqualTo(resultMarket);
        assertThat(mostValuableStock.toArray(new Stock[0])).isEqualTo(mostValuableStockResults);
    }

    private static SimpleEntry<StockMarketOperations, Stock> addOperation(
        final String stockName,
        final int stockPrice
    ) {
        return new SimpleEntry<>(ADD, new Stock(stockName, stockPrice));
    }

    private static SimpleEntry<StockMarketOperations, Stock> removeOperation(
        final String stockName,
        final int stockPrice
    ) {
        return new SimpleEntry<>(REMOVE, new Stock(stockName, stockPrice));
    }

    private static final SimpleEntry<StockMarketOperations, Stock> mostValuableStockOperation =
        new SimpleEntry<>(MOST_VALUABLE_STOCK, null);

    private static Stream<Arguments> stockOperations() {
        return Stream.of(
            Arguments.of(
                Arrays.asList(
                    addOperation("Apple", 100),
                    addOperation("Yandex", 2),
                    addOperation("Tinkoff", 10000),
                    mostValuableStockOperation,
                    removeOperation("Yandex", 2),
                    mostValuableStockOperation,
                    removeOperation("Tinkoff", 10000),
                    mostValuableStockOperation
                ),
                new PriorityQueueStockMarket(
                    new Stock("Apple", 100)
                ),
                new Stock[] {
                    new Stock("Tinkoff", 10000),
                    new Stock("Tinkoff", 10000),
                    new Stock("Apple", 100)
                }
            ),
            Arguments.of(
                List.of(),
                new PriorityQueueStockMarket(),
                new Stock[] {}
            ),
            Arguments.of(
                Arrays.asList(
                    addOperation("d", 1),
                    addOperation("b", 1),
                    addOperation("a", 1),
                    addOperation("c", 1),
                    mostValuableStockOperation,
                    addOperation("a", 100),
                    mostValuableStockOperation,
                    removeOperation("b", 1)
                ),
                new PriorityQueueStockMarket(
                    new Stock("a", 100),
                    new Stock("d", 1),
                    new Stock("c", 1),
                    new Stock("a", 1)
                ),
                new Stock[] {
                    new Stock("a", 1),
                    new Stock("a", 100)
                }
            ),
            Arguments.of(
                Arrays.asList(
                    addOperation("a", 1),
                    mostValuableStockOperation,
                    removeOperation("b", 2),
                    mostValuableStockOperation,
                    removeOperation("a", 1)
                ),
                new PriorityQueueStockMarket(),
                new Stock[] {
                    new Stock("a", 1),
                    new Stock("a", 1)
                }
            )
        );
    }
}
