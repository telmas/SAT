package alg.sat;

import alg.sat.controller.Checker;
import alg.sat.controller.Reader;
import alg.sat.controller.Solver;
import alg.sat.entity.Assignment;
import alg.sat.entity.Formula;
import alg.sat.exception.UnsatisfiableFormulaException;

public class Main {

    public static void main(String[] args) {

        if (args.length < 1 || args[0] == null || args[0].trim().equals("") || args[0].isEmpty()) {
            System.out.printf("%s", "Attention! The required file path is not passed as argument!");
            return;
        }
        try {
            //todo: am skipping m and n
            String filePathString = args[0].trim();
            Reader reader = new Reader(filePathString);
            reader.readTextFileFormula();
            Formula readFormula = reader.getReadFormula();
            Checker checker = new Checker();
            System.out.println("is2SAT: " + checker.is2SAT(readFormula));
            System.out.println("isHornSAT: " + checker.isHornSAT(readFormula));
            Solver solver = new Solver();
            Assignment assignment;
//            assignment = solver.solve2SAT(readFormula);
//            System.out.println(assignment);
//            System.out.print("Formula satisfied? ");
//            System.out.println(checker.checkAssignment(readFormula, assignment));
            assignment = solver.solveHornSAT(readFormula);
            System.out.println(assignment);
            System.out.print("Formula satisfied? ");
            System.out.println(checker.checkAssignment(readFormula, assignment));
            Assignment assignment2 = solver.solveGeneralSAT(readFormula);
            System.out.println("Brute Force Solution:");
            System.out.println(assignment2);
        } catch (UnsatisfiableFormulaException e) {
            e.printStackTrace();
        }
    }
}
