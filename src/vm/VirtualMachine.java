package vm;

import java.util.Stack;

public class VirtualMachine {
    private int[] instructions;
    private byte[] index;
    private byte[] data;
    private int counter; // program counter
    private final Stack<Integer> stack;
    VirtualMachine() {
        this.counter = 0;
        this.stack = new Stack<>();
    }
    public void execute(final int[] instructions, final byte[] index, final byte[] data) {
        this.instructions = instructions;
        this.index = index;
        while (true) {
            switch (type()) {
                case 0x00: // stop
                    return;
                case 0x10: // push
                    stack.push(operand1());
                    break;
                case 0x20: // add
                    stack.push(stack.pop() + stack.pop());
                    break;
                case 0x21: // sub
                    stack.push(stack.pop() - stack.pop());
                    break;
                case 0x22: // mul
                    stack.push(stack.pop() * stack.pop());
                    break;
                case 0x23: // div
                    stack.push(stack.pop() / stack.pop());
                    break;
                case 0x30: // print
                    final int offset = index[operand1()];
                    final int size = index[operand1() + 1];
                    final StringBuilder builder = new StringBuilder();
                    for (int i = offset; i < offset + size; i++) {
                        builder.append((char) data[i]);
                    }
                    System.out.println(builder.toString());
                    break;
            }
            counter++;
        }
    }
    private int type() {
        return instructions[counter] >>> 24;
    }
    private int operand1() {
        return instructions[counter] >>> 16 & 0xff;
    }
    private int operand2() {
        return instructions[counter] >>> 8 & 0xff;
    }
    private int operand3() {
        return instructions[counter] & 0xff;
    }

    public static void main(String[] args) {
        final VirtualMachine vm = new VirtualMachine();
        final int[] test = { 0x30000000, 0x00000000 };

        char[] hello = "hello world!".toCharArray();
        int offset = 0;
        int size = hello.length;
        final byte[] test2 = { (byte) offset, (byte) size };
        final byte[] test3 = new byte[size];
        for (int i = 0; i < size; i++) {
            test3[offset + i] = (byte) hello[i];
        }

        vm.execute(test, test2, test3);
    }
}
