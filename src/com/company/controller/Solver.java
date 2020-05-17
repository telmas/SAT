package com.company.controller;

import com.company.entity.Assignment;
import com.company.entity.Formula;
import com.company.entity.Graph;
import com.company.entity.Literal;

import java.util.*;

public class Solver {

    public Assignment solveGeneralSAT(Formula formula) {
        return null;
    }

    public Assignment solve2SAT(Formula formula) {

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
            }
        }

        if (satisfiable) {
            Assignment candidateAssignment = new Assignment(literalSet.size());
            TreeMap<Integer, Literal> post = graph.getPost();
            System.out.println(post);
            post.values().forEach(vertex -> {
                candidateAssignment.getSolution()[vertex.getIndex() - 1] = !vertex.isNegated();
            });
            return candidateAssignment;
        }
        return null;
    }

    public Assignment solveHornSAT(Formula formula) {
        return null;
    }
}
