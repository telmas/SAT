package alg.sat.entity;

import java.util.*;
import java.util.stream.IntStream;

public class Assignment {
    private Map<Integer, Boolean> solution;
    private FixedSizeBitSet fixedSizeBitSet;

    public Assignment() {
        this.setSolution(new HashMap<>());
        this.setFixedSizeBitSet(new FixedSizeBitSet(solution.size()));
    }

    public Assignment(Formula formula, BitSet bitSet) {
        HashMap<Integer, Boolean> map = new HashMap<>();
        List<Integer> literalIndexes = formula.getAllLiteralIndexes();
        literalIndexes.forEach(index -> map.putIfAbsent(index, bitSet.get(index - 1)));
        this.setFixedSizeBitSet(new FixedSizeBitSet(map.size()));
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
        FixedSizeBitSet fixedSizeBitSet = new FixedSizeBitSet(solution.size());
        solution.forEach((key, value) -> {
            if (value) {
                fixedSizeBitSet.set(key - 1);
            }
        });
        String s = "Assignment" + solution;
        String b = "Assignment{" + "Bits:" + fixedSizeBitSet + '}';
        return s + "\n" + b;
    }

    public FixedSizeBitSet getFixedSizeBitSet() {
        return fixedSizeBitSet;
    }

    public void setFixedSizeBitSet(FixedSizeBitSet fixedSizeBitSet) {
        this.fixedSizeBitSet = fixedSizeBitSet;
    }


    private static class FixedSizeBitSet extends BitSet {
        private final int nbits;

        public FixedSizeBitSet(final int nbits) {
            super(nbits);
            this.nbits = nbits;
        }

        @Override
        public String toString() {
            final StringBuilder buffer = new StringBuilder(nbits);
            IntStream.range(0, nbits)
                    .mapToObj(i -> get(i) ? '1' : '0')
                    .forEach(obj -> buffer.append(obj).append(", "));
            return buffer.toString().replaceAll(", $", "");
        }
    }
}
