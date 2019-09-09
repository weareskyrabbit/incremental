package middle_end;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Function {
    private final String name;
    private List<Instruction> list;
    public Function(final String name, final int locals, final List<Instruction> instructions) {
        this.name = name;

        list = new ArrayList<>();
        // prologue
        list.add(new Instruction(InstructionType.PUSH,
                Register.RBP,
                null));
        list.add(new Instruction(InstructionType.MOV,
                Register.RBP,
                Register.RSP));
        list.add(new Instruction(InstructionType.SUB,
                Register.RSP,
                new Immediate(locals * 8)));
        list.addAll(instructions);

        /* optimize(); */

    }
    void optimize() {
        list = list.stream()
                .filter(Instruction::necessary)
                .collect(Collectors.toList());
    }
    void build() {
        Builder.append(".global " + name + "\n" + name + ":\n");
        for (Instruction instruction : list) {
            instruction.build();
        }
    }
}
