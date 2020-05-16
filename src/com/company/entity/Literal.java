package com.company.entity;

public class Literal {
    private int index;
    private boolean value;
    private boolean negated;

    public Literal() {

    }

    public Literal(int index, boolean value, boolean negated) {
        this.index = index;
        this.value = value;
        this.negated = negated;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public boolean isNegated() {
        return negated;
    }

    public void setNegated(boolean negated) {
        this.negated = negated;
    }
}
