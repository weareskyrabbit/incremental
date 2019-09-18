package driver;

import front_end.RecursiveDescentParser;
import front_end.ParsingException;
import io.Reader;
import io.Writer;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Driver {
    public static void main(String[] args) throws IOException {
        boolean debugging = true;
        if (args.length == 0) {
            System.out.println("select a file to compile");
        }
        // front_end : Groupy -> AST
        final String input = Reader.use(args[0] + ".grp", Reader::read);
        final RecursiveDescentParser parser = new RecursiveDescentParser();
        List<ast.FunctionDeclare> ast_result1  = null;
        String[] ast_result2 = null;
        try {
            ast_result1 = parser.parse(input);
            ast_result2 = parser.strings().toArray(new String[0]);
        } catch (ParsingException exception) {
            System.out.println(exception.toString()); // TODO line, position
            /*
            for (int i = 0; i < parser.offset; i++) {
                System.out.print(" ");
            }
            System.out.println("^");
            */
            System.exit(1);
        }
        if (debugging) {
            final StringBuilder s = new StringBuilder();
            ast_result1.stream()
                    .map(ast -> ast.toS(0) + "\n")
                    .forEach(s::append);
            Writer.use(args[0] + ".ast", writer -> writer.write(s.toString()));
        }

        // middle_end : AST -> IR
        /*
        Builder.clear();
        Module module = Builder.generate(trees);
        module.build();
        System.out.println(Builder.build());
        */
        final List<ir.Function> ir_result1 = ast_result1.stream()
                .map(ast.FunctionDeclare::toIR)
                .collect(Collectors.toList());
        if (debugging) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < ast_result2.length; i++) {
                builder.append('c')
                        .append(i)
                        .append(" = \"")
                        .append(ast_result2[i])
                        .append("\"\n");
            }
            ir_result1.stream()
                    .map(Object::toString)
                    .forEach(builder::append);
            Writer.use(args[0] + ".ir", writer -> writer.write(builder.toString()));
        }
        // IR -> WC
        final DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(
                        args[0] + ".wc")));
        dos.writeInt(0xdeadbeef);                           // magic
        dos.writeInt(1);
        dos.writeInt(0);
        dos.writeInt(0);
        dos.writeInt(ast_result2.length);                   // constant pool size
        for (final String object : ast_result2) {
            dos.writeInt(object.length());                  // size
            dos.writeBytes(object);                         // value
        }
        dos.writeInt(ir_result1.size());                    // functions size
        for (ir.Function function : ir_result1) {
            dos.writeInt(function.instructions_size() + 1); // instructions size
            for (int instruction : function.toWC()) {
                dos.writeInt(instruction);
            }
            dos.writeInt(0x00000000);                       // stop
        }
        dos.flush();
        /*
        // vm
        final VirtualMachine vm = new VirtualMachine();
        // read .wc
        vm.analyze();
        vm.execute();
        */

        /*
        final StringBuilder ir = new StringBuilder();
        ir.append("; tab=8\n");
        ast_result1.forEach(tree -> ir.append(tree.generate()).append('\n'));
        Writer.use(args[0] + ".ir", writer -> writer.write(ir.toString()));
        */
        /*
        // back_end
        final String output = new Builder().build(trees);
        Writer.use(args[0] + ".s", writer -> writer.write(output));
        */
        // IR -> Assembly
        StringBuilder assembly_result = new StringBuilder(".intel_syntax noprefix\n");
        for (int i = 0; i < ast_result2.length; i++) {
            assembly_result.append(".Lc")
                    .append(i)
                    .append(":\n")
                    .append("\t.ascii \"")
                    .append(ast_result2[i])
                    .append("\\0\"\n");
        }
        ir_result1.stream()
                .map(ir.Function::toAssembly)
                .forEach(assembly_result::append);
        Writer.use(args[0] + ".s", writer -> writer.write(assembly_result.toString()));
    }
}
