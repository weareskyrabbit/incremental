package middle_end;

public class Jump extends Instruction {
    private final Label label;
    private static final Register[] registers = {
            Register.RDI,
            Register.RSI,
            Register.RDX,
            Register.RCX,
            Register.R8,
            Register.R9
    };
    Jump(final InstructionType type, final Operand left, final Operand right, final Label label) {
        super(type, left, right);
        this.label = label;
    }
    @Override
    void build() {
        switch (type) {
            case CALL:
                if (left != null) {
                    Builder.append("  mov  " + registers[0].toString() + ", " + left.toString() + "\n");
                    if (right != null) {
                        Builder.append("  mov  " + registers[1].toString() + ", " + right.toString() + "\n");
                    }
                }
                Builder.append("  call " + label.toString() + "\n");
                break;
        }
    }
}
