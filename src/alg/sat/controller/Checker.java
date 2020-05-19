package alg.sat.controller;

import alg.sat.entity.Assignment;
import alg.sat.entity.Clause;
import alg.sat.entity.Formula;
import alg.sat.entity.Literal;

public class Checker {

    public boolean checkAssignment(Formula formula, Assignment assignment) {
        boolean satisfied = true;
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


//    public boolean checkAssignment(Formula formula, Assignment assignment) {
//        for (Clause clause : formula.getClauses()) {
//            boolean evaluatedClause;
//            for (Literal literal : clause.getLiterals()) {
//                Boolean literalSolutionValue = assignment.getSolution().get(literal.getIndex());
//                if (literalSolutionValue && !literal.isNegated()) {
//                    break;
//                }
//                evaluatedClause = literal.isNegated() ? !literalSolutionValue : literalSolutionValue;
//                if (evaluatedClause) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
    //assumed we always have 2 literals per clause
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
