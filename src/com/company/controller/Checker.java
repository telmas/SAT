package com.company.controller;

import com.company.entity.Assignment;
import com.company.entity.Clause;
import com.company.entity.Formula;

import java.util.Optional;

public class Checker {

    public boolean checkAssignment(Formula formula, Assignment assignment) {
        return false;
    }

    public boolean is2SAT(Formula formula) {
        Optional<Clause> violatingClause = formula.getClauses()
                .stream()
                .filter(clause -> clause.getLiterals().size() != 2)
                .findAny();
        return !violatingClause.isPresent();
    }

    public boolean isHornSAT(Formula formula) {
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
