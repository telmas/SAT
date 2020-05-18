package alg.sat.exception;

public class UnsatisfiableFormulaException extends Exception {
    public UnsatisfiableFormulaException(String errorMessage) {
        super(errorMessage);
    }
}
