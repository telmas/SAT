package com.company;

import com.company.controller.Checker;
import com.company.controller.Reader;
import com.company.entity.Formula;

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
    }
}
