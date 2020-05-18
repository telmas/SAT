package alg.sat.controller;

import alg.sat.entity.Assignment;
import alg.sat.exception.UnsatisfiableFormulaException;
import alg.sat.entity.Formula;
import alg.sat.entity.Graph;
import alg.sat.entity.Literal;

import java.util.*;

public class Solver {

    public Assignment solveGeneralSAT(Formula formula) throws UnsatisfiableFormulaException {
        BitSet bitSet = new BitSet((int) Math.pow(2, formula.getVariablesCont()));

//        todo
        Checker checker = new Checker();
        Assignment candidateAssignment = new Assignment();
//        if (checker.checkAssignment()) {
//            return candidateAssignment;
//        };
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

        computedSCCs.keySet().forEach(key -> {
            computedSCCs.get(key).forEach(literal -> {
                literalSCCKeyMap.put(literal, key);
            });
        });

        Set<Literal> literalSet = new HashSet<>();
        formula.getClauses().forEach(clause -> {
            literalSet.addAll(clause.getLiterals());
        });

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
            reversePost.values().forEach(vertex -> {
                candidateAssignment.getSolution().putIfAbsent(vertex.getIndex(), !vertex.isNegated());
            });
            return candidateAssignment;
        }
        throw new UnsatisfiableFormulaException("Given 2SAT formula is unsatisfiable");
    }

    public Assignment solveHornSAT(Formula formula) {
        return null;
    }
}
