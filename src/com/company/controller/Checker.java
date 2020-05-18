package com.company.controller;

import com.company.entity.Assignment;
import com.company.entity.Clause;
import com.company.entity.Formula;
import com.company.entity.Literal;

public class Checker {

    public boolean checkAssignment(Formula formula, Assignment assignment) {
//        formula.getClauses().forEach(clause -> {
//            boolean evaluatedClause = true;
//            clause.getLiterals().forEach(literal -> {
//                Boolean literalValue = assignment.getSolution().get(literal.getIndex());
//                evaluatedClause
//
//            });
//        });
        //todo
        return false;
    }

    public boolean is2SAT(Formula formula) {
        boolean violatingClauseFound = false;
        for (Clause clause : formula.getClauses()) {
            if (clause.getLiterals().size() != 2) {
                violatingClauseFound = true;
                break;
            }
        }
        return !violatingClauseFound;
    }

    public boolean isHornSAT(Formula formula) {
        boolean violatingClauseFound = false;
        for (Clause clause : formula.getClauses()) {
            long count = 0L;
            for (Literal literal : clause.getLiterals()) {
                if (!literal.isNegated()) {
                    count++;
                }
                if (count > 1) {
                    violatingClauseFound = true;
                    break;
                }
            }
        }
        return !violatingClauseFound;
    }
}
