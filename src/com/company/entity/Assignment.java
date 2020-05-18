package com.company.entity;

import java.util.HashMap;
import java.util.Map;

public class Assignment {
    private Map<Integer, Boolean> solution;

    public Assignment() {
        this.setSolution(new HashMap<>());
    }

    public Map<Integer, Boolean> getSolution() {
        return solution;
    }

    public void setSolution(Map<Integer, Boolean> solution) {
        this.solution = solution;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "solution=" + solution +
                '}';
    }
}
