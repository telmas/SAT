package com.company.entity;

import java.util.Arrays;

public class Assignment {
    private boolean[] solution;

    public Assignment(int literals) {
        this.setSolution(new boolean[literals]);
    }

    public Assignment(boolean[] solution) {
        this.setSolution(solution);
    }

    public boolean[] getSolution() {
        return solution;
    }

    public void setSolution(boolean[] solution) {
        this.solution = solution;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "solution=" + Arrays.toString(solution) +
                '}';
    }
}
