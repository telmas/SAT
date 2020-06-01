package alg.sat.entity;

import java.util.ArrayList;
import java.util.HashSet;

public class Clause {
    private ArrayList<Literal> literals;
    /**
     * Utilized for HornSAT
     */
    private HashSet<Literal> literalSet;

    public Clause() {

    }

    public Clause(ArrayList<Literal> literals) {
        this.literals = literals;
    }

    public ArrayList<Literal> getLiterals() {
        return literals;
    }

    public void setLiterals(ArrayList<Literal> literals) {
        this.literals = literals;
    }

    public HashSet<Literal> getLiteralSet() {
        return literalSet;
    }

    public void setLiteralSet(HashSet<Literal> literalSet) {
        this.literalSet = literalSet;
    }
    
    public boolean isEmptyImplication(Integer index) {
        if (literalSet.size() == 1) {
            for (Literal literal : literalSet) {
                if(!literal.isNegated() && literal.getIndex() != index) {
                    return true;
                }
                break;
            }
        }
        return false;
    }
}
