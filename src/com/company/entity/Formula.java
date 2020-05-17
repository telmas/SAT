package com.company.entity;

import java.util.ArrayList;

public class Formula {
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

    public void getFormulaLiterals() {
//        getClauses().stream().flatMap()
    }
}
