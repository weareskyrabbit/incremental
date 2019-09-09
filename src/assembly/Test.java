package assembly;

public class Test {
    public static void main(String[] args) {
        final Label main = new Label("main", true);
        final Label add = new Label("add", true);
        Instruction[] instructions = {
                main,
                new Push(Register.RBP),
                new Mov(Register.RBP, Register.RSP),
                new Sub(Register.RSP, new Immediate(16)),
                new Mov(Register.RAX, Register.RBP),
                new Sub(Register.RAX, new Immediate(0)),
                new Mov(new Memory(Register.RAX), new Immediate(100)),
                new Mov(Register.RAX, Register.RBP),
                new Sub(Register.RAX, new Immediate(8)),
                new Mov(new Memory(Register.RAX), new Immediate(50)),
                new Mov(Register.RAX, Register.RBP),
                new Sub(Register.RAX, new Immediate(0)),
                new Mov(Register.RDI, new Memory(Register.RAX)),
                new Mov(Register.RAX, Register.RBP),
                new Sub(Register.RAX, new Immediate(8)),
                new Mov(Register.RSI, Register.RAX),
                new Call(add),
                new Mov(Register.RSP, Register.RBP),
                new Pop(Register.RBP),
                new Ret(),

                add,
                new Push(Register.RBP),
                new Mov(Register.RBP, Register.RSP),
                new Sub(Register.RSP, new Immediate(0)),
                new Push(Register.RDI),
                new Push(Register.RSI),
                new Pop(Register.RAX),
                new Pop(Register.RDI),
                new Add(Register.RAX, Register.RDI),
                new Push(Register.RAX),
                new Pop(Register.RAX),
                new Mov(Register.RSP, Register.RBP),
                new Pop(Register.RBP),
                new Ret()
        };
        System.out.println(".intel_syntax noprefix");
        for (Instruction instruction : instructions) {
            System.out.println(instruction.build());
        }
    }
}
