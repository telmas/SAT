package alg.sat.controller;

import alg.sat.entity.Assignment;
import alg.sat.entity.Clause;
import alg.sat.entity.Formula;
import alg.sat.entity.Literal;

public class Checker {

    public boolean checkAssignment(Formula formula, Assignment assignment) {
        boolean satisfied;
        for (Clause clause : formula.getClauses()) {
            boolean evaluatedClause = false;
            for (Literal literal : clause.getLiterals()) {
                Boolean literalSolutionValue = assignment.getSolution().get(literal.getIndex());
                evaluatedClause = literal.isNegated() ? !literalSolutionValue : literalSolutionValue;
                if (evaluatedClause) {
                    break;
                }
            }
            satisfied = evaluatedClause;
            if (!satisfied) {
                return false;
            }
        }
        return true;
    }

    //assumption: always 2 literals per clause
    public boolean is2SAT(Formula formula) {
        for (Clause clause : formula.getClauses()) {
            if (clause.getLiterals().size() != 2) {
                return false;
            }
        }
        return true;
    }

    public boolean isHornSAT(Formula formula) {
        for (Clause clause : formula.getClauses()) {
            long count = 0L;
            for (Literal literal : clause.getLiterals()) {
                if (!literal.isNegated()) {
                    count++;
                }
                if (count > 1) {
                    return false;
                }
            }
        }
        return true;
    }
}
