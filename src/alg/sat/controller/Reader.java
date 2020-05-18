package alg.sat.controller;

import alg.sat.entity.Clause;
import alg.sat.entity.Formula;
import alg.sat.entity.Literal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Reader {
    private String filePathString;
    private Formula readFormula;

    public Reader(String filePathString) {
        this.setFilePathString(filePathString);
    }

    public void readTextFileFormula() {
        Path filePath = Paths.get(getFilePathString());
        try {
            List<String[]> collect = Files.lines(filePath, StandardCharsets.ISO_8859_1)
//                    .map(line -> line.substring(0, line.indexOf('/')).split(", "))
                    .map(line -> line.split(", "))
                    .collect(Collectors.toList());

            Formula formula = new Formula();
            formula.setVariablesCont(Integer.parseInt(collect.get(0)[0]));
            formula.setClausesCount(Integer.parseInt(collect.get(1)[0]));

            ArrayList<Clause> clauses = new ArrayList<>();
            collect.stream().skip(2).forEach(stringArray -> {
                ArrayList<Literal> literals = new ArrayList<>();
                for (String s : stringArray) {
                    Literal literal = new Literal();
                    int index = Integer.parseInt(s.trim());
                    literal.setIndex(Math.abs(index));
                    literal.setNegated(index < 0);
                    literals.add(literal);
                }
                clauses.add(new Clause(literals));
            });
            formula.setClauses(clauses);
            setReadFormula(formula);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFilePathString() {
        return filePathString;
    }

    public void setFilePathString(String filePathString) {
        this.filePathString = filePathString;
    }

    public Formula getReadFormula() {
        return readFormula;
    }

    public void setReadFormula(Formula readFormula) {
        this.readFormula = readFormula;
    }
}
