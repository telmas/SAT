package alg.sat.entity;

import java.util.ArrayList;

public class Formula {
    private int clausesCount;
    private int variablesCont;
    private ArrayList<Clause> clauses;

    public Formula() {

    }

    public Formula(ArrayList<Clause> clauses) {
        this.clauses = clauses;
    }

    public ArrayList<Clause> getClauses() {
        return clauses;
    }

    public void setClauses(ArrayList<Clause> clauses) {
        this.clauses = clauses;
    }

    public int getClausesCount() {
        return clausesCount;
    }

    public void setClausesCount(int clausesCount) {
        this.clausesCount = clausesCount;
    }

    public int getVariablesCont() {
        return variablesCont;
    }

    public void setVariablesCont(int variablesCont) {
        this.variablesCont = variablesCont;
    }
}
