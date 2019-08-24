package middle_end;

public class Test {
    public static void main(String[] args) {
        final Instruction[] instructions_factorial = {
                new Instruction(InstructionType.PUSH,
                        Register.RDI,
                        null),
                new Instruction(InstructionType.SUB,
                        Register.RDI,
                        new Immediate(1)),
                new Jump(InstructionType.CALL,
                        null,
                        null,
                        new Label("factorial")),
                new Instruction(InstructionType.POP,
                        Register.RDI,
                        null),
                new Instruction(InstructionType.MUL,
                        Register.RAX,
                        Register.RDI),
                new Instruction(InstructionType.MOV,
                        Register.RSP,
                        Register.RBP),
                new Instruction(InstructionType.POP,
                        Register.RBP,
                        null),
                new Instruction(InstructionType.RET,
                        null,
                        null)

        };
        final Instruction[] instructions_main = {
                new Instruction(InstructionType.MOV,
                        Register.RAX,
                        Register.RBP),
                new Instruction(InstructionType.SUB,
                        Register.RAX,
                        new Immediate(0)),
                new Instruction(InstructionType.MOV,
                        new Address(Register.RAX),
                        new Immediate(2)),
                new Instruction(InstructionType.MOV,
                        Register.RAX,
                        Register.RBP),
                new Instruction(InstructionType.SUB,
                        Register.RAX,
                        new Immediate(8)),
                new Instruction(InstructionType.MOV,
                        new Address(Register.RAX),
                        new Immediate(4)),
                new Jump(InstructionType.CALL,
                        new Immediate(5),
                        null,
                        new Label("factorial"))
        };
        final Function[] function_declarations = {
                new Function("factorial",
                        0,
                        instructions_factorial),
                new Function("main",
                        3,
                        instructions_main)
        };
        new Module(function_declarations).build();
        System.out.println(Builder.build());
    }
}
