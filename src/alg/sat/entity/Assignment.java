package alg.sat.entity;

import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Assignment {
    private Map<Integer, Boolean> solution;

    public Assignment() {
        this.setSolution(new HashMap<>());
    }

    public Assignment(Formula formula, BitSet bitSet) {
        HashMap<Integer, Boolean> map = new HashMap<>();
        List<Integer> literalIndexes = formula.getAllLiteralIndexes();
        literalIndexes.forEach(index -> map.putIfAbsent(index, bitSet.get(index - 1)));
        this.setSolution(map);
    }

    public void setSolution(Map<Integer, Boolean> solution) {
        this.solution = solution;
    }

    public Map<Integer, Boolean> getSolution() {
        return solution;
    }

    @Override
    public String toString() {
        return "Assignment{" + "solution=" + solution + '}';
    }
}
