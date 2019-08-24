package middle_end;

public class Instruction {
    final InstructionType type;
    final Operand left;
    final Operand right;
    public Instruction(final InstructionType type, final Operand left, final Operand right) {
        this.type = type;
        this.left = left;
        this.right = right;
    }
    void build() {
        switch (type) {
            case ADD:
                Builder.append("  add  " + left.toString() + ", " + right.toString() + "\n");
                break;
            case SUB:
                Builder.append("  sub  " + left.toString() + ", " + right.toString() + "\n");
                break;
            case MUL:
                Builder.append("  imul  " + left.toString() + ", " + right.toString() + "\n");
                break;
            case DIV:
                Builder.append("  cqo\n  idiv  " + left.toString() + ", " + right.toString() + "\n");
                break;
            case PUSH:
                Builder.append("  push " + left.toString() + "\n");
                break;
            case POP:
                // register or memory
                Builder.append("  pop  " + left.toString() + "\n");
                break;
            case MOV:
                Builder.append("  mov  " + left.toString() + ", " +right.toString() + "\n");
                break;
            case RET:
                Builder.append("  ret\n");
                break;
        }
    }
    boolean necessary() {
        switch (type) {
            case ADD:
                if (left instanceof Immediate && ((Immediate)left).is_zero() ||
                        right instanceof Immediate && ((Immediate)right).is_zero()) {
                    return false;
                }
            case SUB:
                if (right instanceof Immediate && ((Immediate)right).is_zero()) {
                    return false;
                }
            case MUL:
                if ((left instanceof Immediate && ((Immediate)left).is_one()) ||
                        right instanceof Immediate && ((Immediate)right).is_one()) {
                    return false;
                }
            case DIV:
                if (right instanceof Immediate && ((Immediate)right).is_one()) {
                    return false;
                }
        }
        return true;
    }
}
