package com.company.controller;

import com.company.entity.Assignment;
import com.company.entity.Clause;
import com.company.entity.Formula;
import com.company.entity.Literal;

public class Checker {

    public boolean checkAssignment(Formula formula, Assignment assignment) {
        boolean satisfied = true;
        for (Clause clause : formula.getClauses()) {
            boolean evaluatedClause = false;
            for (Literal literal : clause.getLiterals()) {
                Boolean literalSolutionValue = assignment.getSolution().get(literal.getIndex());
                evaluatedClause = evaluatedClause || (literal.isNegated() ? !literalSolutionValue : literalSolutionValue);
                if (evaluatedClause) {
                    break;
                }
            }
            satisfied = satisfied && evaluatedClause;
            if (!satisfied) {
                return false;
            }
        }
        return true;
    }

    public boolean is2SAT(Formula formula) {
        boolean violatingClauseFound = false;
        for (Clause clause : formula.getClauses()) {
            if (clause.getLiterals().size() != 2) {//assumed we always have 2 literals per clause
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
