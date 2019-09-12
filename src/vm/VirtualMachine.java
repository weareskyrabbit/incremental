package vm;

import ast.FunctionDeclaration;
import front_end.ParsingException;
import front_end.RecursiveDescentParser;
import io.Reader;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class VirtualMachine {
    private byte[] input;
    private int position;

    private int[] instructions;
    private String[] constant_pool;

    private int counter; // program counter
    private final Stack<Integer> stack;
    public VirtualMachine() {
        this.counter = 0;
        this.stack = new Stack<>();
    }
    public void analyze() {
        final int magic = read_u4();
        if (magic != 0xdeadbeef) {
            System.out.println("magic is invalid");
        }
        int functions_size = read_u4();
        int instructions_size = read_u4();
        instructions = new int[instructions_size];
        for (int i = 0; i < instructions_size; i++) {
            instructions[i] = read_u4();
        }
        for (int i = 0; i < functions_size - 1; i++) {
            instructions_size = read_u4();
            for (int j = 0; j < instructions_size; j++) {
                read_u4();
            }
        }
        int constant_pool_size = read_u4();
        constant_pool = new String[constant_pool_size];
        for (int i = 0; i < constant_pool_size; i++) {
            int size = read_u4();
            byte[] bytes = read(size);
            StringBuilder builder = new StringBuilder();
            for (byte b : bytes) {
                builder.append((char) b);
            }
            constant_pool[i] = builder.toString();
        }
    }
    public void execute() {
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
                    System.out.println(constant_pool[operand1()]);
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
    private byte[] read(final int length) {
        final byte[] bytes = Arrays.copyOfRange(input, position, position + length);
        position += length;
        return bytes;
    }
    private int read_u2() {
        final byte[] bytes = read(2);
        // unsigned
        return (short) ((bytes[0] << 8 & 0xffff) | (bytes[1] & 0x00ff));
    }
    private int read_u4() {
        final byte[] bytes = read(4);
        // unsigned
        return bytes[0] << 24 |
                (bytes[1] << 16 & 0x00ffffff) |
                (bytes[2] << 8 & 0x0000ffff) |
                (bytes[3] & 0x000000ff);
    }
    public static void main(String[] args) throws IOException {
        final String input = io.Reader.use("hello.grp", Reader::read);
        final RecursiveDescentParser parser = new RecursiveDescentParser();
        List<FunctionDeclaration> ast = null;
        String[] strings = null;
        try {
            ast = parser.parse(input);
            strings = parser.strings().toArray(new String[1]);
        } catch (ParsingException exception) {
            System.out.println(exception.toString());
        }

        final List<ir.Function> ir = ast.stream()
                .map(FunctionDeclaration::toIR)
                .collect(Collectors.toList());

        try (final DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(
                new FileOutputStream("hello.wc")))) {
            dos.writeInt(0xdeadbeef);       // magic
            dos.writeInt(ir.size());        // functions size
            for (ir.Function function : ir) {
                dos.writeInt(function.instructions_size() + 1); // instructions size
                for (int instruction : function.toWordCode()) {
                    dos.writeInt(instruction);
                }
                dos.writeInt(0x00000000);       // stop
            }
            dos.writeInt(strings.length);   // constant pool size
            for (final String object : strings) {
                dos.writeInt(object.length());   // size
                dos.writeBytes(object);          // value
            }

            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final VirtualMachine vm = new VirtualMachine();

        try (final BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream("hello.wc"))) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int n = bis.read(data);
            while (n != -1) {
                baos.write(data, 0, n);
                n = bis.read(data);
            }
            vm.input = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        vm.analyze();
        vm.execute();
    }
}
