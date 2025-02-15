package alg.sat.controller;

import alg.sat.entity.*;
import alg.sat.exception.UnsatisfiableFormulaException;

import java.math.BigInteger;
import java.util.*;

public class Solver {

    public Assignment solveGeneralSAT(Formula formula) throws UnsatisfiableFormulaException {
        Checker checker = new Checker();
        for (BigInteger bigIndex = BigInteger.valueOf(0);
             bigIndex.compareTo(BigInteger.valueOf((long) Math.pow(2, formula.getVariablesCont()))) < 0;
             bigIndex = bigIndex.add(BigInteger.ONE)) {
            BitSet bitSet = BitSet.valueOf(new long[]{bigIndex.longValue()});
            Assignment assignment = new Assignment(formula, bitSet);
            if (checker.checkAssignment(formula, assignment)) {
                return assignment;
            }
        }
        throw new UnsatisfiableFormulaException("Given SAT formula is unsatisfiable");
    }

    public Assignment solve2SAT(Formula formula) throws UnsatisfiableFormulaException {
        Graph<Literal> graph = new Graph<>();
        formula.getClauses().forEach(clause -> {
            ArrayList<Literal> literals = clause.getLiterals();
            Literal firstLiteral = literals.get(0);
            Literal secondLiteral = literals.get(1);
            Literal inverseNegationFirstLiteral = new Literal(firstLiteral.getIndex(), !firstLiteral.isNegated());
            Literal inverseNegationSecondLiteral = new Literal(secondLiteral.getIndex(), !secondLiteral.isNegated());
            graph.addEdge(inverseNegationFirstLiteral, secondLiteral);
            graph.addEdge(inverseNegationSecondLiteral, firstLiteral);
        });

        Map<Integer, List<Literal>> computedSCCs = graph.computeSCC();
        Map<Literal, Integer> literalSCCKeyMap = new HashMap<>();

        computedSCCs.keySet().forEach(key -> computedSCCs.get(key).forEach(literal -> literalSCCKeyMap.put(literal, key)));

        Set<Literal> literalSet = new HashSet<>();
        formula.getClauses().forEach(clause -> literalSet.addAll(clause.getLiterals()));

        boolean satisfiable = true;
        for (Literal literal : literalSet) {
            Literal newLiteral = new Literal();
            newLiteral.setIndex(literal.getIndex());
            newLiteral.setNegated(literal.isNegated());

            Literal negatedLiteral = new Literal();
            negatedLiteral.setIndex(literal.getIndex());
            negatedLiteral.setNegated(!literal.isNegated());

            Integer sccKey1 = literalSCCKeyMap.get(newLiteral);
            Integer sccKey2 = literalSCCKeyMap.get(negatedLiteral);
            if (sccKey1.equals(sccKey2)) {
                satisfiable = false;
                break;
            }
        }

        if (satisfiable) {
            Assignment candidateAssignment = new Assignment();
            TreeMap<Integer, Literal> post = graph.getPost();
            post.values().forEach(vertex -> candidateAssignment.getSolution().put(vertex.getIndex(), !vertex.isNegated()));
            return candidateAssignment;
        }
        throw new UnsatisfiableFormulaException("Given 2SAT formula is unsatisfiable");
    }

    public Assignment solveHornSAT(Formula formula) throws UnsatisfiableFormulaException {

        Set<Literal> literalSet = new HashSet<>();
        formula.getClauses().forEach(clause -> literalSet.addAll(clause.getLiterals()));
        Assignment candidateAssignment = new Assignment();

        literalSet.forEach(literal -> candidateAssignment.getSolution().put(literal.getIndex(), false));

        boolean unsatisfiedImplicationPresent;
        do {
            unsatisfiedImplicationPresent = false;
            Literal literal = null;
            ArrayList<Clause> clauses = formula.getClauses();
            for (Clause clause : clauses) {
                literal = clause.getLiterals().get(0);
                if (clause.getLiterals().size() == 1 && !literal.isNegated()) {
                    formula.getClauses().remove(clause);
                    candidateAssignment.getSolution().put(literal.getIndex(), true);
                    unsatisfiedImplicationPresent = true;
                    break;
                }
            }
            if (unsatisfiedImplicationPresent) {
                for (Clause clause : formula.getClauses()) {
                    Literal inverseNegationLiteral = new Literal(literal.getIndex(), !literal.isNegated());
                    clause.getLiterals().remove(inverseNegationLiteral);
                }
            }
        } while (unsatisfiedImplicationPresent);

        for (Clause clause : formula.getClauses()) {
            if (clause.getLiterals().stream().allMatch(Literal::isNegated)) {
                boolean expression = false;
                for (Literal literal :clause.getLiterals()) {
                    expression = !candidateAssignment.getSolution().get(literal.getIndex());
                    if (expression) {
                        break;
                    }
                }
                if (!expression) {
                    throw new UnsatisfiableFormulaException("Given HornSAT formula is unsatisfiable");
                }
            }
        }
        return candidateAssignment;
    }

    public Assignment solveHornSATLinear(Formula formula) throws UnsatisfiableFormulaException {
        Assignment assignment = new Assignment();
        for (int i = 1; i <= formula.getVariablesCont(); i++) {
            assignment.getSolution().put(i, false);
        }

        ArrayList<Integer> noTailClauseLiteralIndexes = formula.getNoTailClauseLiteralIndexes();
        while (!noTailClauseLiteralIndexes.isEmpty()) {
            Integer index = noTailClauseLiteralIndexes.remove(0);
            assignment.getSolution().put(index, true);
            if (!formula.getImplicationLeftSideLiteralIndexesMap().isEmpty()) {
                for (Clause clause : formula.getImplicationLeftSideLiteralIndexesMap().get(index)) {
                    clause.getLiteralSet().remove(new Literal(index, true));
                    if (clause.isEmptyImplication(index)) {
                        for (Literal literal: clause.getLiteralSet()) {
                            noTailClauseLiteralIndexes.add(literal.getIndex());
                            break;
                        }
                    }
                }
            }
        }

        for (Clause clause : formula.getClauses()) {
            if (clause.getLiterals().stream().allMatch(Literal::isNegated)) {
                boolean clauseSatisfied = false;
                for (Literal literal :clause.getLiterals()) {
                    clauseSatisfied = !assignment.getSolution().get(literal.getIndex());
                    if (clauseSatisfied) {
                        break;
                    }
                }
                if(!clauseSatisfied) {
                    throw new UnsatisfiableFormulaException("Given HornSAT formula is unsatisfiable");
                }
            }
        }
        return assignment;
    }
}