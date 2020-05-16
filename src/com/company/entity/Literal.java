package com.company.entity;

public class Literal {
    private int index;
    private boolean variable;
    private boolean negated;

    public Literal() {

    }

    public Literal(int index, boolean variable, boolean negated) {
        this.index = index;
        this.variable = variable;
        this.negated = negated;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isVariable() {
        return variable;
    }

    public void setVariable(boolean variable) {
        this.variable = variable;
    }

    public boolean isNegated() {
        return negated;
    }

    public void setNegated(boolean negated) {
        this.negated = negated;
    }
}
