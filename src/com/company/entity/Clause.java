package com.company.entity;

import java.util.ArrayList;

public class Clause {
    private ArrayList<Literal> literals;

    public Clause(ArrayList<Literal> literals) {
        this.literals = literals;
    }

    public ArrayList<Literal> getLiterals() {
        return literals;
    }

    public void setLiterals(ArrayList<Literal> literals) {
        this.literals = literals;
    }
}
