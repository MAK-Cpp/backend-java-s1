package edu.hw3.tasks.task2;

import java.util.ArrayList;
import java.util.Stack;

public final class ClusteringBrackets {
    private ClusteringBrackets() {
    }

    public static String[] clusterize(final String brackets) {
        ArrayList<String> clusterizedBrackets = new ArrayList<>();
        Stack<String> bracketsStack = new Stack<>();
        for (Character bracket : brackets.toCharArray()) {
            switch (bracket) {
                case '(' -> bracketsStack.push(String.valueOf(bracket));
                case ')' -> {
                    if (bracketsStack.isEmpty()) {
                        throw new BracketSequenceException("expected '(', got nothing");
                    }
                    if (bracketsStack.size() == 1) {
                        clusterizedBrackets.add(bracketsStack.pop() + bracket);
                    } else {
                        final String addedBracket = bracketsStack.pop() + bracket;
                        bracketsStack.push(bracketsStack.pop() + addedBracket);
                    }
                }
                default -> throw new BracketSequenceException("expected '(' or ')', got " + bracket);
            }
        }
        if (!bracketsStack.isEmpty()) {
            throw new BracketSequenceException("expected ')', got nothing");
        }
        return clusterizedBrackets.toArray(new String[0]);
    }
}
