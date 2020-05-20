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
                Clause clause = new Clause();
                for (String s : stringArray) {
                    Literal literal = new Literal();
                    int index = Integer.parseInt(s.trim());
                    int absoluteIndex = Math.abs(index);
                    boolean isNegated = index < 0;
                    literal.setIndex(absoluteIndex);
                    literal.setNegated(isNegated);
                    literals.add(literal);

                    if (isNegated) {
                        formula.addClauseContainingNegatedLiteral(absoluteIndex, clause);
                    }
                }
                clause.setLiterals(literals);
                if (clause.getLiterals().size() == 1 && !clause.getLiterals().get(0).isNegated()) {
                    formula.getNoTailClauseLiteralIndexes().add(clause.getLiterals().get(0).getIndex());
                }
                clauses.add(clause);
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
