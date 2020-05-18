package alg.sat.entity;

import java.util.Objects;

public class Literal {
    private int index;
    private boolean variable;
    private boolean negated;

    public Literal() {

    }

    public Literal(int index) {
        this(index, false);
    }

    public Literal(int index, boolean negated) {
        this(index, false, negated);
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

    @Override
    public String toString() {
        return "Literal{" +
                "index=" + index +
                ", variable=" + variable +
                ", negated=" + negated +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Literal)) return false;
        Literal literal = (Literal) o;
        return getIndex() == literal.getIndex() &&
                isVariable() == literal.isVariable() &&
                isNegated() == literal.isNegated();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIndex(), isVariable(), isNegated());
    }

    public boolean isNegationOf(Literal literal) {
        return getIndex() == literal.getIndex()
                && isNegated() != literal.isNegated();
    }
}
