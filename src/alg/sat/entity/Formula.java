package alg.sat.entity;

import java.util.*;

public class Formula {
    private int clausesCount;
    private int variablesCont;
    private ArrayList<Clause> clauses;
    private ArrayList<Integer> noTailClauseLiteralIndexes;
    private Map<Integer, HashSet<Clause>> implicationLeftSideLiteralIndexesMap;

    public Formula() {
        clauses = new ArrayList<>();
        noTailClauseLiteralIndexes = new ArrayList<>();
        implicationLeftSideLiteralIndexesMap = new HashMap<>();
    }

    public Formula(Formula formula) {
        setVariablesCont(formula.getVariablesCont());
        setClausesCount(formula.getClausesCount());
        setClauses(new ArrayList<>(formula.getClauses()));
        setImplicationLeftSideLiteralIndexesMap(new HashMap<>(formula.getImplicationLeftSideLiteralIndexesMap()));
        setNoTailClauseLiteralIndexes(new ArrayList<>(formula.getNoTailClauseLiteralIndexes()));
    }

    public void addClauseContainingNegatedLiteral(int i, Clause clause) {
        if (implicationLeftSideLiteralIndexesMap.containsKey(i)) {
            implicationLeftSideLiteralIndexesMap.get(i).add(clause);
        } else {
            HashSet<Clause> clauses = new HashSet<>();
            clauses.add(clause);
            implicationLeftSideLiteralIndexesMap.put(i, clauses);
        }
    }

    public ArrayList<Clause> getClauses() {
        return clauses;
    }

    public void setClauses(ArrayList<Clause> clauses) {
        this.clauses = clauses;
    }

    public int getClausesCount() {
        return clausesCount;
    }

    public void setClausesCount(int clausesCount) {
        this.clausesCount = clausesCount;
    }

    public int getVariablesCont() {
        return variablesCont;
    }

    public void setVariablesCont(int variablesCont) {
        this.variablesCont = variablesCont;
    }

    public List<Integer> getAllLiteralIndexes() {
        List<Integer> list = new ArrayList<>();
        for (Clause clause : getClauses()) {
            for (Literal literal : clause.getLiterals()) {
                Integer index = literal.getIndex();
                list.add(index);
            }
        }
        return list;
    }

    public List<Literal> getAllLiterals() {
        List<Literal> list = new ArrayList<>();
        for (Clause clause : getClauses()) {
            list.addAll(clause.getLiterals());
        }
        return list;
    }

    public ArrayList<Integer> getNoTailClauseLiteralIndexes() {
        return noTailClauseLiteralIndexes;
    }

    public void setNoTailClauseLiteralIndexes(ArrayList<Integer> noTailClauseLiteralIndexes) {
        this.noTailClauseLiteralIndexes = noTailClauseLiteralIndexes;
    }

    public Map<Integer, HashSet<Clause>> getImplicationLeftSideLiteralIndexesMap() {
        return implicationLeftSideLiteralIndexesMap;
    }

    public void setImplicationLeftSideLiteralIndexesMap(Map<Integer, HashSet<Clause>> implicationLeftSideLiteralIndexesMap) {
        this.implicationLeftSideLiteralIndexesMap = implicationLeftSideLiteralIndexesMap;
    }
}
