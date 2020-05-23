package alg.sat.controller;

import alg.sat.entity.*;
import alg.sat.exception.UnsatisfiableFormulaException;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Solver {

    public Assignment solveGeneralSATBruteForceClassic(Formula formula) throws UnsatisfiableFormulaException {
        Checker checker = new Checker();
        for (BigInteger bi = BigInteger.valueOf(0);
             bi.compareTo(BigInteger.valueOf((long) Math.pow(2, formula.getVariablesCont()))) < 0;
             bi = bi.add(BigInteger.ONE)) {
            BitSet bitSet = BitSet.valueOf(new long[]{bi.longValue()});
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
            TreeMap<Integer, Literal> reversePost = new TreeMap<>();
            reversePost.putAll(post);//don't change
            reversePost.values().forEach(vertex -> candidateAssignment.getSolution().putIfAbsent(vertex.getIndex(), !vertex.isNegated()));
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
                if(!expression) {
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
                    clause.getLiterals().remove(new Literal(index, true));
                    if (clause.getLiterals().size() == 1 &&
                            !clause.getLiterals().get(0).isNegated() &&
                            clause.getLiterals().get(0).getIndex() != index) {
                        noTailClauseLiteralIndexes.add(clause.getLiterals().get(0).getIndex());
                    }
                }
            }
        }

        for (Clause clause : formula.getClauses()) {
            if (clause.getLiterals().stream().allMatch(Literal::isNegated)) {
                boolean expression = false;
                for (Literal literal :clause.getLiterals()) {
                    expression = !assignment.getSolution().get(literal.getIndex());
                    if (expression) {
                        break;
                    }
                }
                if(!expression) {
                    throw new UnsatisfiableFormulaException("Given HornSAT formula is unsatisfiable");
                }
            }
        }
        return assignment;
    }

    public Assignment solveGeneralSATBruteForceDPLL(Formula formula) throws UnsatisfiableFormulaException {
        Assignment assignment = new Assignment();
        for (int i = 1; i <= formula.getVariablesCont(); i++) {
            assignment.getSolution().put(i, false);
        }
        if (booleanSolveGeneralSATBruteForceDPLL(formula, assignment)) {
            return assignment;
        }
        throw new UnsatisfiableFormulaException("Given SAT formula is unsatisfiable");
    }

    public boolean booleanSolveGeneralSATBruteForceDPLL(Formula formula, Assignment assignment) {

        if (formula.getClauses().size() == 0) {
            return true;
        }
        if (formula.getClauses().stream().anyMatch(clause -> clause.getLiterals().size() == 0)) {
            return false;
        }

        List<Literal> allLiterals = formula.getAllLiterals();
        List<Clause> unitClauses = formula.getUnitClauses();
        Set<Literal> pureLiterals = allLiterals.stream()
                .filter(literal -> Collections.frequency(allLiterals, new Literal(literal.getIndex(), !literal.isNegated())) == 0)
                .collect(Collectors.toSet());

        for (Clause unitClaus : unitClauses) {
            unitPropagate(unitClaus.getLiterals().get(0), formula, assignment);
        }
        pureLiterals.forEach(literal -> pureLiteralElimination(literal, formula, assignment));

        Collections.shuffle(formula.getAllLiterals());
        Literal chosenLiteral = allLiterals.get(0);
        Literal negatedLiteral = new Literal(chosenLiteral.getIndex(), !chosenLiteral.isNegated());

        return booleanSolveGeneralSATBruteForceDPLL(formula, assignment) && chosenLiteral.getLiteralTrueByNegationStatus()
                || booleanSolveGeneralSATBruteForceDPLL(formula, assignment) && negatedLiteral.getLiteralTrueByNegationStatus();
    }

    private void unitPropagate(Literal literal, Formula formula, Assignment assignment) {
        assignment.getSolution().put(literal.getIndex(), literal.getLiteralTrueByNegationStatus());
        formula.getClauses().removeIf(clause -> clause.getLiterals().contains(literal));

        for (Clause clause : formula.getClauses()) {
            Literal negatedLiteral = new Literal(literal.getIndex(), !literal.isNegated());
            if (clause.getLiterals().contains(negatedLiteral)) {
                formula.getClauses().remove(clause);
            }
        }
    }

    private void pureLiteralElimination(Literal literal, Formula formula, Assignment assignment) {
        assignment.getSolution().put(literal.getIndex(), literal.getLiteralTrueByNegationStatus());
        formula.getClauses().removeIf(clause -> clause.getLiterals().contains(literal));
    }
}