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
            String filePathString = args[0].trim();
            Reader reader = new Reader(filePathString);
            reader.readTextFileFormula();
            Formula readFormula = reader.getReadFormula();
            Checker checker = new Checker();
            Solver solver = new Solver();
            Assignment assignment;
            Formula newFormula = new Formula(readFormula);
            if (checker.is2SAT(readFormula)) {
                System.out.println("Given CNF is: 2SAT");
                assignment = solver.solve2SAT(readFormula);
                printAndValidateAssignment(readFormula, assignment, checker);
            } else if (checker.isHornSAT(readFormula)) {
                System.out.println("Given CNF is: HornSAT");
                assignment = solver.solveHornSAT(newFormula);
                printAndValidateAssignment(newFormula, assignment, checker);
                System.out.println("Linear Horn Solution: ");
                assignment = solver.solveHornSATLinear(readFormula);
                printAndValidateAssignment(readFormula, assignment, checker);
            } else {
                System.out.println("Given CNF is: Undefined");
            }
            System.out.println("Brute Force Solution (Classic):");
            assignment = solver.solveGeneralSATBruteForceClassic(readFormula);
            printAndValidateAssignment(readFormula, assignment, checker);
        } catch (UnsatisfiableFormulaException e) {
            e.printStackTrace();
        }
    }

    public static void printAndValidateAssignment(Formula formula, Assignment assignment, Checker checker) {
        System.out.println(assignment);
        System.out.print("Formula satisfied? ");
        System.out.println(checker.checkAssignment(formula, assignment));
    }
}
