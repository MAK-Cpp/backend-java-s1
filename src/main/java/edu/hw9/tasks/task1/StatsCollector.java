package edu.hw9.tasks.task1;

import java.util.List;

public interface StatsCollector {
    void push(String statName, double... values);

    List<Statistic> stats();
}
