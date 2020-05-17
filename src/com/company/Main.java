package com.company;

import com.company.controller.Checker;
import com.company.controller.Reader;
import com.company.entity.Formula;
import com.company.entity.Graph;
import com.company.entity.Literal;
import sun.java2d.windows.GDIRenderer;

import java.util.*;
import java.util.logging.Handler;

public class Main {

    public static void main(String[] args) {

        if (args.length < 1 || args[0] == null || args[0].trim().equals("") || args[0].isEmpty()) {
            System.out.printf("%s", "Attention! The required file path is not passed as argument!");
            return;
        }

        String filePathString = args[0].trim();
        Reader reader = new Reader(filePathString);
        reader.readTextFileFormula();
        Formula readFormula = reader.getReadFormula();
        Checker checker = new Checker();
        System.out.println("is2SAT: " + checker.is2SAT(readFormula));
        System.out.println("isHornSAT: " + checker.isHornSAT(readFormula));

        ArrayList<Literal> literals = new ArrayList<>();
        readFormula.getClauses().forEach(x -> {
            literals.addAll(x.getLiterals());
        });
        Graph<Literal> graph = new Graph<>();
        Map<Literal, List<Literal>> map = new HashMap<>();
        LinkedList<Literal> literals1 = new LinkedList<>();
        literals1.add(literals.get(1));
        map.put(literals.get(0), literals1);
        literals1 = new LinkedList<>();
        literals1.add(literals.get(2));
        literals1.add(literals.get(3));
        literals1.add(literals.get(4));
        map.put(literals.get(1), literals1);
        literals1 = new LinkedList<>();
        literals1.add(literals.get(5));
        map.put(literals.get(2), literals1);
        literals1 = new LinkedList<>();
        map.put(literals.get(3), literals1);
        literals1 = new LinkedList<>();
        literals1.add(literals.get(1));
        literals1.add(literals.get(5));
        map.put(literals.get(4), literals1);
        literals1 = new LinkedList<>();
        literals1.add(literals.get(2));
        map.put(literals.get(5), literals1);
        graph.setAdjacencyList(map);
        Map<Integer, List<Literal>> computeSCC = graph.computeSCC();
        System.out.println(computeSCC.toString());
    }
}
