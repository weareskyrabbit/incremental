package back_end;

public enum InstructionType {
    // example : `STORE` `LOAD`
    ;
    // number of operands
    private final int number;
    InstructionType(final int number) {
        this.number = number;
    }
}