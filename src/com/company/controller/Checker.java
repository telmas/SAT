package com.company.controller;

import com.company.entity.Assignment;
import com.company.entity.Formula;

public class Checker {

    public boolean checkAssignment(Formula formula, Assignment assignment) {
        return false;
    }

    public boolean is2SAT(Formula formula) {
        boolean violatingClauseFound = formula.getClauses()
                .stream()
                .anyMatch(clause -> clause.getLiterals().size() != 2);
        return !violatingClauseFound;
    }

    public boolean isHornSAT(Formula formula) {//todo is second loop efficient enough--probably not
        boolean violatingClauseFound = formula
                .getClauses()
                .stream().anyMatch(clause -> clause
                        .getLiterals()
                        .stream()
                        .filter(literal -> !literal.isNegated())
                        .count() > 1);
        return !violatingClauseFound;
    }
}
