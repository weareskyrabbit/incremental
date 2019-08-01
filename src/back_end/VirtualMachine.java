package back_end;

// 32-bit instruction
// CISC
// multi-threaded machine
// stack machine
public abstract class VirtualMachine {
    Instruction[] instructions;
    // offset in instructions
    int offset;
    // execute instructions
    abstract void execute();
}
