package middle_end;

import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        final List<Instruction> instructions_factorial = Arrays.asList(
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

        );
        final List<Instruction> instructions_main = Arrays.asList(
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
        );
        final List<Function> function_declarations = Arrays.asList(
                new Function("factorial",
                        0,
                        instructions_factorial),
                new Function("main",
                        3,
                        instructions_main)
        );
        new Module(function_declarations).build();
        System.out.println(Builder.build());
    }
}
